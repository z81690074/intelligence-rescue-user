
package com.aojun.user.server.controller;

import com.aojun.common.base.configuration.CommonConfig;
import com.aojun.common.base.util.Result;
import com.aojun.user.api.entity.SysOss;
import com.aojun.user.server.service.SysOssService;
import com.google.common.collect.Maps;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;



/**
 * 文件上传
 *
 */
@RestController
@RequestMapping("/oss")
@Api(tags = "上传管理")
@Slf4j
@RefreshScope
public class SysOssController {

    @Autowired
    private SysOssService sysOssService;

    @Autowired
    private CommonConfig commonConfig;

    /**
     * 通过id查询单条记录
     *
     * @param id
     * @return R
     */
    @GetMapping("/getById/{id}")
    @ApiOperation("通过id查询单条记录")
    public Result<SysOss> getById(@PathVariable("id") Integer id) {
        return Result.ok(sysOssService.getById(id));
    }


    /**
     * 新增记录
     *
     * @param multipartFile
     * @return R
     */
    @PostMapping("/save")
    @ApiOperation("保存")
    @Transactional
    public Result save(@RequestParam("file") MultipartFile multipartFile) {
        if(multipartFile.isEmpty()){
            return Result.failed("上传文件不能为空");
        }
        if (multipartFile.getSize() > commonConfig.getFileSize()) {
            return Result.failed("上传文件不能超过5M");
        }
        Map<String,Object> map = Maps.newHashMap();
        map.put("url",sysOssService.uploadFile(multipartFile));
        return Result.ok(map);
    }

    /**
     * 修改记录
     *
     *
     *
     *
     * @param sysOss
     * @return R
     */
    @PutMapping("/update")
    @ApiOperation("通过id更新单条记录")
    public Result update(@RequestBody SysOss sysOss) {
        return Result.ok(sysOssService.updateById(sysOss));
    }

    /**
     * 通过id删除一条记录
     *
     * @param id
     * @return R
     */
    @DeleteMapping("/removeById/{id}")
    @ApiOperation("通过id删除单条记录")
    public Result removeById(@PathVariable Integer id) {
        return Result.ok(sysOssService.removeById(id));
    }


}
