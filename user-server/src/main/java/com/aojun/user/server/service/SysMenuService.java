package com.aojun.user.server.service;

import com.aojun.user.api.entity.SysMenu;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Set;

/**
 * 菜单管理
 */
public interface SysMenuService extends IService<SysMenu> {

    /**
     * 获取用户菜单列表
     */
    List<SysMenu> getUserMenuList(Integer userId);

    /**
     * 获取用户权限列表
     */
    Set<String> getUserPermissions(Integer userId);

    /**
     * 根据父菜单，查询子菜单
     *
     * @param parentId   父菜单ID
     * @param menuIdList 用户菜单ID
     */
    List<SysMenu> queryListParentId(Integer parentId, List<Integer> menuIdList);

}
