package com.jieyun.opcdata.service;


import com.jieyun.opcdata.entity.req.MonitorItemsInfoReq;
import com.jieyun.opcdata.entity.req.OpcGroupAndItemsReq;
import com.jieyun.opcdata.entity.req.ProgInfoDtoReq;
import com.jieyun.opcdata.entity.resp.ProgInfoDtoResp;

import java.util.List;
import java.util.Map;

/**
 * @CalssName OpcServer
 * @Desc TODO
 * @Author huike
 * @email <link>754873891@qq.com </link>
 * @CreateDate 2019/12/30
 * @Version 1.0
 **/
public interface OpcService {

    /**
     * 上传OPC服务相关信息
     *
     * @param progInfoDtoReq
     */
    boolean uploadConfigInfo(ProgInfoDtoReq progInfoDtoReq);

    /**
     * 获取应用程序在注册表中的信息
     *
     * @param progInfoDtoReq
     * @return opc服务在注册表中的信息
     */
    List<ProgInfoDtoResp> getRegistryInfo(ProgInfoDtoReq progInfoDtoReq);

    /**
     * 获取OPC服务的Groups和Items
     *
     * @param opcGroupAndItemsReq
     * @return
     */
    Map<Object, Object> getOpcGroupsAndItemsId(OpcGroupAndItemsReq opcGroupAndItemsReq);

    /**
     * 选取要监测的OPC Items点位
     *
     * @param monitorItemsInfoReq
     * @return
     */
    String monitorOpcItems(MonitorItemsInfoReq monitorItemsInfoReq);

}
