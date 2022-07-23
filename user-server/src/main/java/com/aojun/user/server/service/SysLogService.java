package com.aojun.user.server.service;


import com.aojun.user.api.entity.SysLog;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 日志表 服务类
 * </p>
 */
public interface SysLogService extends IService<SysLog> {

    IPage<SysLog> queryPage(Map<String, Object> params);
}
