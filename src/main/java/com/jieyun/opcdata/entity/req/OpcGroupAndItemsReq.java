package com.jieyun.opcdata.entity.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @CalssName Opc
 * @Desc 获取OPC服务器中Groups和Items 的Id的请求实体
 * @Author huiKe
 * @email <link>754873891@qq.com </link>
 * @CreateDate 2019/12/31
 * @Version 1.0
 **/
@Data
public class OpcGroupAndItemsReq extends ProgInfoDtoReq {
    @ApiModelProperty("程序在注册表中的Id")
    private String clsId;
}
