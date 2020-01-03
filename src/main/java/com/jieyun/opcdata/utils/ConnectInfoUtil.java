package com.jieyun.opcdata.utils;

import com.jieyun.opcdata.entity.resp.ProgInfoDtoResp;
import lombok.extern.slf4j.Slf4j;
import org.openscada.opc.dcom.list.ClassDetails;
import org.openscada.opc.lib.common.ConnectionInformation;
import org.openscada.opc.lib.da.Server;
import org.openscada.opc.lib.list.Categories;
import org.openscada.opc.lib.list.Category;
import org.openscada.opc.lib.list.ServerList;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.Executors;

/**
 * @CalssName ConnectInfoUtil
 * @Desc 公共模块 -> 创建并连接OPC服务器
 * @Author huiKe
 * @email <link>754873891@qq.com </link>
 * @Date 2019-12-27
 * @Version 1.0
 **/
@Slf4j
public class ConnectInfoUtil {
    /**
     * 配置连接opc服务器的地址信息
     * 要求：需要的这些配置都要从Configman页面获取，可配置的
     *
     * @param host     192.168.186.151
     * @param user     OPCServer
     * @param password 754873891@qq.com
     * @param domain   ""
     * @param clsId    f8582cf2-88fb-11d0-b850-00c0f0104305
     * @return
     */
    public static Server getConnectionInformation(String host, String user, String password, String domain, String clsId) {
        log.info("create connection info start");
        ConnectionInformation ci = new ConnectionInformation();
        // ip
        ci.setHost(host);
        // 用户名
        ci.setUser(user);
        // 密码
        ci.setPassword(password);
        // 域（默认localhost）
        ci.setDomain(domain);
        // 应用，在注册表中相对应的CLSID值
        ci.setClsid(clsId);
        log.info("create OPC Server start");
        // 创建Server服务
        final Server server = new Server(ci, Executors.newSingleThreadScheduledExecutor());
        log.info("create OPC Server over");
        return server;
    }


    /**
     * 获取OPC应用程序中所有的 ClsId，ProgId 和 Description
     *
     * @param host     192.168.186.151
     * @param user     OPCServer
     * @param password 754873891@qq.com
     * @param domain   ""
     * @return ClsId ：应用在注册表中相对应的CLSID值
     * Grogid ：应用在注册表中对应的程序名称
     */
    public List<ProgInfoDtoResp> getAllPrgInfoInRegistry(String host, String user, String password, String domain) {
        List<ProgInfoDtoResp> clsIds = new ArrayList<>();
        try {
            log.info("开始连接OPCServer");
            ServerList serverList = new ServerList(host, user, password, domain);
            log.info("OPCServer 已经连接");
            //列举某Server下的所有OPC连接
            Collection<ClassDetails> detailsList = serverList.listServersWithDetails(
                    new Category[]{Categories.OPCDAServer10, Categories.OPCDAServer20}, new Category[]{});
            log.info(host + " 下 opc server的所有连接 {}", detailsList.toString());
            // 遍历opc服务下的opc连接列表
            for (ClassDetails details : detailsList) {
                ProgInfoDtoResp progInfoDtoResp = new ProgInfoDtoResp();
                progInfoDtoResp.setClsId(details.getClsId());
                progInfoDtoResp.setProgId(details.getProgId());
                progInfoDtoResp.setDesc(details.getDescription());
                clsIds.add(progInfoDtoResp);
                log.info("程序在注册表中的信息:{}", clsIds);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return clsIds;
    }
}
