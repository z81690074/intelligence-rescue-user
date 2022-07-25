
package com.aojun.user.api.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * 系统用户
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_user")
public class SysUser extends Model<SysUser> {

    @TableId(value = "user_id")
    private Integer userId;
    /**
     * 用户名
     */
    @ApiModelProperty("用户名")
    private String username;
    /**
     *
     */
    @ApiModelProperty("真实姓名")
    private String realname;
    /**
     * 密码
     */
    @ApiModelProperty("密码")
    private String password;

    /**
     * 明文密码
     */
    @ApiModelProperty("明文密码")
    private String enablePwd;

    /**
     * 头像
     */
    @ApiModelProperty("头像")
    private String headUrl;

    /**
     * 新密码
     */
    @TableField(exist = false)
    private String newPassword;
    /**
     * 性别 1:男,2:女,3:未知
     */
    @ApiModelProperty("性别")
    private Integer sex;
    /**
     * 手机号
     */
    @ApiModelProperty("手机号")
    private String mobile;

    /**
     * 登录ip
     */
    @ApiModelProperty("登录ip")
    private String ipAddress;

    /**
     * 登录时间
     */
    @ApiModelProperty("登录时间")
    private Date loginDate;

    /**
     * 状态  0：禁用   1：正常
     */
    @ApiModelProperty("状态")
    private Integer status;
    /**
     * 创建时间
     */
    @ApiModelProperty("创建时间")
    private Date createTime;
    /**
     * 创建人
     */
    @ApiModelProperty("创建人")
    private Integer createBy;
    /**
     * 更新时间
     */
    @ApiModelProperty("更新时间")
    private Date updateTime;
    /**
     * 更新人
     */
    @ApiModelProperty("更新人")
    private Integer updateBy;
    /**
     * 0:管理员，
     */
    @ApiModelProperty("类型")
    private Integer userType;

    /**
     * 手机唯一标识
     */
    @ApiModelProperty("手机唯一标识")
    private String registrationId;

    /**
     * 微信openId
     */
    @ApiModelProperty("微信openId")
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
     * 角色列表
     */
    @TableField(exist = false)
    private List<SysRole> roleList;

    /**
     * 角色id列表
     */
    @TableField(exist = false)
    private Set<Integer> roleIds;

    /**
     * 角色英文名称
     */
    @TableField(exist = false)
    private Set<String> roleEnglishNames;


}
