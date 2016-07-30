package com.android.mylibrary.model;

/**
 * Created by liujunqin on 2016/7/23.
 */
public class OpenDoorLimit {

    private String doorId;
    private String doorDesc;

    public OpenDoorLimit(String doorId, String doorDesc) {
        this.doorId = doorId;
        this.doorDesc = doorDesc;
    }

    public String getDoorId() {
        return doorId;
    }

    public void setDoorId(String doorId) {
        this.doorId = doorId;
    }

    public String getDoorDesc() {
        return doorDesc;
    }

    public void setDoorDesc(String doorDesc) {
        this.doorDesc = doorDesc;
    }
}
