package com.aojun.user.api.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * <p>
 * 日志表
 * </p>
 *
 * @author liujian
 * @since 2020-08-07
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_log")
public class SysLog extends Model<SysLog> {

    /**
     * 编号
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 日志类型
     */
    private String type;

    /**
     * 日志标题
     */
    private String title;

    /**
     * 操作IP地址
     */
    private String ipAddr;

    /**
     * 用户代理
     */
    private String userAgent;

    /**
     * 请求URI
     */
    private String requestUri;

    /**
     * 操作方式
     */
    private String method;

    /**
     * 操作提交的数据
     */
    private String params;

    /**
     * 执行时间
     */
    private Long time;

    /**
     * 异常信息
     */
    private String exception;

    /**
     * 创建者
     */
    private String createBy;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 删除标记
     */
    @TableLogic
    private Integer delFlag;


}
