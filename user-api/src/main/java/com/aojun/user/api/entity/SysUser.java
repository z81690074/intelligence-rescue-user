
package com.aojun.user.api.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * 系统用户
 *
 * @author liu jian
 * @date 2020-05-26 13:03:22
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_user")
public class SysUser extends Model<SysUser> {

    @TableId(value = "user_id", type = IdType.ASSIGN_ID)
    private Long userId;
    /**
     * 用户名
     */
    private String username;
    /**
     *
     */
    private String realname;
    /**
     * 密码
     */
    private String password;

    /**
     * 明文密码
     */
    private String enablePwd;

    /**
     * 头像
     */
    private String headUrl;

    /**
     * 新密码
     */
    @TableField(exist = false)
    private String newPassword;
    /**
     * 性别 1:男,2:女,3:未知
     */
    private Integer sex;
    /**
     * 手机号
     */
    private String mobile;

    /**
     * 登录ip
     */
    private String ipAddress;

    /**
     * 登录时间
     */
    private Date loginDate;

    /**
     * 状态  0：禁用   1：正常
     */
    private Integer status;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 创建人
     */
    private Long createBy;
    /**
     * 更新时间
     */
    private Date updateTime;
    /**
     * 更新人
     */
    private Long updateBy;
    /**
     * 0:管理员，
     */
    private Integer userType;

    /**
     * 手机唯一标识
     */
    private String registrationId;

    /**
     * 用户授权标识 0：已授权 ，1：未授权
     */
    private Integer authMark;

    /**
     * 微信openId
     */
    private String openId;

    /**
     * 删除状态 0：正常 ，1：删除
     */
    @TableLogic
    private Integer delFlag;

    /**
     * 过滤掉权限标识
     */
    private String delPerms;


    /**
     * 企业ids列表
     */
    @TableField(exist = false)
    private String bizIds;
    /**
     * 渠道商ids列表
     */
    @TableField(exist = false)
    private String agentIds;

    /**
     * 企业名称s
     */
    @TableField(exist = false)
    private String bizNames;

    /**
     * 角色列表
     */
    @TableField(exist = false)
    private List<SysRole> roleList;

    /**
     * 角色id列表
     */
    @TableField(exist = false)
    private Set<Long> roleIds;


    /**
     * 角色英文名称
     */
    @TableField(exist = false)
    private Set<String> roleEnglishNames;


}
