package com.android.constant;


/**
 * Created by liujunqin on 2016/6/28.
 */
public class Constants {

    public static final String HOST = "http://120.76.202.60:8080/microcard";

    /**
     * 注册接口
     */
    public static final String Register = "/v0.1/user/admin/register";
    /**
     * 登录
     */
    public static final String Login = "/v0.1/user/login";
    /**
     * 注销
     */
    public static final String LoginOut = "/v0.1/user/loginout";
    /**
     * 修改密码
     */

    public static final String ModifyPwd = "/v0.1/user/updatePassword";
    /**
     * 修改用户名
     */

    public static final String ModifyUserName = "/v0.1/user";
    /**
     * 忘记密码
     */

    public static final String ForgetPassword = "/v0.1/user/forgetPassword";

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
    public static final String Provice = "/v0.1/place/province";
    /**
     * 获取城市
     */
    public static final String City = "/v0.1/place/city";
    /**
     * 获取区县
     */
    public static final String Area = "/v0.1/place/area";

    /**
     * 小区列表
     */
    public static final String  House = "/v0.1/place/house";

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

    /**
     * 添加楼栋
     */
    public static final String AddBuild = "/v0.1/place/build";

    /**
     * 待审核列表
     */
    public static final String isROOT = "/v0.1/user/audit";

    /**
     * 审核
     */
    public static final String ROOT = "/v0.1/user/audit";

    /**
     * 微卡列表
     */
    public static final String CardList = "/v0.1/place/build/card";


    /**
     * 获取楼栋二维码
     */
    public static final String GetQrCode = "/v0.1/place/build";


    /**
     * 往来日志
     */
    public static final String InviteLog = "/v0.1/user/invite";

    /**
     * 获取业主列表
     */
    public static final String OwnerList = "/v0.1/user/owner";

    /**
     * 添加业主
     */
    public static final String AddOwner = "/v0.1/user/owner";
    /**
     * 删除业主
     */
    public static final String DeleteOwner = "/v0.1/user/owner";

    /**
     * 申请审核
     */
    public static final String Apply = "/v0.1/user/audit/apply";

    /**
     * 添加开门权限
     */
    public static final String AddLimit = "/v0.1/place/build/card";
    /**
     * 删除开门权限
     */
    public static final String DeleteLimit = "/v0.1/place/build/card";
    /*==================================================================================*/
    /**
     * 搜索小区
     */
    public static final String  SearchHouse = "/v0.1/place/house";
    /**
     * 小区列表
     */
    public static final String cell_list = "/v0.1/place/house/admin";
    /**
     * 小区管理员列表
     */
    public static final String managers = "/v0.1/user/property";
    /**
     * 删除小区管理员
     */
    public static final String delete_member = "/v0.1/user/property";
    /**
     * 删除小区
     */
    public static final String delete_cell = "/v0.1/place/house";
    /**
     * 添加小区
     */
    public static final String add_cell = "/v0.1/place/house";

    /**
     * 广告列表
     */
    public static final String AdList = "/v0.1/ad";

    /**
     * 添加广告
     */
    public static final String  AddAd = "/v0.1/ad";
    /**
     * 删除广告
     */
    public static final String DeleteAd = "/v0.1/ad";
}
