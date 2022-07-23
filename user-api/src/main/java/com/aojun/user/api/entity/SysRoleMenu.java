
package com.aojun.user.api.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 角色与菜单对应关系
 *
 * @author liu jian
 * @date 2020-05-26 13:03:22
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_role_menu")
public class SysRoleMenu extends Model<SysRoleMenu> {

    /**
     * 主键id
     */
    @TableId(value = "id",type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 角色ID
     */
    private Long roleId;
    /**
     * 菜单ID
     */
    private Long menuId;

}
