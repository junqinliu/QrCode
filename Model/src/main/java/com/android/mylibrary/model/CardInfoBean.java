package com.android.mylibrary.model;

/**
 * Created by liujunqin on 2016/6/2.
 */
public class CardInfoBean {


    /**
     * areacode : 350102
     * description : 金福新村
     * houseid : 1
     * name : 金福新村
     */

    private String areacode;
    private String description;
    private int houseid;
    private String name;

    public CardInfoBean() {
    }

    public CardInfoBean(String areacode, String description, int houseid, String name) {
        this.areacode = areacode;
        this.description = description;
        this.houseid = houseid;
        this.name = name;
    }

    public CardInfoBean(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getAreacode() {
        return areacode;
    }

    public void setAreacode(String areacode) {
        this.areacode = areacode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getHouseid() {
        return houseid;
    }

    public void setHouseid(int houseid) {
        this.houseid = houseid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
