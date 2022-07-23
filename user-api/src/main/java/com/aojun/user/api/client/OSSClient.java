package com.aojun.user.api.client;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;



@FeignClient(name = "user-server",fallback = OSSClient.OssServiceFallbackImpl.class)
public interface OSSClient {

    /**
     * 上传文件
     *
     * @param multipartFile 文件名
     * @return String
     */
    @PostMapping(value = "/rpc/oss/upload")
    String uploadFile(@RequestParam("file") MultipartFile multipartFile);



    @Component
    @Slf4j
    class OssServiceFallbackImpl implements OSSClient {

        @Override
        public  String uploadFile(MultipartFile multipartFile) {
            log.error("上传文件异常:{}", multipartFile);
            return null;
        }

    }



}
