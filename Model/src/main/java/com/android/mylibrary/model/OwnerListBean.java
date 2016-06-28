package com.android.mylibrary.model;

import java.io.Serializable;

/**
 * Created by liujunqin on 2016/6/14.
 */
public class OwnerListBean implements Serializable{

    //姓名
    private String name;
    //手机号码
    private String phoneNum;
    //性别  0表示男 1表示女
    private String sex;

    public OwnerListBean() {
    }

    public OwnerListBean(String name, String phoneNum, String sex) {
        this.name = name;
        this.phoneNum = phoneNum;
        this.sex = sex;
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

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }
}
