
package com.aojun.user.server.mapper;

import com.aojun.user.api.entity.SysMenu;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 菜单管理
 *
 * @author liu jian
 * @date 2020-05-26 13:03:22
 */
@Mapper
public interface SysMenuMapper extends BaseMapper<SysMenu> {

    /**
     * 清空菜单
     */
    void deleteMenu();


}
