package com.aojun.user.server.service;

import com.aojun.user.api.entity.SysOss;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;

/**
 * 文件上传
 */
public interface SysOssService extends IService<SysOss> {

    String uploadFile(MultipartFile multipartFile);

}
