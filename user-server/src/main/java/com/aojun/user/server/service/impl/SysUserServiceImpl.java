package com.aojun.user.server.service.impl;

import cn.hutool.core.util.StrUtil;
import com.aojun.common.base.constant.Constant;
import com.aojun.common.redis.util.RedisUtils;
import com.aojun.user.server.mapper.SysRoleMapper;
import com.aojun.user.server.service.SysUserRoleService;
import com.aojun.user.api.entity.SysRole;
import com.aojun.user.api.entity.SysUser;
import com.aojun.user.api.entity.SysUserRole;
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

import static com.aojun.common.base.util.UserRequestUtil.getBizId;
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
        // 判断当前登录人角色，若是企业 biz 添加过滤条件
        Long usrId = null;
        if (getBizId() != null){
            usrId = getUserId();
        }
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
                SysUser sysUser = sysUserMapper.getUserInfoById(user.getUserId());
                if (sysUser != null) {
                    user.setBizIds(sysUser.getBizIds());
                    user.setBizNames(sysUser.getBizNames());
                }
            });
        }
        return iPage;

    }

    private List<SysRole> getRoleList(SysUser user) {
        List<SysRole> roleList = new ArrayList<>();
        Set<Long> roleIdList = sysUserRoleService.queryRoleIdList(user.getUserId());
        Iterator<Long> it = roleIdList.iterator();
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
    public SysUser getUserInfoById(Long userId) {
        SysUser user = sysUserMapper.selectById(userId);
        if (user != null) {
            // 获取用户数据权限
            user.setRoleList(getRoleList(user));
            return user;
        }
        return user;
    }

    @Override
    @Transactional
    public Result saveUser(SysUser sysUser) {
        sysUser.setCreateBy(getUserId());
        sysUser.setCreateTime(new Date());
        sysUser.setUpdateBy(getUserId());
        sysUser.setUpdateTime(new Date());
        sysUser.setStatus(Constant.TableStatus.ENABLE.getValue());
        sysUser.setDelFlag(Constant.TableDelFlag.NOTDEL.getValue());
        // 新增默认密码123456
        if (StrUtil.isBlank(sysUser.getPassword())) {
            sysUser.setPassword(ENCODER.encode(Constant.DEFAULT_PASSWORD));
            sysUser.setEnablePwd(Constant.DEFAULT_PASSWORD);
        } else {
            sysUser.setEnablePwd(sysUser.getPassword());
            sysUser.setPassword(ENCODER.encode(sysUser.getPassword()));
        }

        // 1.保存用戶信息
        sysUserMapper.insert(sysUser);
        // 2.保存用户和角色关系
        if (CollectionUtils.isNotEmpty(sysUser.getRoleIds())) {
            sysUserRoleService.saveUserRoleIds(sysUser.getUserId(), sysUser.getRoleIds());
        }
        return Result.ok();
    }

    @Override
    @Transactional
    public Result updateByUserId(SysUser sysUser) {
        sysUser.setUpdateBy(getUserId());
        sysUser.setUpdateTime(new Date());
        // 1.更新用戶信息
        if (StringUtils.isNotBlank(sysUser.getPassword())) {
            // 管理员重置密码
            sysUser.setEnablePwd(sysUser.getPassword());
            sysUser.setPassword(ENCODER.encode(sysUser.getPassword()));
        }
        sysUserMapper.update(sysUser,
                new QueryWrapper<SysUser>()
                        .eq("user_id", sysUser.getUserId())
        );
        // 4.保存用户和角色关系
        if (CollectionUtils.isNotEmpty(sysUser.getRoleIds())) {
            sysUserRoleService.saveUserRoleIds(sysUser.getUserId(), sysUser.getRoleIds());
//            redisUtils.delete(sysUser.getUsername());
        }
        return Result.ok();
    }

    @Override
    public Result updatePwd(SysUser sysUser) {
        SysUser user = sysUserMapper.selectById(sysUser.getUserId());
        if (!ENCODER.matches(sysUser.getPassword(), user.getPassword())) {
            return Result.failed("原密码输入不正确");
        }
        // 个人账户修改密码
        sysUser.setEnablePwd(sysUser.getNewPassword());
        sysUser.setPassword(ENCODER.encode(sysUser.getNewPassword()));
        sysUser.setUpdateBy(getUserId());
        sysUser.setUpdateTime(new Date());
        sysUserMapper.update(sysUser,
                new QueryWrapper<SysUser>()
                        .eq("user_id", sysUser.getUserId()));
        return Result.ok();

    }

    @Override
    public Result setRegistrationId(SysUser sysUser) {
        return sysUserMapper.setRegistrationId(sysUser);
    }

}
