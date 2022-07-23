package com.aojun.user.server.service.impl;

import cn.hutool.extra.ssh.JschUtil;
import cn.hutool.extra.ssh.Sftp;
import com.aojun.common.sftp.config.SFTPConfig;
import com.aojun.common.sftp.util.SFTPUtil;
import com.aojun.user.api.entity.SysOss;
import com.aojun.user.server.mapper.SysOssMapper;
import com.aojun.user.server.service.SysOssService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jcraft.jsch.Session;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.UUID;

import static com.aojun.common.base.constant.Constant.FTP_URL;
import static com.aojun.common.base.util.DateUtils.formatToDay;
import static com.aojun.common.base.util.UserRequestUtil.getUserId;
import static org.icepdf.core.pobjects.functions.Function.DOMAIN_NAME;


/**
 * 文件上传
 *
 */
@Service
@Slf4j
@RefreshScope
public class SysOssServiceImpl extends ServiceImpl<SysOssMapper, SysOss> implements SysOssService {
    @Autowired
    private SFTPConfig sftpConfig;

    @Override
    public String uploadFile(MultipartFile multipartFile) {
        // 获取文件名
        String multiFileName = multipartFile.getOriginalFilename();
        // 获取文件后缀
        String prefix = multiFileName.substring(multiFileName.lastIndexOf("."));
        String fileNme = UUID.randomUUID() + prefix;
        SFTPUtil sftp = new SFTPUtil(sftpConfig.getUsername(), sftpConfig.getPassword(), sftpConfig.getHost(), sftpConfig.getPort());
        sftp.login();
        try {
            Session session = JschUtil.getSession(sftpConfig.getHost(), sftpConfig.getPort(), sftpConfig.getUsername(), sftpConfig.getPassword());
            session.setTimeout(60000);
            Sftp sftpl = JschUtil.createSftp(session);
            // 获取当天日期文件夹是否存在
            String fileURL = FTP_URL + "/" + formatToDay();
            sftp.uploadFile(sftpl, fileURL, fileNme, multipartFile.getInputStream());
        } catch (Exception e) {
            e.printStackTrace();
            log.error("文件上传失败！" + fileNme);
        } finally {
            sftp.logout();
        }

        log.info("文件上传成功！");
        String url = DOMAIN_NAME + formatToDay() + "/" + fileNme;
        SysOss sysOss = new SysOss();
        sysOss.setUserId(getUserId());
        sysOss.setUrl(url);
        sysOss.setCreateDate(new Date());
        this.save(sysOss);
        return "";
    }
}
