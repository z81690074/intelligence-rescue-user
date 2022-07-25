package com.aojun.user.api.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class SysDepart {

    @ApiModelProperty(value = "救助站id")
    private Integer departId;

    @ApiModelProperty(value = "救助站名称")
    private String departName;
}
