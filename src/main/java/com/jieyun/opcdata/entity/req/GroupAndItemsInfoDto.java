package com.jieyun.opcdata.entity.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @CalssName GroupAndItemsInfoDto
 * @Desc 要监控的位点对象
 * @Author huiKe
 * @email <link>754873891@qq.com </link>
 * @CreateDate 2019/12/31
 * @Version 1.0
 **/
@Data
public class GroupAndItemsInfoDto {
    @ApiModelProperty(value = "要监控的项Id", notes = "不能空")
    private String itemId;
    @ApiModelProperty("要监控的项Name")
    private String itemName;
    @ApiModelProperty(value = "要监控的组Id", notes = "不能空")
    private String groupId;
    @ApiModelProperty("要监控的组名称")
    private String groupName;

}
