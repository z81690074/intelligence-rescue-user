
package com.aojun.user.api.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;
import java.util.Set;

/**
 * 角色
 *
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_role")
public class SysRole extends Model<SysRole>{

    /**
     * 角色id
     */
    @TableId(value = "role_id")
    private Integer roleId;
    /**
     * 角色名称
     */
    private String roleName;
    /**
     * 英文名
     */
    private String englishName;
    /**
     * 状态  0：禁用   1：正常
     */
    private Integer status;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 创建人
     */
    private Integer createBy;
    /**
     * 更新时间
     */
    private Date updateTime;
    /**
     * 更新人
     */
    private Integer updateBy;

    /**
     * 删除状态 0：正常 ，1：删除
     */
    @TableLogic
    private Integer delFlag;

    /**
     * 角色菜单列表
     */
    @TableField(exist = false)
    private Set<Integer> MenuIdList;

}
