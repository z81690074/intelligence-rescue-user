
package com.aojun.user.api.form;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;


/**
 * 登录表单
 *
 * @author liujian
 * @since 2.0.0 2018-10-25
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
    private Long usrMacId;// 用户授权id
    private String openId;
    private Long userId;
    private String ipAddress;



}
