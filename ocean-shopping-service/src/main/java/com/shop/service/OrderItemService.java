package com.shop.service;

import com.shop.pojo.OsOrderItem;

import java.util.List;

/**
 * @author Hpsyche
 */
public interface OrderItemService {
    /**
     * 创建订单详情信息
     * @param osOrderItem
     */
    void createOrderItem(OsOrderItem osOrderItem);

    /**
     * 查询所有指定orderId的orderItem
     * @param orderId
     * @return
     */
    List<OsOrderItem> selectOrderItemsByOrderId(Long orderId);
}
