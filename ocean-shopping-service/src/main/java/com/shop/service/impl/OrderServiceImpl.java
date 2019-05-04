package com.shop.service.impl;

import com.shop.mapper.OrderMapper;
import com.shop.pojo.CreateAliOrder;
import com.shop.pojo.OsOrder;
import com.shop.pojo.OsOrderItem;
import com.shop.service.OrderItemService;
import com.shop.service.OrderService;
import com.shop.uiils.OrderResult;
import com.shop.uiils.SearchOrderByStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author Hpsyche
 */
@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private OrderItemService orderItemService;
    @Override
    public void createOrder(OsOrder order) {
        order.setPaymentType(1);
        order.setPostFee("0");
        order.setStatus(1);
        Date date=new Date();
        order.setCreateTime(date);
        order.setUpdateTime(date);
        order.setConsignTime(date);
        order.setBuyerLinkman("");
        order.setBuyerTel("");
        order.setBuyerAddress("");
        orderMapper.createOrder(order);
    }

    @Override
    public OrderResult selectOrderById(Long orderId) {
        //1.查询订单信息
        OsOrder order = orderMapper.selectByOrderId(orderId).get(0);
        //2.在orderItem中查询所有order_id为order.orderId的
        List<OsOrderItem> orderItems=orderItemService.selectOrderItemsByOrderId(orderId);
        //3.写一个pojo-utils，用来封装order和List<orderItem>
        OrderResult result=new OrderResult();
        result.setOrder(order);
        result.setOrderItems(orderItems);
        return result;
    }

    @Override
    public List<OsOrder> selectOrdersByUserIdAndStatus(Long userId,Integer status) {
        SearchOrderByStatus pojo=new SearchOrderByStatus();
        pojo.setUserId(userId);
        pojo.setStatus(status);
        return orderMapper.selectOrdersByUserId(pojo);
    }

    @Override
    public void updateOrder(CreateAliOrder order) {
        orderMapper.updateOrder(order);
    }

    @Override
    public void updateOrderStatus(String id) {
        orderMapper.updateOrderStatus(id);
    }
}
