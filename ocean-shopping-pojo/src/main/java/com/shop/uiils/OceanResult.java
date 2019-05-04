package com.shop.uiils;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.Serializable;

/**
 * @author Hpsyche
 */
public class OceanResult {
    private Object data;
    private String msg;
    private Integer status;

    public static OceanResult ok() {
        return new OceanResult(null);
    }

    public OceanResult(Object data, String msg, Integer status) {
        this.data = data;
        this.msg = msg;
        this.status = status;
    }

    public static OceanResult build(Object data, String msg,Integer status ) {
        return new OceanResult(data, msg,status );
    }
    public static OceanResult ok(Object data) {
        return new OceanResult(data);
    }

    public OceanResult(Object data) {
        this.status = 200;
        this.msg = "OK";
        this.data = data;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
