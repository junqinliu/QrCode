package com.android.mylibrary.model;

/**
 * Created by liujunqin on 2016/6/2.
 */
public class MemberBean {

    /**
     *  成员名字
     */

    private String memberName;
    /**
     *  成员手机
     */

    private String memberPhoneNum;
    /**
     *  成员头像
     */

    private String memberPhoto;

    public MemberBean(String memberName, String memberPhoneNum, String memberPhoto) {
        this.memberName = memberName;
        this.memberPhoneNum = memberPhoneNum;
        this.memberPhoto = memberPhoto;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public void setMemberPhoneNum(String memberPhoneNum) {
        this.memberPhoneNum = memberPhoneNum;
    }

    public void setMemberPhoto(String memberPhoto) {
        this.memberPhoto = memberPhoto;
    }

    public String getMemberName() {
        return memberName;
    }

    public String getMemberPhoneNum() {
        return memberPhoneNum;
    }

    public String getMemberPhoto() {
        return memberPhoto;
    }
}
