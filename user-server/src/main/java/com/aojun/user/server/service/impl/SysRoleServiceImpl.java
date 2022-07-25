package com.aojun.user.server.service.impl;

import com.aojun.common.base.constant.Constant;
import com.aojun.common.base.util.Result;
import com.aojun.common.base.util.Query;
import com.aojun.user.server.service.SysRoleMenuService;
import com.aojun.user.api.entity.SysRole;
import com.aojun.user.server.mapper.SysRoleMapper;
import com.aojun.user.server.mapper.SysRoleMenuMapper;
import com.aojun.user.server.service.SysRoleService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;

import static com.aojun.common.base.util.UserRequestUtil.getUserId;


/**
 * 角色
 */
@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements SysRoleService {

    @Autowired
    private SysRoleMapper sysRoleMapper;

    @Autowired
    private SysRoleMenuService roleMenuService;

    @Autowired
    private SysRoleMenuMapper sysRoleMenuMapper;

    /**
     * 角色简单分页查询
     *
     * @param params 角色
     * @return
     */
    @Override
    public IPage<SysRole> getSysRolePage(Map<String,Object> params) {
        IPage<SysRole> iPage = this.page(
                new Query<SysRole>(params).getPage(),
                new QueryWrapper<SysRole>()
                        .like(ObjectUtils.isNotEmpty(params.get("roleName")), "role_name", params.get("roleName"))
        );
        return iPage;
    }

    @Override
    public Result saveRole(SysRole sysRole) {
        // 1. 保存角色
        sysRole.setCreateBy(getUserId());
        sysRole.setCreateTime(new Date());
        sysRole.setUpdateBy(getUserId());
        sysRole.setUpdateTime(new Date());
        sysRole.setStatus(Constant.TableStatus.ENABLE.getValue());
        sysRole.setDelFlag(Constant.TableDelFlag.NOTDEL.getValue());
        sysRoleMapper.insert(sysRole);
        if(CollectionUtils.isNotEmpty(sysRole.getMenuIdList())){
            roleMenuService.saveRoleMenu(sysRole.getRoleId(),sysRole.getMenuIdList());
        }
        return Result.ok();
    }

    @Override
    public Result updateRole(SysRole sysRole) {
        // 1. 保存角色
        sysRole.setUpdateBy(getUserId());
        sysRole.setUpdateTime(new Date());
        sysRoleMapper.update(sysRole,new QueryWrapper<SysRole>().eq("role_id",sysRole.getRoleId()));
        if(CollectionUtils.isNotEmpty(sysRole.getMenuIdList())){
            roleMenuService.saveRoleMenu(sysRole.getRoleId(),sysRole.getMenuIdList());
        }
        return Result.ok();
    }

    @Override
    public Result getByRoleId(Integer roleId) {
        SysRole sysRole = sysRoleMapper.selectById(roleId);
        if(CollectionUtils.isNotEmpty(sysRoleMenuMapper.getMenuIdsByRoleId(roleId))) {
            sysRole.setMenuIdList(sysRoleMenuMapper.getMenuIdsByRoleId(roleId));
        }
        return Result.ok(sysRole);
    }


}
