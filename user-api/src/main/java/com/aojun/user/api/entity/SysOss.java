
package com.aojun.user.api.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * 文件上传
 *
 * @author liu jian
 * @date 2020-05-26 13:03:22
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_oss")
public class SysOss extends Model<SysOss> {

    /**
     *
     */
    @TableId(value = "id",type = IdType.ASSIGN_ID)
    private Long id;
    /**
     * URL地址
     */
    private String url;
    /**
     * 创建时间
     */
    private Date createDate;
    /**
     * 创建人
     */
    private Long userId;
    /**
     * 高
     */
    private Integer high;
    /**
     * 宽
     */
    private Integer width;

}
