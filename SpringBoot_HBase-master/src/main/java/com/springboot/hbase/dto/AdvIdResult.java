package com.springboot.hbase.dto;

/*
* 第一次查询返回的结果格式
 */
public class AdvIdResult {
    private String code;
    private String msg;
    private String advId;
    private Double latitude;
    private Double longitude;

    public AdvIdResult(){}

    public AdvIdResult(String code, String msg, String advId, Double latitude, Double longitude) {
        this.code = code;
        this.msg = msg;
        this.advId = advId;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setAdvId(String advId) {
        this.advId = advId;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public String getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public String getAdvId() {
        return advId;
    }

    public Double getLatitude() {
        return latitude;
    }

    public Double getLongitude() {
        return longitude;
    }
}
