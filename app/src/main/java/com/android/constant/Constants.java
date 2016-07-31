package com.android.constant;


/**
 * Created by liujunqin on 2016/6/28.
 */
public class Constants {

    public static final String HOST = "http://120.76.202.60:8080/microcard";


    /**
     * 登录
     */
    public static final String Login = "/v0.1/user/login";
    /**
     * 获取用户信息
     */
    public static final String getUserInfo = "/v0.1/user";
    /**
     * 楼栋列表
     */
    public static final String build = "/v0.1/place/build";
    /**
     * 获取省份
     */
    public static final String  Provice = "/v0.1/place/province";
    /**
     * 获取城市
     */
    public static final String  City = "/v0.1/place/city";
    /**
     * 获取区县
     */
    public static final String  Area = "/v0.1/place/area";

    /**
     * 设备报修 业主投诉列表
     */

    public static final String PropertyList = "/v0.1/property";
    /**
     * 消息推送提交
     */
    public static final String PropertyPost = "/v0.1/property";

    /**
     * 配置微卡(授权二维码)
     */
    public static final String AuthorizateQrCode = "/v0.1/place/build";

    /**
     * 删除楼栋
     */
    public static final String DeleteBuild = "/v0.1/place/build";

    /*==================================================================================*/
    /**
     * 小区列表
     */
    public static final String cell_list = "/v0.1/place/house/admin";
    /**
     * 小区列表
     */
    public static final String cell_list_search = "/v0.1/place/house";
    /**
     * 小区管理员列表
     */
    public static final String managers = "/v0.1/user/property";
    /**
     * 删除小区管理员
     */
    public static final String delete_member = "/v0.1/user/property";
    /**
     * 删除小区管理员
     */
    public static final String add_cell = "/v0.1/place/house";

}
