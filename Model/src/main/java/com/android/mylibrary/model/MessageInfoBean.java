package com.android.mylibrary.model;

/**
 * Created by liujunqin on 2016/6/15.
 */
public class MessageInfoBean {

    //业主名字
    private  String name;
    //业主手机号
    private  String phoneNum;
    //提交时间
    private String time;
    //提交信息
    private String message;

    public MessageInfoBean() {
    }

    public MessageInfoBean(String name, String phoneNum, String time, String message) {
        this.name = name;
        this.phoneNum = phoneNum;
        this.time = time;
        this.message = message;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
