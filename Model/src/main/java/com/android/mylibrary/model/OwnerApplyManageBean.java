package com.android.mylibrary.model;

/**
 * Created by liujunqin on 2016/6/18.
 */
public class OwnerApplyManageBean {

    //用户
    private String userName;

    //所在楼层
    private String houseNum;

    //申请时间
    private  String applyTime;

    //审核状态
    private  String status;


    public OwnerApplyManageBean(String userName, String houseNum, String applyTime, String status) {
        this.userName = userName;
        this.houseNum = houseNum;
        this.applyTime = applyTime;
        this.status = status;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getHouseNum() {
        return houseNum;
    }

    public void setHouseNum(String houseNum) {
        this.houseNum = houseNum;
    }

    public String getApplyTime() {
        return applyTime;
    }

    public void setApplyTime(String applyTime) {
        this.applyTime = applyTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
