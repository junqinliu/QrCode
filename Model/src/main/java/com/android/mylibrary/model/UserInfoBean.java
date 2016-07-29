package com.android.mylibrary.model;

/**
 * Created by liujunqin on 2016/7/24.
 */
public class UserInfoBean {

    private  String token;
    private  String name;
    private  String userid;
    private  String aduitstatus;//状态（枚举通过、拒绝、审核中） PASS/REFUSE/AUDITING
    private  String authority;//ADMIN/PROPERTY/OWNER/USER   权限（枚举管理员、物业、业主、成员）
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getAduitstatus() {
        return aduitstatus;
    }

    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }

    public void setAduitstatus(String aduitstatus) {
        this.aduitstatus = aduitstatus;
    }
}
