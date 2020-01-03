package com.jieyun.opcdata.controller;

import com.jieyun.opcdata.entity.req.MonitorItemsInfoReq;
import com.jieyun.opcdata.entity.req.OpcGroupAndItemsReq;
import com.jieyun.opcdata.entity.req.ProgInfoDtoReq;
import com.jieyun.opcdata.entity.resp.ProgInfoDtoResp;
import com.jieyun.opcdata.service.OpcService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @CalssName OpcDataController
 * @Desc OPC服务
 * @Author huiKe
 * @email <link>754873891@qq.com </link>
 * @CreateDate 2019/12/30
 * @Version 1.0
 **/
@RestController
public class OpcDataController {

    private OpcService opcService;

    @Autowired
    public OpcDataController(OpcService opcService) {
        this.opcService = opcService;
    }

    /**
     * 获取OPC应用程序在注册表中的信息（Get program information in the registry）
     */
    @PostMapping("opc/get/registry/info")
    public List<ProgInfoDtoResp> getPrigInfoInResist(@RequestBody ProgInfoDtoReq progInfoDtoReq) {
        return opcService.getRegistryInfo(progInfoDtoReq);
    }

    /**
     * 登陆OPC服务
     *
     * @param progInfoDtoReq 登陆opc服务的请求实体
     */
    @PostMapping("/login/server")
    public boolean uploadConfigInfo(@RequestBody ProgInfoDtoReq progInfoDtoReq) {
        return opcService.uploadConfigInfo(progInfoDtoReq);
    }

    /**
     * 获取OPC服务中所有的group和items的Id
     *
     * @return 返回opc服务中的tree结构
     */
    @PostMapping("opc/get/group")
    public Map<Object, Object> getOpcGroupAndItemsId(@RequestBody OpcGroupAndItemsReq opcGroupAndItemsReq) {
        return opcService.getOpcGroupsAndItemsId(opcGroupAndItemsReq);
    }

    /**
     * 选取需要监测的opc点位，获取相应的点位数据值
     *
     * @return 返回监控的opc点位值
     */
    @PostMapping("opc/monitor/Items")
    public String monitorOpcItems(@RequestBody MonitorItemsInfoReq monitorItemsInfoReq) {
        return opcService.monitorOpcItems(monitorItemsInfoReq);
    }

}
