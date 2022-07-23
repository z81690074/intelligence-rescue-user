package com.aojun.user.api.client;

import com.aojun.user.api.entity.SysLog;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@FeignClient(contextId = "logClient", name = "system-module", fallback = LogClient.LogServiceFallbackImpl.class)
public interface LogClient {

    /**
     * 保存日志
     */
    @PostMapping("/rpc/log/saveLog")
    boolean saveLog(@RequestBody SysLog sysLog);

    @Component
    @Slf4j
    class LogServiceFallbackImpl implements LogClient {

        @Override
        public boolean saveLog(SysLog sysLog) {
            log.error("保存日志异常异常:{}", sysLog);
            return false;
        }
    }
}
