package com.android.mylibrary.model;

/**
 * Created by liujunqin on 2016/8/1.
 */
public class RoomCardBean {

    private String open; // 是否具有开门权限
    private String buildname;
    private String buildid;

    public String getOpen() {
        return open;
    }

    public void setOpen(String open) {
        this.open = open;
    }

    public String getBuildname() {
        return buildname;
    }

    public void setBuildname(String buildname) {
        this.buildname = buildname;
    }

    public String getBuildid() {
        return buildid;
    }

    public void setBuildid(String buildid) {
        this.buildid = buildid;
    }
}
