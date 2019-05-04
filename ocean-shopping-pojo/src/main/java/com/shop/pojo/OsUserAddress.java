package com.shop.pojo;

import java.util.Date;

/**
 * @author Hpsyche
 */
public class OsUserAddress {
    private Long addressId;
    private Long userId;
    private String receiverName;
    private String receiverMobile;
    private String receiverProvince;
    private String receiverCity;
    private String receiverArea;
    private String receiverAddress;
    private String receiverZip;
    private Date receiverCreated;
    private Date receiverUpdated;

    @Override
    public String toString() {
        return "OsUserAddress{" +
                "addressId=" + addressId +
                ", userId=" + userId +
                ", receiverName='" + receiverName + '\'' +
                ", receiverMobile='" + receiverMobile + '\'' +
                ", receiverProvince='" + receiverProvince + '\'' +
                ", receiverCity='" + receiverCity + '\'' +
                ", receiverArea='" + receiverArea + '\'' +
                ", receiverAddress='" + receiverAddress + '\'' +
                ", receiverZip='" + receiverZip + '\'' +
                ", receiverCreated='" + receiverCreated + '\'' +
                ", receiverUpdated='" + receiverUpdated + '\'' +
                '}';
    }

    public Long getAddressId() {
        return addressId;
    }

    public void setAddressId(Long addressId) {
        this.addressId = addressId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    public String getReceiverMobile() {
        return receiverMobile;
    }

    public void setReceiverMobile(String receiverMobile) {
        this.receiverMobile = receiverMobile;
    }

    public String getReceiverProvince() {
        return receiverProvince;
    }

    public void setReceiverProvince(String receiverProvince) {
        this.receiverProvince = receiverProvince;
    }

    public String getReceiverCity() {
        return receiverCity;
    }

    public void setReceiverCity(String receiverCity) {
        this.receiverCity = receiverCity;
    }

    public String getReceiverArea() {
        return receiverArea;
    }

    public void setReceiverArea(String receiverArea) {
        this.receiverArea = receiverArea;
    }

    public String getReceiverAddress() {
        return receiverAddress;
    }

    public void setReceiverAddress(String receiverAddress) {
        this.receiverAddress = receiverAddress;
    }

    public String getReceiverZip() {
        return receiverZip;
    }

    public void setReceiverZip(String receiverZip) {
        this.receiverZip = receiverZip;
    }

    public Date getReceiverCreated() {
        return receiverCreated;
    }

    public void setReceiverCreated(Date receiverCreated) {
        this.receiverCreated = receiverCreated;
    }

    public Date getReceiverUpdated() {
        return receiverUpdated;
    }

    public void setReceiverUpdated(Date receiverUpdated) {
        this.receiverUpdated = receiverUpdated;
    }
}
