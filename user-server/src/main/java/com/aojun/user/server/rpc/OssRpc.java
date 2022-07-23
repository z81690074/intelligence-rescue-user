package com.aojun.user.server.rpc;

import com.aojun.user.server.service.SysOssService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;


@RestController
@RequestMapping("/rpc/oss")
public class OssRpc {

    @Autowired
    private SysOssService sysOssService;


    @PostMapping(value = "/upload")
    String uploadFile(@RequestParam("file") MultipartFile multipartFile){
        return sysOssService.uploadFile(multipartFile);
    }
}
