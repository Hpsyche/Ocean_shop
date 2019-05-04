package com.shop.uiils;

/**
 * @author Hpsyche
 * 查询cid中的前num个商品
 */
public class GetItemsByCidAndNum {
    private Integer num;
    private Long cid;

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public Long getCid() {
        return cid;
    }

    public void setCid(Long cid) {
        this.cid = cid;
    }

    public GetItemsByCidAndNum(Integer num, Long cid) {
        this.num = num;
        this.cid = cid;
    }
}
