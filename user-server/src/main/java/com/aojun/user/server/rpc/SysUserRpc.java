package com.aojun.user.server.rpc;

import cn.hutool.core.util.ArrayUtil;
import com.aojun.user.api.dto.UserInfo;
import com.aojun.user.api.entity.SysUser;
import com.aojun.user.server.service.SysUserRoleService;
import com.aojun.user.server.service.SysUserService;
import com.aojun.user.server.mapper.SysUserRoleMapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.aojun.common.base.exception.RRException;
import com.aojun.common.base.util.Result;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Set;


@RestController
@RequestMapping("/rpc/user")
public class SysUserRpc {

    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private SysUserRoleService sysUserRoleService;
    @Autowired
    private SysUserRoleMapper sysUserRoleMapper;

    /**
     * 获取登录人信息
     *
     * @param username
     * @return
     */
    @GetMapping("/getByName/{username}")
    public Result<UserInfo> findUserByUsername(@PathVariable("username") String username) {
        UserInfo userInfo = new UserInfo();
        //获取当前用户
        SysUser sysUser = sysUserService.getOne(new QueryWrapper<SysUser>().eq("username", username));
        if (sysUser == null) {
            throw new RRException("当前用户为空");
        }
        //获取用户权限
//        Set<String> permiss = sysMenuService.getUserPermissions(sysUser.getUserId());
        //获取当前角色list
        Set<Integer> roleIdList = sysUserRoleService.queryRoleIdList(sysUser.getUserId());
        //获取角色名称
        Set<String> roleNamesList = sysUserRoleMapper.queryRoleEnglishNamesList(sysUser.getUserId());
        userInfo.setRoleEnglishNames(roleNamesList);
        userInfo.setSysUser(sysUser);
//        if(!CollectionUtils.isEmpty(permiss)){
//            userInfo.setPermissions(ArrayUtil.toArray(permiss, String.class));
//        }
        if (!CollectionUtils.isEmpty(roleIdList)) {
            userInfo.setRoles(ArrayUtil.toArray(roleIdList, Integer.class));
        }
        return Result.ok(userInfo);
    }

    /**
     * 验证用户名是否唯一
     */
    @GetMapping("/validateUsername/{username}")
    public boolean validateUsername(@PathVariable("username") String username) {
        int count = sysUserService.count(new QueryWrapper<SysUser>().eq(StringUtils.isNotBlank(username), "username", username));
        return count == 0;
    }

}
