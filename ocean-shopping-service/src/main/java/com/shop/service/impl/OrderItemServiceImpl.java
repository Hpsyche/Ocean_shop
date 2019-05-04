package com.shop.service.impl;

import com.shop.mapper.ItemMapper;
import com.shop.mapper.OrderItemMapper;
import com.shop.pojo.OsItem;
import com.shop.pojo.OsOrderItem;
import com.shop.service.OrderItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

/**
 * @author Hpsyche
 */
@Service
public class OrderItemServiceImpl implements OrderItemService {
    @Autowired
    private OrderItemMapper orderItemMapper;
    @Autowired
    private ItemMapper itemMapper;
    @Override
    public void createOrderItem(OsOrderItem osOrderItem) {
        osOrderItem.setId(String.format("%010d",Math.abs(UUID.randomUUID().toString().hashCode())));
        Long itemId=Long.valueOf(osOrderItem.getItemId());
        List<OsItem> list = itemMapper.selectById(itemId);
        OsItem item=list.get(0);
        osOrderItem.setTitle(item.getTitle());
        osOrderItem.setPrice(item.getPrice());
        osOrderItem.setTotalFee(osOrderItem.getNum()*item.getPrice());
        osOrderItem.setPicPath(item.getImage());
        orderItemMapper.createOrderItem(osOrderItem);
    }

    @Override
    public List<OsOrderItem> selectOrderItemsByOrderId(Long orderId) {
        return orderItemMapper.selectByOrderId(orderId);
    }
}
