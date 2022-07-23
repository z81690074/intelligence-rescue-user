package com.aojun.user.server.controller;


import com.aojun.common.base.util.Result;
import com.aojun.user.server.service.SysLogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * <p>
 * 日志表 前端控制器
 * </p>
 */
@RestController
@RequestMapping("/log")
@Api(tags ="日志管理")
public class SysLogController {

    @Autowired
    private SysLogService sysLogService;

    /**
     * 分页查询
     * @param params 系统日志
     * @return
     */
    @PostMapping("/page")
    @ApiImplicitParams({@ApiImplicitParam(name="serviceId",value = "服务ID"),
            @ApiImplicitParam(name="createBy",value = "创建人")})
    public Result getLogPage(@RequestBody Map<String, Object> params) {
            return Result.ok(sysLogService.queryPage(params));
    }

}
