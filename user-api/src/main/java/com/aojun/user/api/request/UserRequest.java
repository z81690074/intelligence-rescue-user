package com.aojun.user.api.request;

import com.aojun.user.api.entity.SysRole;
import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

@Data
public class UserRequest implements Serializable {
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

    @ApiModelProperty("手机号")
    private String mobile;

    @ApiModelProperty("性别")
    private Integer sex;

    /**
     * 角色列表
     */
    @ApiModelProperty("角色ID列表")
    private Set<Integer> roleIds;

}
