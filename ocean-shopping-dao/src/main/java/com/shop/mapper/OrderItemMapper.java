package com.shop.mapper;

import com.shop.pojo.OsOrderItem;

import java.util.List;

/**
 * @author Hpsyche
 */
public interface OrderItemMapper {
    /**
     * 创建商品订单详情
     * @param orderItem
     * @return
     */
    void createOrderItem(OsOrderItem orderItem);

    /**
     * 查询指定orderId的orderItem
     * @param orderId
     * @return
     */
    List<OsOrderItem> selectByOrderId(Long orderId);
}
