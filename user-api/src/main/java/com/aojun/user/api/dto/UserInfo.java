
package com.aojun.user.api.dto;

import com.aojun.user.api.entity.SysUser;
import lombok.Data;

import java.io.Serializable;
import java.util.Set;


@Data
public class UserInfo implements Serializable {
    /**
     * 用户基本信息
     */
    private SysUser sysUser;
    /**
     * 权限标识集合
     */
    private String[] permissions;

    /**
     * 角色集合
     */
    private Integer[] roles;

    /**
     * 角色英文名称
     */
    private Set<String> roleEnglishNames;

    /**
     * 头像
     */
    private String headUrl;


}
