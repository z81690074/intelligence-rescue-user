package com.aojun.user.server.service.impl;

import com.aojun.common.base.util.MapUtils;
import com.aojun.user.api.entity.SysUserRole;
import com.aojun.user.server.service.SysUserRoleService;
import com.aojun.user.server.mapper.SysUserRoleMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

/**
 * 用户与角色对应关系
 */
@Service
public class SysUserRoleServiceImpl extends ServiceImpl<SysUserRoleMapper, SysUserRole> implements SysUserRoleService {

    @Override
    public void saveUserRoleIds(Integer userId, Set<Integer> roleIds) {
        //先删除用户与角色关系
        this.removeByMap(new MapUtils().put("user_id", userId));

        //保存用户与角色关系
        Set<SysUserRole> list = new HashSet<>(roleIds.size());
        roleIds.forEach(roleId ->{
           SysUserRole sysUserRole = new SysUserRole();
           sysUserRole.setUserId(userId);
           sysUserRole.setRoleId(roleId);
           list.add(sysUserRole);
        });
        this.saveBatch(list);
    }

    @Override
    public Set<Integer> queryRoleIdList(Integer userId) {
        return baseMapper.queryRoleIdList(userId);
    }

}
