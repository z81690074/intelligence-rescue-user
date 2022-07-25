package com.aojun.user.server.service.impl;

import cn.hutool.core.util.StrUtil;
import com.aojun.common.base.constant.Constant;
import com.aojun.common.redis.util.RedisUtils;
import com.aojun.user.api.form.PasswordForm;
import com.aojun.user.api.request.UserRequest;
import com.aojun.user.server.mapper.SysRoleMapper;
import com.aojun.user.server.service.SysUserRoleService;
import com.aojun.user.api.entity.SysRole;
import com.aojun.user.api.entity.SysUser;
import com.aojun.user.server.mapper.SysUserMapper;
import com.aojun.user.server.service.SysRoleService;
import com.aojun.user.server.service.SysUserService;
import com.aojun.common.base.util.Query;
import com.aojun.common.base.util.Result;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

import static com.aojun.common.base.util.UserRequestUtil.getUserId;


/**
 * 系统用户
 *
 */
@Service
@Slf4j
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {

    private static final PasswordEncoder ENCODER = new BCryptPasswordEncoder();
    @Autowired
    private SysUserRoleService sysUserRoleService;
    @Autowired
    private SysRoleService sysRoleService;
    @Autowired
    private SysUserMapper sysUserMapper;
    @Autowired
    private RedisUtils redisUtils;
    @Autowired
    private SysRoleMapper roleMapper;


    /**
     * 系统用户简单分页查询
     *
     * @param params 系统用户
     * @return
     */
    @Override
    public IPage<SysUser> getSysUserPage(Map<String, Object> params) {
        // 判断当前登录人角色，
        Integer usrId = getUserId();
        IPage<SysUser> iPage = this.page(
                new Query<SysUser>(params).getPage(),
                new QueryWrapper<SysUser>()
                        .like(ObjectUtils.isNotEmpty(params.get("username")), "username", params.get("username"))
                        .or()
                        .like(ObjectUtils.isNotEmpty(params.get("username")), "realname", params.get("username"))
                        .eq(ObjectUtils.isNotEmpty(params.get("mobile")), "mobile", params.get("mobile"))
                        .ne("user_id", 1l)
                        .eq(usrId !=null,"user_id", usrId)
        );
        if (!CollectionUtils.isEmpty(iPage.getRecords())) {
            iPage.getRecords().forEach(user -> {
                user.setPassword(null);
                user.setRoleList(getRoleList(user));
            });
        }
        return iPage;

    }

    private List<SysRole> getRoleList(SysUser user) {
        List<SysRole> roleList = new ArrayList<>();
        Set<Integer> roleIdList = sysUserRoleService.queryRoleIdList(user.getUserId());
        Iterator<Integer> it = roleIdList.iterator();
        while (it.hasNext()) {
            for (int i = 0; i < roleIdList.size(); i++) {
                SysRole roleEntity = sysRoleService.getById(it.next());
                if (roleEntity != null) {
                    roleList.add(roleEntity);
                }
            }
        }
        return roleList;
    }

    @Override
    public SysUser getUserInfoById(Integer userId) {
        SysUser user = sysUserMapper.selectById(userId);
        if (user != null) {
            // 获取用户数据权限
            user.setRoleList(getRoleList(user));
            return user;
        }
        return null;
    }

    @Override
    @Transactional
    public Result saveUser(UserRequest request) {
        SysUser sysUser = new SysUser();
        sysUser.setUsername(request.getUsername());
        sysUser.setSex(request.getSex());
        sysUser.setMobile(request.getMobile());
        sysUser.setRealname(request.getRealname());

        sysUser.setCreateBy(getUserId());
        sysUser.setCreateTime(new Date());
        sysUser.setUpdateBy(getUserId());
        sysUser.setUpdateTime(new Date());
        sysUser.setStatus(Constant.TableStatus.ENABLE.getValue());
        sysUser.setDelFlag(Constant.TableDelFlag.NOTDEL.getValue());
        // 新增默认密码123456
        if (StrUtil.isBlank(request.getPassword())) {
            sysUser.setPassword(ENCODER.encode(Constant.DEFAULT_PASSWORD));
            sysUser.setEnablePwd(Constant.DEFAULT_PASSWORD);
        } else {
            sysUser.setEnablePwd(request.getPassword());
            sysUser.setPassword(ENCODER.encode(request.getPassword()));
        }

        // 1.保存用戶信息
        sysUserMapper.insert(sysUser);
        // 2.保存用户和角色关系
        if (CollectionUtils.isNotEmpty(request.getRoleIds())) {
            sysUserRoleService.saveUserRoleIds(sysUser.getUserId(), request.getRoleIds());
        }
        return Result.ok();
    }

    @Override
    @Transactional
    public Result updateByUserId(UserRequest request) {
        SysUser sysUser = new SysUser();
        sysUser.setUserId(request.getUserId());
        sysUser.setUsername(request.getUsername());
        sysUser.setSex(request.getSex());
        sysUser.setMobile(request.getMobile());
        sysUser.setRealname(request.getRealname());

        sysUser.setUpdateBy(getUserId());
        sysUser.setUpdateTime(new Date());
        // 1.更新用戶信息
        if (StringUtils.isNotBlank(request.getPassword())) {
            // 管理员重置密码
            sysUser.setEnablePwd(request.getPassword());
            sysUser.setPassword(ENCODER.encode(request.getPassword()));
        }
        sysUserMapper.update(sysUser,
                new QueryWrapper<SysUser>()
                        .eq("user_id", sysUser.getUserId())
        );
        // 4.保存用户和角色关系
        if (CollectionUtils.isNotEmpty(request.getRoleIds())) {
            sysUserRoleService.saveUserRoleIds(sysUser.getUserId(), request.getRoleIds());
            redisUtils.delete(sysUser.getUsername());
        }
        return Result.ok();
    }

    @Override
    public Result updatePwd(PasswordForm passwordForm) {
        SysUser user = sysUserMapper.selectById(passwordForm.getUserId());
        if (!ENCODER.matches(passwordForm.getPassword(), user.getPassword())) {
            return Result.failed("原密码输入不正确");
        }
        // 个人账户修改密码
        user.setEnablePwd(passwordForm.getNewPassword());
        user.setPassword(ENCODER.encode(passwordForm.getNewPassword()));
        user.setUpdateBy(getUserId());
        user.setUpdateTime(new Date());
        sysUserMapper.update(user,
                new QueryWrapper<SysUser>()
                        .eq("user_id", passwordForm.getUserId()));
        return Result.ok();

    }

}
