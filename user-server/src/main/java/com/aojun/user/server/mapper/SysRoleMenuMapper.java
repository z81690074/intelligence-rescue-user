
package com.aojun.user.server.mapper;

import com.aojun.user.api.entity.SysRoleMenu;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

/**
 * 角色与菜单对应关系
 *
 */
@Mapper
public interface SysRoleMenuMapper extends BaseMapper<SysRoleMenu> {

    List<String> queryMenuIdListByRoleIds(@Param("roleIds") List<Integer> roleIds);

    Set<Integer> getMenuIdsByRoleId(@Param("roleId")Integer roleId);


}
