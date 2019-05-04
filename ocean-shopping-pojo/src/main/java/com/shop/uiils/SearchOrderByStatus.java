package com.shop.uiils;

/**
 * @author Hpsyche
 * 根据订单类型查询订单的pojo
 */
public class SearchOrderByStatus {
    private Long userId;
    private Integer status;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
