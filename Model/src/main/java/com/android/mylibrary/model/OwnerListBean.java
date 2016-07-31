package com.android.mylibrary.model;

import java.io.Serializable;

/**
 * Created by liujunqin on 2016/6/14.
 */
public class OwnerListBean implements Serializable{


    /**
     * sex : 1
     * phone : 18650868655
     * houseid : 1
     * housename : 金福新村
     * name : 物业管理员
     * userid : 57
     */

    private int sex;
    private String phone;
    private int houseid;
    private String housename;
    private String name;
    private int userid;

    public OwnerListBean() {
    }

    public OwnerListBean(String name, String phone, int sex) {
        this.name = name;
        this.phone = phone;
        this.sex = sex;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getHouseid() {
        return houseid;
    }

    public void setHouseid(int houseid) {
        this.houseid = houseid;
    }

    public String getHousename() {
        return housename;
    }

    public void setHousename(String housename) {
        this.housename = housename;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }
}
