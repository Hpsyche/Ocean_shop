package com.shop.pojo;

/**
 * @author Hpsyche
 * 生成阿里订单的pojo
 */
public class CreateAliOrder {
    private String id;
    private String mobile;
    private String money;
    private String address;
    private String name;

    @Override
    public String toString() {
        return "CreateAliOrder{" +
                "id=" + id +
                ", mobile='" + mobile + '\'' +
                ", money=" + money +
                ", address='" + address + '\'' +
                ", name='" + name + '\'' +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
