package com.android.mylibrary.model;

import java.io.Serializable;

public class SortModel implements Serializable {
	//显示的数�?
	private String name;
	//显示数据拼音的首字母
	private String sortLetters;
	//手机号码
	private String phoneNum;
	//性别  0表示男 1表示女
	private String sex;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSortLetters() {
		return sortLetters;
	}
	public void setSortLetters(String sortLetters) {
		this.sortLetters = sortLetters;
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
