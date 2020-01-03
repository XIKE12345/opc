package com.jieyun.opcdata.service.impl;


import com.jieyun.opcdata.entity.req.GroupAndItemsInfoDto;
import com.jieyun.opcdata.entity.req.MonitorItemsInfoReq;
import com.jieyun.opcdata.entity.req.OpcGroupAndItemsReq;
import com.jieyun.opcdata.entity.req.ProgInfoDtoReq;
import com.jieyun.opcdata.entity.resp.ProgInfoDtoResp;
import com.jieyun.opcdata.service.OpcService;
import com.jieyun.opcdata.utils.ConnectInfoUtil;
import lombok.extern.slf4j.Slf4j;
import org.jinterop.dcom.common.JIException;
import org.jinterop.dcom.core.JIVariant;
import org.openscada.opc.lib.common.NotConnectedException;
import org.openscada.opc.lib.da.*;
import org.openscada.opc.lib.da.browser.Branch;
import org.openscada.opc.lib.da.browser.Leaf;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @CalssName OpcServerImpl
 * @Desc OPC 接口的实现类
 * @Author huiKe
 * @email <link>754873891@qq.com </link>
 * @CreateDate 2019/12/30
 * @Version 1.0
 **/
@Slf4j
@Service
public class OpcServiceImpl implements OpcService {

    static Server server = null;

    private Map<Object, Object> globalMap = new HashMap<>(16);


