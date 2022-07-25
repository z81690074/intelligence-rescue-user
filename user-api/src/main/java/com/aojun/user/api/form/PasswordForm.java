
package com.aojun.user.api.form;

import com.aojun.common.base.util.validation.QueryGroup;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * 密码表单
 *
 */
@Data
public class PasswordForm {

    private Integer userId;

    /**
     * 原密码
     */
    @NotNull(groups = {QueryGroup.class}, message = "原密码不能为空")
    private String password;
    /**
     * 新密码
     */
    @NotNull(groups = {QueryGroup.class}, message = "新密码不能为空")
    private String newPassword;

}
