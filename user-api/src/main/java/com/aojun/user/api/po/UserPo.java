package com.aojun.user.api.po;

import lombok.Data;

@Data
public class UserPo {
    /**
     * 用户名
     */
    private String username;
    /**
     * 姓名
     */
    private String realname;

    /**
     * 性别 1:男,2:女,3:未知
     */
    private Integer sex;
    /**
     * 手机号
     */
    private String mobile;
}
