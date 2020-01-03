package com.jieyun.opcdata.entity.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @CalssName MonitorItemsInfo
 * @Desc TODO
 * @Author huiKe
 * @email <link>754873891@qq.com </link>
 * @CreateDate 2019/12/31
 * @Version 1.0
 **/
@Data
public class MonitorItemsInfoReq extends ProgInfoDtoReq {

    @ApiModelProperty("要监控的组的信息")
    private List<GroupAndItemsInfoDto> groupsInfo;

    @ApiModelProperty("要监控的项的信息")
    private List<GroupAndItemsInfoDto> itemsInfo;

    private String clsId;

}
