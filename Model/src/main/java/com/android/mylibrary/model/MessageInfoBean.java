package com.android.mylibrary.model;

/**
 * Created by liujunqin on 2016/6/15.
 */
public class MessageInfoBean {

    //业主名字
    private String username;
    //业主手机号
    private String userphone;
    //提交时间
    private String time;
    //提交信息
    private String title;
    //id
    private String userid;

    private String propertytype;

    private String propertyphone;
    private String propertyaddress;


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserphone() {
        return userphone;
    }

    public void setUserphone(String userphone) {
        this.userphone = userphone;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getPropertytype() {
        return propertytype;
    }

    public void setPropertytype(String propertytype) {
        this.propertytype = propertytype;
    }

    public String getPropertyphone() {
        return propertyphone;
    }

    public void setPropertyphone(String propertyphone) {
        this.propertyphone = propertyphone;
    }

    public String getPropertyaddress() {
        return propertyaddress;
    }

    public void setPropertyaddress(String propertyaddress) {
        this.propertyaddress = propertyaddress;
    }
}
