package com.jieyun.opcdata.entity.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @CalssName ProgInfoDtoReq
 * @Desc OPCServer 请求相关实体
 * @Author huiKe
 * @email <link>754873891@qq.com </link>
 * @CreateDate 2019/12/30
 * @Version 1.0
 **/
@Data
public class ProgInfoDtoReq {
    @ApiModelProperty(value = "端口")
    private String host;

    @ApiModelProperty(value = "用户名")
    private String user;

    @ApiModelProperty(value = "密码")
    private String password;

    @ApiModelProperty(value = "域（不填，默认为localhost）")
    private String domain;

    @ApiModelProperty(value = "注册表中的值")
    private String clsId;

}
