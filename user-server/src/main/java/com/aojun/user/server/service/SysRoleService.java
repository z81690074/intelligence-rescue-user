package com.aojun.user.server.service;

import com.aojun.common.base.util.Result;
import com.aojun.user.api.entity.SysRole;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * 角色
 */
public interface SysRoleService extends IService<SysRole> {

    /**
     * 角色简单分页查询
     *
     * @param params 角色
     * @return
     */
    IPage<SysRole> getSysRolePage(Map<String,Object> params);

    /**
     * 保存角色
     */
    Result saveRole(SysRole sysRole);

    /**
     * 更新角色
     */
    Result updateRole(SysRole sysRole);

    /**
     * 获取角色详情
     */
    Result getByRoleId(Long roleId);


}
