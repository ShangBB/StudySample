package com.shangbb.studysample.http;

/**
 * @Fuction: 请求api接口,这里面是app用到的所有URL
 * @Author: Shang
 * @Date: 2016/4/11  14:14
 */
public class Api {

    /*数据服务器地址*/
    public static final String BASE_URL = "http://changqingshu.joyee.org:9966/";

    //七牛上传图片地址
    public static final String QINIU_URL = "http://7xsw56.com2.z0.glb.qiniucdn.com/";
    public static final String QINIU_TOKEN_URL = BASE_URL + "opera/opera/uploadtoken";

    //注册
    public static final String REGISTER_URL = "platformenter/platformenter/registered";
    //重置密码
    public static final String RESET_URL = "platformenter/platformenter/registered";
    //验证码
    public static final String SEURITYCODE_URL = "platformenter/platformenter/messageauth";

    //添加游客
    public static final String MEMBER_ADD_URL = "organization/organization/people/uploadmemberinfo";

    //获取血压数据
    public static final String GET_BLOOD_PRESSURE_URL = "device/device/belter/bloodopre";

    //版本更新
    public static final String UPDATE_URL = "platformenter/version/appversion";



}
