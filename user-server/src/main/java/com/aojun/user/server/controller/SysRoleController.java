
package com.aojun.user.server.controller;

import com.aojun.common.base.util.Result;
import com.aojun.user.api.entity.SysRole;
import com.aojun.user.server.service.SysRoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


/**
 * 角色
 *
 */
@RestController
@RequestMapping("/role")
@Api(tags = "角色管理")
public class SysRoleController  {

    @Autowired
    private SysRoleService sysRoleService;

    /**
     * 简单分页查询
     *
     * @param params 对象
     * @return
     */
    @GetMapping("/list")
    @ApiOperation("分页list")
    public Result getSysRolePage(@RequestBody Map<String,Object> params) {
        return Result.ok(sysRoleService.getSysRolePage(params));
    }


    /**
     * 通过id查询单条记录
     *
     * @param roleId
     * @return R
     */
    @GetMapping("/getById/{roleId}")
    @ApiOperation("通过id查询单条记录")
    public Result getByRoleId(@PathVariable("roleId") Integer roleId) {
        return sysRoleService.getByRoleId(roleId);
    }


    /**
     * 新增记录
     *
     * @param sysRole
     * @return R
     */
    @PostMapping("/save")
    @ApiOperation("保存角色")
    public Result save(@RequestBody SysRole sysRole) {
        return sysRoleService.saveRole(sysRole);
    }

    /**
     * 修改记录
     *
     * @param sysRole
     * @return R
     */
    @PutMapping("/update")
    @ApiOperation("通过id更新记录")
    public Result update(@RequestBody SysRole sysRole) {
        return sysRoleService.updateRole(sysRole);
    }

    /**
     * 通过id删除一条记录
     *
     * @param roleId
     * @return R
     */
    @DeleteMapping("/removeById/{roleId}")
    @ApiOperation("通过id删除单条记录")
    public Result removeById(@PathVariable Integer roleId) {
        return Result.ok(sysRoleService.removeById(roleId));
    }


}
