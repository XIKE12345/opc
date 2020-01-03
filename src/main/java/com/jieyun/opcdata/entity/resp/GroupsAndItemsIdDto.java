package com.jieyun.opcdata.entity.resp;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @CalssName GroupsAndItemsIdDto
 * @Desc OPC服务中Groups and Items 的Id
 * @Author huiKe
 * @email <link>754873891@qq.com </link>
 * @CreateDate 2019/12/31
 * @Version 1.0
 **/
@Data
public class GroupsAndItemsIdDto {

    @ApiModelProperty("项ID")
    private String itemId;

    @ApiModelProperty("项Name")
    private String itemName;

    @ApiModelProperty("父Id")
    private String branchId;

    @ApiModelProperty("父名称")
    private String branchName;
}
