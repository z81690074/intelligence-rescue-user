package com.aojun.user.server.rpc;


import com.aojun.user.api.entity.SysLog;
import com.aojun.user.server.service.SysLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rpc/log")
public class LogRpc {

    @Autowired
    private SysLogService sysLogService;

    /**
     * 保存日志
     */
    @PostMapping("/saveLog")
    public boolean saveLog(@RequestBody SysLog sysLog){
        return sysLogService.save(sysLog);
    }
}
