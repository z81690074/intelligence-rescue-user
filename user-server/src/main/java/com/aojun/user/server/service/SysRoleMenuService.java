package com.aojun.user.server.service;

import com.aojun.user.api.entity.SysRoleMenu;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Set;

/**
 * 角色与菜单对应关系
 */
public interface SysRoleMenuService extends IService<SysRoleMenu> {

    void saveRoleMenu(Integer roleId, Set<Integer> menuIdList);

}
