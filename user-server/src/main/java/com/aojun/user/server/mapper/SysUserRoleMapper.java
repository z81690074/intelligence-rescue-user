
package com.aojun.user.server.mapper;

import com.aojun.user.api.entity.SysUserRole;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

/**
 * 用户与角色对应关系
 */
@Mapper
public interface SysUserRoleMapper extends BaseMapper<SysUserRole> {

    /**
     * 根据用户ID，获取角色ID列表
     */
    Set<Integer> queryRoleIdList(@Param("userId")Integer userId);

    /**
     * 根据用户ID，获取角色name列表
     */
    Set<String> queryRoleEnglishNamesList(@Param("userId")Integer userId);

    /**
     * 查询用户的所有权限
     *
     * @param userId 用户ID
     */
    List<String> queryAllPerms(@Param("userId")Integer userId);

    /**
     * 查询用户的所有菜单ID
     */
    List<Integer> queryAllMenuId(@Param("userId") Integer userId);


}
