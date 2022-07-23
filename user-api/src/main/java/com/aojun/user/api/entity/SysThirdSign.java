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
 * 第三方签名认证信息
 * </p>
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_third_sign")
public class SysThirdSign extends Model<SysThirdSign> {

    @TableId(value = "id",type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 第三方接口ip 白名单
     */
    private String ipAddress;

    /**
     * 签名秘钥
     */
    private String secretKey;

    /**
     * 第三方标识
     */
    private String thirdType;

    /**
     * 第三方公司名称
     */
    private String thirdName;

    /**
     * 创建人
     */
    private Long createBy;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 删除状态  0：存在，1：删除
     */
    @TableLogic
    private Integer delFlag;


}
