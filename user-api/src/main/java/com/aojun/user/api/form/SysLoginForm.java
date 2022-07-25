
package com.aojun.user.api.form;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;


/**
 * 登录表单
 */
@Getter
@Setter
public class SysLoginForm implements Serializable {

    private static final long serialVersionUID = 1L;

    private String username;
    private String password;
    private String captcha;
    private String randomStr;
    private String mobile;
    private String realname;
    private String macAddress;
    private String mobileCode;// 手机验证码
    private String openId;
    private Integer userId;
    private String ipAddress;



}
