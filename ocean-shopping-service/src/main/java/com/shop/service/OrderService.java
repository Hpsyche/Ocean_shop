package com.shop.service;

import com.shop.pojo.CreateAliOrder;
import com.shop.pojo.OsOrder;
import com.shop.uiils.OrderResult;

import java.util.List;

/**
 * @author Hpsyche
 */
public interface OrderService {
    /**
     * 创建商品订单
     * @param order
     */
    void createOrder(OsOrder order);

    /**
     * 根据订单id查询订单
     * @param orderId
     * @return
     */
    OrderResult selectOrderById(Long orderId);

    /**
     * 通过用户id查询订单列表
     * @param userId
     * @return
     */
    List<OsOrder> selectOrdersByUserIdAndStatus(Long userId,Integer status);

    void updateOrder(CreateAliOrder order);

    void updateOrderStatus(String id);
}
