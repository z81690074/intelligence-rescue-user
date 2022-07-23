package com.aojun.user.server.service.impl;

import com.aojun.user.api.entity.SysRoleMenu;
import com.aojun.user.server.mapper.SysRoleMenuMapper;
import com.aojun.user.server.service.SysRoleMenuService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * 角色与菜单对应关系
 *
 */
@Service
public class SysRoleMenuServiceImpl extends ServiceImpl<SysRoleMenuMapper, SysRoleMenu> implements SysRoleMenuService {

    @Autowired
    private SysRoleMenuMapper sysRoleMenuMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveRoleMenu(Long roleId, Set<Long> menuIdList) {
        //先删除角色与菜单关系
        sysRoleMenuMapper.delete(new QueryWrapper<SysRoleMenu>().eq("role_id",roleId));

        if (CollectionUtils.isEmpty(menuIdList)) {
            return;
        }

        //保存角色与菜单关系
        List<SysRoleMenu> list = new ArrayList<>(menuIdList.size());
        for (Long menuId : menuIdList) {
            SysRoleMenu sysRoleMenu = new SysRoleMenu();
            sysRoleMenu.setMenuId(menuId);
            sysRoleMenu.setRoleId(roleId);
            list.add(sysRoleMenu);
        }
        this.saveBatch(list);
    }
}
