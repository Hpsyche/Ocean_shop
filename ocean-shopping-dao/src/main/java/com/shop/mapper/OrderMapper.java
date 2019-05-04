package com.shop.mapper;

import com.shop.pojo.CreateAliOrder;
import com.shop.pojo.OsOrder;
import com.shop.uiils.SearchOrderByStatus;

import java.util.List;

/**
 * @author Hpsyche
 */
public interface OrderMapper {
    /**
     * 创建商品订单
     * @param osOrder
     */
    void createOrder(OsOrder osOrder);

    /**
     * 根据id查询商品订单信息
     * @param orderId
     * @return
     */
    List<OsOrder> selectByOrderId(Long orderId);

    /**
     * 通过用户id查询订单列表
     * @param pojo
     * @return
     */
    List<OsOrder> selectOrdersByUserId(SearchOrderByStatus pojo);

    void updateOrder(CreateAliOrder order);

    void updateOrderStatus(String id);
}
