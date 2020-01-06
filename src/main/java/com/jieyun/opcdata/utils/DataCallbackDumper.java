package com.jieyun.opcdata.utils;

import lombok.extern.slf4j.Slf4j;
import org.jinterop.dcom.common.JIException;
import org.openscada.opc.lib.da.DataCallback;
import org.openscada.opc.lib.da.Item;
import org.openscada.opc.lib.da.ItemState;

/**
 * @CalssName DataCallbackDumper
 * @Desc 对获取到的数值进行处理
 * @Author huiKe
 * @email <link>754873891@qq.com </link>
 * @CreateDate 2019/12/31
 * @Version 1.0
 **/
@Slf4j
public class DataCallbackDumper implements DataCallback {
    @Override
    public void changed(Item item, ItemState itemState) {
        log.info("Item:{}, Value: {}, Timestamp: {}, Quality: {}", item.getId(), itemState.getValue(), itemState.getTimestamp(), itemState.getQuality());
        try {
             VariantDumper.dumpValue("\t", itemState.getValue());
        } catch (final JIException e) {
            e.printStackTrace();
        }

    }
}
