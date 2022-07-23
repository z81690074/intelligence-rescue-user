package com.aojun.user.server.service.impl;

import com.aojun.common.base.util.Query;
import com.aojun.user.api.entity.SysLog;
import com.aojun.user.server.mapper.SysLogMapper;
import com.aojun.user.server.service.SysLogService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * <p>
 * 日志表 服务实现类
 */
@Service
public class SysLogServiceImpl extends ServiceImpl<SysLogMapper, SysLog> implements SysLogService {

    @Override
    public IPage<SysLog> queryPage(Map<String, Object> params) {
        String serviceId = (String)params.get("serviceId");
        String createBy = (String)params.get("createBy");
        return this.page(
                new Query<SysLog>(params).getPage(),
                new QueryWrapper<SysLog>()
                        .like(StringUtils.isNotBlank(serviceId),"service_id", serviceId)
                        .like(StringUtils.isNotBlank(createBy),"create_by", createBy)
                        .orderByDesc("create_time")
        );
    }
}
