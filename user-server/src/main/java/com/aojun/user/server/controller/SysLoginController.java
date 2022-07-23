package com.aojun.user.server.controller;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.aojun.auth.api.client.AuthClient;
import com.aojun.user.api.entity.SysUser;
import com.aojun.user.server.mapper.SysUserMapper;
import com.aojun.user.server.service.SysUserService;
import com.aojun.user.api.form.SysLoginForm;
import com.aojun.user.server.config.LoginConfig;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.code.kaptcha.Producer;
import com.aojun.common.base.constant.Constant;
import com.aojun.common.base.po.UserInfo;
import com.aojun.common.base.util.Result;
import com.aojun.common.redis.util.RedisUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;
import java.util.Map;

import static com.aojun.common.base.util.DateUtils.format;
import static com.aojun.common.redis.util.RedisUtils.DEFAULT_EXPIRE;


@RestController
@Slf4j
@Api(tags = "登录管理")
@RefreshScope
public class SysLoginController {

    @Autowired
    private Producer producer;
    @Autowired
    private RedisUtils redisUtils;
    @Autowired
    private AuthClient authClient;
    @Autowired
    private LoginConfig loginConfig;
    @Autowired
    private SysUserMapper userMapper;
    @Autowired
    private SysUserService sysUserService;


    /**
     * 获取验证码
     */
    @GetMapping("/captcha")
    @ApiOperation(value = "获取验证码", produces = "application/octet-stream")
    public void captcha(String randomStr, HttpServletResponse response) {
        response.setHeader("Cache-Control", "no-store, no-cache");
        response.setContentType("image/jpeg");
        //生成验证码
        String text = producer.createText();
        //保存验证码信息
        redisUtils.delete(Constant.CODE_KEY + randomStr);
        redisUtils.set(Constant.CODE_KEY + randomStr, text, 300); // 1分钟有效
        System.out.println("code: " + text);

        try {
            OutputStream out = response.getOutputStream();
            BufferedImage image = producer.createImage(text);
            ImageIO.write(image, "jpg", out);
            out.flush();
            out.close();
        } catch (IOException e) {
            log.error("生成验证码异常:", e);
        }

    }

    /**
     * 登录
     *
     * @param loginForm
     * @return
     */
    @PostMapping("/login")
    @ApiOperation("登录")
    public Result login(@RequestBody SysLoginForm loginForm) {
        // 1.判断验证码是否正确
        if (!validate(loginForm.getCaptcha(), loginForm.getRandomStr())) {
            return Result.failed("验证码不正确");
        }
        // 2.判断用户名是否正确
        SysUser user = userMapper.selectOne(new QueryWrapper<SysUser>().eq("username", loginForm.getUsername()));
        if (user == null) return Result.failed("用户名错误");
        if (!user.getEnablePwd().equals(loginForm.getPassword())) return Result.failed("密码错误");
        if (user.getStatus() == 0) return Result.failed("账号已禁用，请联系管理员");
        return loginAccount(loginForm);
    }

    /**
     * 登录
     *
     * @param loginForm
     * @return
     */
    @PostMapping("/authlogin")
    @ApiOperation("授权登录")
    public Result authlogin(@RequestBody SysLoginForm loginForm) {
        // 验证手机验证码是否正确
        String key = Constant.PHONE_KEY + loginForm.getMobile();
        if (StrUtil.isNotBlank(loginForm.getMobileCode()) && redisUtils.hasKey(key)) {
            String code = redisUtils.get(key);
            redisUtils.delete(key);
            if (!code.equals(loginForm.getMobileCode())) {
                return Result.failed("手机验证码输入错误");
            }
        } else {
            return Result.failed("手机验证码错误");
        }

        return Result.failed("该电脑尚未授权，请联系账户管理员授权通过，方可登录");
    }

    /**
     * 判断账号是不是存在，若存在，判断该账号 是否授权
     */
    @GetMapping("/validateUserAuth")
    @ApiOperation("判断账号是否授权接口")
    public Result validateUserAuth(@RequestParam("username") String username) {
        SysUser sysUser = sysUserService.getOne(new QueryWrapper<SysUser>().eq(StringUtils.isNotBlank(username), "username", username));
        if (sysUser == null) return Result.failed("用户名错误");
        return Result.ok(sysUser);
    }


    /**
     * 鉴权登录
     */
    private Result loginAccount(SysLoginForm loginForm) {

        String grant_type = "password";// 密码方式获取token
        // 2.业务请求
        Map<String, Object> dataMap = authClient.oauth2(authHeaders(), grant_type, loginForm.getUsername(), loginForm.getPassword());
        // 登录成功
        if (dataMap.get("access_token") != null) {
            UserInfo userInfo = JSON.parseObject(JSON.toJSONString(dataMap.get("userInfo")), UserInfo.class);
            SysUser user = new SysUser();
            // 获取登录人ip
            user.setIpAddress(loginForm.getIpAddress());
            // 设置登录时间
            user.setLoginDate(new Date());
            userMapper.update(user, new QueryWrapper<SysUser>().eq("user_id", userInfo.getUserId()));
            // 把当前用户token 放到redis
            redisUtils.set(userInfo.getUsername(), dataMap.get("access_token"), DEFAULT_EXPIRE); // 7天有效
            return Result.ok(dataMap);
        }
        return Result.failed("登录失败");

    }

    /**
     * 校验验证码
     *
     * @param captcha
     * @param randomStr
     * @return
     */
    private boolean validate(String captcha, String randomStr) {
        if (StringUtils.isBlank(captcha)) {
            return false;
        }
        // 验证验证码是否正确
        String key = Constant.CODE_KEY + randomStr;
        if (redisUtils.hasKey(key)) {
            Object validCode = redisUtils.get(key);
            if (validCode == null || !captcha.trim().equalsIgnoreCase(String.valueOf(validCode).trim())) {
                return false;
            }
            // 删除验证码
            redisUtils.delete(key);
            return true;
        }
        return false;
    }


    /**
     * 生成请求头 header
     *
     * @return
     */
    private String authHeaders() {
        String base64Source = loginConfig.getClientId() + ":" + loginConfig.getClientSecret();
        return LoginConfig.AUTH_PREFIX + Base64.encodeBase64String(base64Source.getBytes());
    }


}
