package com.android.mylibrary.model;

/**
 * Created by liujunqin on 2016/6/2.
 */
public class CardInfoBean {

    /**
     * 卡的房间号
     */
    private String cardNum;

    /**
     * 卡的图标
     */
    private String carPhotoUrl;

    public CardInfoBean(String cardNum, String carPhotoUrl) {
        this.cardNum = cardNum;
        this.carPhotoUrl = carPhotoUrl;
    }

    public String getCardNum() {
        return cardNum;
    }

    public void setCardNum(String cardNum) {
        this.cardNum = cardNum;
    }

    public String getCarPhotoUrl() {
        return carPhotoUrl;
    }

    public void setCarPhotoUrl(String carPhotoUrl) {
        this.carPhotoUrl = carPhotoUrl;
    }
}
