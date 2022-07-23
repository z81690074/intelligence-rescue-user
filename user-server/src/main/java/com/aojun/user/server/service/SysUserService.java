package com.aojun.user.server.service;

import com.aojun.user.api.entity.SysUser;
import com.aojun.common.base.util.Result;
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
    SysUser getUserInfoById(Long userId);

    /**
     * 保存功能
     */
    Result saveUser(SysUser sysUser);

    /**
     * 更新用戶
     */
    Result updateByUserId(SysUser sysUser);
    /**
     *  个人密码修改
     */
    Result updatePwd(SysUser sysUser);

    Result setRegistrationId(SysUser sysUser);
}