    @Override
    public boolean uploadConfigInfo(ProgInfoDtoReq progInfoDtoReq) {
        try {
            server = ConnectInfoUtil.getConnectionInformation(progInfoDtoReq.getHost(), progInfoDtoReq.getUser(),
                    progInfoDtoReq.getPassword(), progInfoDtoReq.getDomain(), progInfoDtoReq.getClsId());
            server.connect();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 获取OPC应用在注册表中的相关信息
     *
     * @param progInfoDtoReq
     * @return
     */
    @Override
    public List<ProgInfoDtoResp> getRegistryInfo(ProgInfoDtoReq progInfoDtoReq) {
        List<ProgInfoDtoResp> progInfoDtoRespList = new ArrayList<>();
        try {
            if (!ObjectUtils.isEmpty(progInfoDtoReq)) {
                String host = progInfoDtoReq.getHost();
                String user = progInfoDtoReq.getUser();
                String password = progInfoDtoReq.getPassword();
                String domain = progInfoDtoReq.getDomain();
                // 获取OPC应用程序在注册表中的
                ConnectInfoUtil connectInfoUtil = new ConnectInfoUtil();
                progInfoDtoRespList = connectInfoUtil.getAllPrgInfoInRegistry(host, user, password, domain);
            }
        } catch (Exception e) {
            log.error("Get program information in the registry exception ", e);
        }
        return progInfoDtoRespList;
    }

    /**
     * 获取OPC服务中的所有Groups和Items
     *
     * @return
     */
    @Override
    public Map<Object, Object> getOpcGroupsAndItemsId(OpcGroupAndItemsReq opcGroupAndItemsReq) {
        if (!ObjectUtils.isEmpty(opcGroupAndItemsReq)) {
            // 创建连接
            ConnectInfoUtil connectInfoUtil = new ConnectInfoUtil();
            log.info("start connect opcServer and create Server");
            try {
                // 连接服务:问题，连接1次还是连接多次，目前这种是连接多次，每次请求接口的时候都要进行连接OPC服务（慢）
                log.info("server connect over");
                dumpTree(server.getTreeBrowser().browse(), 0);
            } catch (JIException e) {
                log.error("JIException error", e);
            } catch (UnknownHostException e) {
                log.error("unknown host error", e);
            }
        }
        return globalMap;
    }

    /**
     * 使用递归算法将组织结构（组 -> 项）打印出来
     *
     * @param branch
     * @param level
     */
    String firstLeafBranch = "";
    private void dumpTree(final Branch branch, final int level) {
        String name = branch.getName();
        log.info("-----branch.getName---is---{}", name);
        final StringBuilder sb = new StringBuilder();
        for (int i = 0; i < level; i++) {
            sb.append("  ");
        }
        // 将打印前加空格
        final String indent = sb.toString();
        // 打印所有分支下的子分支的名称和项的Id
        List<Object> firstBreachList = new ArrayList<>();
        if (!StringUtils.isEmpty(name)) {
            firstLeafBranch = name;
        }
        for (final Leaf leaf : branch.getLeaves()) {
            if (!StringUtils.isEmpty(leaf.getItemId())) {
                System.out.println(indent + "Leaf: " + leaf.getName() + " [" + leaf.getItemId() + "]");
                firstBreachList.add(leaf.getItemId());
            }
        }
        // 在此处增加一层
        Map<Object, Object> sim = new HashMap<>(16);



        globalMap.put(firstLeafBranch, firstBreachList);
        log.info("-----globalMap---first---{}", globalMap.toString());

        // 递归打印所有Branch分支
        for (final Branch subBranch : branch.getBranches()) {
            System.out.println(indent + "Branch: " + subBranch.getName());
            dumpTree(subBranch, level + 1);
        }

    }

    /**
     * 选择需要监测的Items(法1,直接获取)
     *
     * @param monitorItemsInfoReq
     * @return 问题：如何将获取到的数据取到并展示给前台
     */
    @Override
    public JIVariant monitorOpcItems(MonitorItemsInfoReq monitorItemsInfoReq) {

        JIVariant value = null;
        if (!ObjectUtils.isEmpty(monitorItemsInfoReq)) {
            List<GroupAndItemsInfoDto> itemsInfoDtos = monitorItemsInfoReq.getItemsInfo();
            List<GroupAndItemsInfoDto> groupsInfo = monitorItemsInfoReq.getGroupsInfo();
            Map<String, String> valueMap = new HashMap<>(16);
            try {
                for (GroupAndItemsInfoDto groupAndItemsInfoDto : groupsInfo) {
                    for (GroupAndItemsInfoDto itemsInfoDto : itemsInfoDtos) {
                        String itemId = itemsInfoDto.getItemId();
                        String groupId = groupAndItemsInfoDto.getGroupId();
                        Group group = server.addGroup(groupId);
                        group.setActive(true);
                        group = server.findGroup(groupId);
                        //"Saw-toothed Waves.Int2"
                        final Item item = group.addItem(itemId);
                        item.setActive(true);
//                        final Map<String, Item> items = group.addItems(itemId);
                        //item的编码
                        item.getId();
                        //item的值
                        ItemState state = item.read(false);
                        value = state.getValue();
                        log.info("----------{}", value);
                        //时间
                        state.getTimestamp();
                        // 质量
                        state.getQuality();
                    }
                }
            } catch (JIException | UnknownHostException e) {
                e.printStackTrace();
            } catch (AddFailedException e) {
                e.printStackTrace();
            } catch (NotConnectedException e) {
                e.printStackTrace();
            } catch (DuplicateGroupException e) {
                e.printStackTrace();
            } catch (UnknownGroupException e) {
                e.printStackTrace();
            }
        }
        return value;
    }


    /**
     * 选择需要监测的Items(法2，使用AccessBase 同步展示)
     *
     * @param monitorItemsInfoReq
     * @return 问题：如何将获取到的数据取到并展示给前台
     */
//    @Override
//    public String monitorOpcItems(MonitorItemsInfoReq monitorItemsInfoReq) {
//
//        if (!ObjectUtils.isEmpty(monitorItemsInfoReq)) {
//            List<GroupAndItemsInfoDto> itemsInfoDtos = monitorItemsInfoReq.getItemsInfo();
//            // 创建连接
//            ConnectInfoUtil connectInfoUtil = new ConnectInfoUtil();
//            log.info("start connect opcServer and create Server");
//
//            Map<String, String> valueMap = new HashMap<>(16);
//            for (GroupAndItemsInfoDto itemsInfoDto : itemsInfoDtos) {
//                String itemId = itemsInfoDto.getItemId();
//                try {
//                    final AccessBase access = new SyncAccess(server, 500);
//
//                    access.addItem(itemId, new DataCallbackDumper());
//
//                    access.bind();
//
//                    Thread.sleep(10 * 1000);
//
//                    access.unbind();
//
//                } catch (JIException | InterruptedException | UnknownHostException e) {
//                    e.printStackTrace();
//                } catch (AddFailedException e) {
//                    e.printStackTrace();
//                } catch (NotConnectedException e) {
//                    e.printStackTrace();
//                } catch (DuplicateGroupException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//        return null;
//    }

}