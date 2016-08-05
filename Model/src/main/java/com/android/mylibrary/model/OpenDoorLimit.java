package com.android.mylibrary.model;

/**
 * Created by liujunqin on 2016/7/23.
 */
public class OpenDoorLimit {


    private  boolean open;
    private  String buildname;
    private  String buildid;

    public boolean isOpen() {
        return open;
    }

    public void setOpen(boolean open) {
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
