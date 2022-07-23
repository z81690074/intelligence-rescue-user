package com.aojun.user.server.service.impl;

import com.aojun.user.api.entity.SysMenu;
import com.aojun.user.server.mapper.SysMenuMapper;
import com.aojun.user.server.mapper.SysUserRoleMapper;
import com.aojun.user.server.service.SysMenuService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * 菜单管理
 *
 */
@Service
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu> implements SysMenuService {
    @Autowired
    private SysUserRoleMapper sysUserRoleMapper;

    @Override
    public List<SysMenu> queryListParentId(Long parentId, List<Long> menuIdList) {
        List<SysMenu> menuList = this.list(new QueryWrapper<SysMenu>().eq("parent_id", parentId));
        if (menuIdList == null) {
            return menuList;
        }
        List<SysMenu> userMenuList = new ArrayList<>();
        for (SysMenu menu : menuList) {
            if (menuIdList.contains(menu.getMenuId())) {
                userMenuList.add(menu);
            }
        }
        return userMenuList;
    }


    @Override
    public List<SysMenu> getUserMenuList(Long userId) {
        //系统管理员，拥有最高权限
        if (userId.equals("admin")) {
            return getAllMenuList(null);
        }

        //用户菜单列表
        List<Long> menuIdList = sysUserRoleMapper.queryAllMenuId(userId);
        return getAllMenuList(menuIdList);
    }

    @Override
    public Set<String> getUserPermissions(Long userId) {
        List<String> permsList;

        //系统管理员，拥有最高权限
        if (userId.equals("admin")) {
            List<SysMenu> menuList = list();
            permsList = new ArrayList<>(menuList.size());
            for (SysMenu menu : menuList) {
                permsList.add(menu.getPerms());
            }
        } else {
            permsList = sysUserRoleMapper.queryAllPerms(userId);
        }
        //用户权限列表
        Set<String> permsSet = new HashSet<>();
        for (String perms : permsList) {
            if (StringUtils.isBlank(perms)) {
                continue;
            }
            permsSet.addAll(Arrays.asList(perms.trim().split(",")));
        }
        return permsSet;
    }

    /**
     * 获取所有菜单列表
     */
    private List<SysMenu> getAllMenuList(List<Long> menuIdList) {
        //查询根菜单列表
        List<SysMenu> menuList = queryListParentId((long) 0, menuIdList);
        //递归获取子菜单
        getMenuTreeList(menuList, menuIdList);

        return menuList;
    }

    /**
     * 递归
     */
    private List<SysMenu> getMenuTreeList(List<SysMenu> menuList, List<Long> menuIdList) {
        List<SysMenu> subMenuList = new ArrayList<>();

        for (SysMenu entity : menuList) {
            //目录
//            if (entity.getType() == Constant.MenuType.CATALOG.getValue()) {
                entity.setList(getMenuTreeList(queryListParentId(entity.getMenuId(), menuIdList), menuIdList));
//            }
            subMenuList.add(entity);
        }

        return subMenuList;
    }

}
