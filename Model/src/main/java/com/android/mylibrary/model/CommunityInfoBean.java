package com.android.mylibrary.model;

/**
 * Created by liujunqin on 2016/6/20.
 */
public class CommunityInfoBean {

    //省份
    private String provice;
    //城市
    private String city;
    //县区
    private String zone;
    //街道
    private  String street;
    //小区
    private String community;
    //手机号
    private String managerNum;
    //小区管理员姓名
    private String managerName;

    public String getProvice() {
        return provice;
    }

    public void setProvice(String provice) {
        this.provice = provice;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getZone() {
        return zone;
    }

    public void setZone(String zone) {
        this.zone = zone;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCommunity() {
        return community;
    }

    public void setCommunity(String community) {
        this.community = community;
    }

    public String getManagerNum() {
        return managerNum;
    }

    public void setManagerNum(String managerNum) {
        this.managerNum = managerNum;
    }

    public String getManagerName() {
        return managerName;
    }

    public void setManagerName(String managerName) {
        this.managerName = managerName;
    }
}
