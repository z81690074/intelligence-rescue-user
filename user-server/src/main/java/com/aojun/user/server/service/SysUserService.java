package com.aojun.user.server.service;

import com.aojun.user.api.entity.SysUser;
import com.aojun.common.base.util.Result;
import com.aojun.user.api.form.PasswordForm;
import com.aojun.user.api.request.UserRequest;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;


public interface SysUserService extends IService<SysUser> {

    /**
     * 系统用户简单分页查询
     *
     * @param params 系统用户
     * @return
     */
    IPage<SysUser> getSysUserPage(Map<String,Object> params);

    /**
     * 通过id查询单条记录
     *
     * @param userId
     * @return
     */
    SysUser getUserInfoById(Integer userId);

    /**
     * 保存功能
     */
    Result saveUser(UserRequest request);

    /**
     * 更新用戶
     */
    Result updateByUserId(UserRequest request);
    /**
     *  个人密码修改
     */
    Result updatePwd(PasswordForm passwordForm);

}
