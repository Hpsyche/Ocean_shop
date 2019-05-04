package com.shop.uiils;

import com.shop.pojo.OsOrder;
import com.shop.pojo.OsOrderItem;

import java.util.List;

/**
 * @author Hpsyche
 * 封装order和orderItems
 */
public class OrderResult {
    private OsOrder order;
    private List<OsOrderItem> orderItems;

    public OsOrder getOrder() {
        return order;
    }

    public void setOrder(OsOrder order) {
        this.order = order;
    }

    public List<OsOrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OsOrderItem> orderItems) {
        this.orderItems = orderItems;
    }
}
