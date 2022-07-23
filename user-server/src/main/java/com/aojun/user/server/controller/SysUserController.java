package com.aojun.user.server.controller;

import com.aojun.user.api.entity.SysUserRole;
import com.aojun.user.server.service.SysUserRoleService;
import com.aojun.user.server.service.SysUserService;
import com.aojun.user.api.entity.SysUser;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.aojun.common.base.util.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


/**
 * 系统用户
 *
 */
@RestController
@RequestMapping("/user")
@Api(tags = "用户管理")
@Slf4j
public class SysUserController {

    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private SysUserRoleService userRoleService;

    /**
     * 简单分页查询
     *
     * @param maps
     * @return
     */
    @PostMapping("/list")
    @ApiOperation("用户分页list")
    public Result<IPage<SysUser>> getSysUserPage(@RequestBody Map<String, Object> maps) {
        return Result.ok(sysUserService.getSysUserPage(maps));
    }


    /**
     * 通过id查询单条记录
     *
     * @param userId
     * @return Result
     */
    @GetMapping("/info/{userId}")
    @ApiOperation("通过id查询单条记录")
    public Result<SysUser> getById(@PathVariable("userId") Long userId) {
        return Result.ok(sysUserService.getUserInfoById(userId));
    }


    /**
     * 新增记录
     *
     * @param sysUser
     * @return R
     */
    @PostMapping("/save")
    @ApiOperation("保存")
    public Result save(@RequestBody SysUser sysUser) {
        return sysUserService.saveUser(sysUser);
    }

    /**
     * 修改记录
     *
     * @param sysUser
     * @return R
     */
    @PutMapping("/update")
    @ApiOperation("更新")
    public Result update(@RequestBody SysUser sysUser) {
        return sysUserService.updateByUserId(sysUser);
    }

    /**
     * 个人密码修改
     */
    @PutMapping("/updatePwd")
    @ApiOperation("个人密码修改")
    public Result updatePwd(@RequestBody SysUser sysUser) {
        return sysUserService.updatePwd(sysUser);
    }

    /**
     * 设置账户鉴权标识
     */
    @PutMapping("/updateAuthMark")
    @ApiOperation("设置账户鉴权标识")
    public Result updateAuthMark(@RequestBody SysUser sysUser) {
        sysUserService.update(sysUser, new QueryWrapper<SysUser>().eq("user_id", sysUser.getUserId()));
        return Result.ok();
    }


    /**
     * 通过id删除一条记录
     *
     * @param userId
     * @return R
     */
    @DeleteMapping("/removeById/{userId}")
    @ApiOperation("通过id删除")
    public Result removeById(@PathVariable Long userId) {
        return Result.ok(sysUserService.removeById(userId));
    }

    /**
     * 验证用户名是否唯一
     */
    @GetMapping("/validateUsername")
    @ApiOperation("验证用户名是否唯一")
    public boolean validateUsername(@RequestParam("username") String username) {
        int count = sysUserService.count(new QueryWrapper<SysUser>().eq(StringUtils.isNotBlank(username), "username", username));
        return count == 0;
    }

    /**
     * 批量插入user
     */
    @ApiOperation("批量插入user")
    @PostMapping("/insertUserBatch")
    public boolean insertUserBatch(@RequestBody List<SysUser> userList) {
        return sysUserService.saveBatch(userList);
    }

    /**
     * 批量插入userRole
     */
    @ApiOperation("批量插入userRole")
    @PostMapping("/insertUserRoleBatch")
    public boolean insertUserRoleBatch(@RequestBody List<SysUserRole> userRoles) {
        return userRoleService.saveBatch(userRoles);
    }


}
