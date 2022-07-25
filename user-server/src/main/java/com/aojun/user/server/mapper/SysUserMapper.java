
package com.aojun.user.server.mapper;

import com.aojun.common.base.util.Result;
import com.aojun.user.api.entity.SysUser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 系统用户
 */
@Mapper
public interface SysUserMapper extends BaseMapper<SysUser> {

    /**
     * 获取用户详情
     */
    SysUser getUserInfoById(@Param("userId") Integer userId);

    Result setRegistrationId(SysUser sysUser);


}
