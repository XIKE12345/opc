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
    public List<ProgInfoDtoResp> getProgInfoInRegist(@RequestBody ProgInfoDtoReq progInfoDtoReq) {
        return opcService.getRegistryInfo(progInfoDtoReq);
    }

    /**
     * 登陆OPC服务
     *
     * @param progInfoDtoReq
     */
    @PostMapping("/login/server")
    public boolean uploadConfigInfo(@RequestBody ProgInfoDtoReq progInfoDtoReq) {
        return opcService.uploadConfigInfo(progInfoDtoReq);
    }

    /**
     * 获取OPC服务中所有的group和items的Id
     *
     * @return
     */
    @PostMapping("opc/get/group")
    public Map<Object, Object> getOpcGroupAndItemsId(@RequestBody OpcGroupAndItemsReq opcGroupAndItemsReq) {
        Map<Object, Object> opcGroupsAndItemsId = opcService.getOpcGroupsAndItemsId(opcGroupAndItemsReq);
        return opcGroupsAndItemsId;
    }

    /**
     * 选取需要监测的opc点位，获取相应的点位数据值
     *
     * @return
     */
    @PostMapping("opc/monitor/Items")
    public String monitorOpcItems(@RequestBody MonitorItemsInfoReq monitorItemsInfoReq) {
        String s = opcService.monitorOpcItems(monitorItemsInfoReq);
        return s;
    }

}
