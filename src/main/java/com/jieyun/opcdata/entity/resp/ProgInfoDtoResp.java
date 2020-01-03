package com.jieyun.opcdata.entity.resp;


import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @CalssName ProgInfoDto
 * @Desc 应用程序相关的实体对象
 * @Author huike
 * @email <link>754873891@qq.com </link>
 * @CreateDate 2019/12/30
 * @Version 1.0
 **/
@Data
public class ProgInfoDtoResp {

    @ApiModelProperty(value = "应用程序在注册表中的Id")
    private String clsId;

    @ApiModelProperty(value = "应用程序Id")
    private String progId;

    @ApiModelProperty("程序描述")
    private String desc;
}
