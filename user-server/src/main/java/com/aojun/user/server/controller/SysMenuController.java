
package com.aojun.user.server.controller;

import cn.hutool.core.util.StrUtil;
import com.aojun.user.api.entity.SysMenu;
import com.aojun.user.api.entity.SysRole;
import com.aojun.user.server.mapper.SysRoleMapper;
import com.aojun.user.server.mapper.SysUserMapper;
import com.aojun.user.api.entity.SysUser;
import com.aojun.user.server.service.SysMenuService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.aojun.common.base.util.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

import static com.aojun.common.base.util.UserRequestUtil.getRoleIds;
import static com.aojun.common.base.util.UserRequestUtil.getUserId;


/**
 * 菜单管理
 *
 */
@RestController
@RequestMapping("/menu")
@Api(tags = "菜单管理")
public class SysMenuController {

    @Autowired
    private SysMenuService sysMenuService;

    @Autowired
    private SysRoleMapper sysRoleMapper;

    @Autowired
    private SysUserMapper sysUserMapper;

    /**
     * 导航菜单
     */
    @PostMapping("/nav")
    @ApiOperation("导航菜单")
    public Result nav() {
//        List<SysMenu> menuList = sysMenuService.getUserMenuList(getUserId());
        Set<String> permissions = sysMenuService.getUserPermissions(getUserId());
        // 过滤掉当前登录人去除权限 del_perms
        SysUser sysUser = sysUserMapper.selectById(getUserId());
        if (sysUser != null && StrUtil.isNotBlank(sysUser.getDelPerms())) {
          permissions.removeAll(Arrays.stream(sysUser.getDelPerms().trim().split(",")).collect(Collectors.toSet()));
        }
        Map<String, Object> map = new HashMap<>();
        // 获取角色list
        List<SysRole> roleList = sysRoleMapper.selectList(new QueryWrapper<SysRole>()
                .in(CollectionUtils.isNotEmpty(getRoleIds()), "role_id", getRoleIds())
        );
//        map.put("menuList", menuList);
        map.put("permissions", permissions);
        map.put("roleList", roleList);
        return Result.ok(map);
    }

    /**
     * 所有菜单列表
     */
    @PostMapping("/list")
    @ApiOperation("所有菜单列表")
    public Result menuList() {
        return Result.ok(sysMenuService.getUserMenuList(getUserId()));
    }

    /**
     * 选择菜单(添加、修改菜单)
     */
    @PostMapping("/select")
    @ApiOperation("选择菜单(添加、修改菜单)")
    public Result<List<SysMenu>> select() {
        //查询列表数据
        List<SysMenu> menuList = sysMenuService.list(
                new QueryWrapper<SysMenu>().ne("type", 2).orderByAsc("order_num"));

        //添加顶级菜单
        SysMenu root = new SysMenu();
        root.setMenuId((long) 0);
        root.setName("根节点");
        root.setParentId((long) -1);
        root.setOpen(true);
        menuList.add(root);
        return Result.ok(menuList);
    }


    /**
     * 通过id查询单条记录
     *
     * @param menuId
     * @return R
     */
    @GetMapping("/getById/{menuId}")
    @ApiOperation("通过id查询单条记录")
    public Result<SysMenu> getById(@PathVariable("menuId") Long menuId) {
        return Result.ok(sysMenuService.getById(menuId));
    }


    /**
     * 新增记录
     *
     * @param sysMenu
     * @return R
     */
    @PostMapping("/save")
    @ApiOperation("保存")
    public Result save(@RequestBody SysMenu sysMenu) {
        sysMenuService.save(sysMenu);
        return Result.ok(sysMenu.getMenuId());
    }

    /**
     * 修改记录
     *
     * @param sysMenu
     * @return R
     */
    @PutMapping("/update")
    @ApiOperation("更新")
    public Result update(@RequestBody SysMenu sysMenu) {
        return Result.ok(sysMenuService.updateById(sysMenu));
    }

    /**
     * 通过id删除一条记录
     *
     * @param menuId
     * @return Result
     */
    @DeleteMapping("/removeById/{menuId}")
    @ApiOperation("通过id删除单条记录")
    public Result removeById(@PathVariable("menuId") Long menuId) {
        return Result.ok(sysMenuService.removeById(menuId));
    }


}
