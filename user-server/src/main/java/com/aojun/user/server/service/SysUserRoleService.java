package com.aojun.user.server.service;

import com.aojun.user.api.entity.SysUserRole;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Set;

/**
 * 用户与角色对应关系
 */
public interface SysUserRoleService extends IService<SysUserRole> {

    void saveUserRoleIds(Long userId, Set<Long> roleIds);

    /**
     * 根据用户ID，获取角色ID列表
     */
    Set<Long> queryRoleIdList(Long userId);
}
