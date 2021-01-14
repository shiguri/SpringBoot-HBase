package com.springboot.hbase.dto;

import com.springboot.hbase.entity.Advertisement;

/*
* 第二次查询返回的结果格式。
 */

public class AdResult {
    private String code = null;
    private String msg = null;
    private Advertisement advertisement = null;

    public AdResult(){}

    public AdResult(String code, String msg, Advertisement advertisement){
        this.code = code;
        this.msg = msg;
        this.advertisement = advertisement;
    }

    public String getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public Advertisement getAdvertisement() {
        return advertisement;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setAdvertisement(Advertisement advertisement) {
        this.advertisement = advertisement;
    }
}
