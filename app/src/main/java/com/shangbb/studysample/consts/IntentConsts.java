package com.shangbb.studysample.consts;

/**
 * @author ShangBB
 * @ClassName: IntentConsts
 * @Description: Intent常量
 * @date 2015-11-19 下午5:06:02
 */
public class IntentConsts {
    /**
     * 初始化数据库的 Intent action
     */
    public static final String ACTION_INIT_DB = "com.xaqy.leanmanager.action.init.db";
    /**
     * 登录的 Intent action
     */
    public static final String ACTION_LOGIN_ACTIVITY = "com.xaqy.leanmanager.action.login";
    /**
     * intent 启动位置
     */
    public static final String START_INTENT_WITH = "start_intent_with";


    /**====================================== INTENT START 编号====================================*/
    //欢迎界面
    public static final int ACTIVITY_SPLASH = 0X001;

    //登录
    public static final int ACTIVITY_LOGIN = 0X002;

    //注册
    public static final int ACTIVITY_REGISTER = 0X003;

    //重置密码
    public static final int ACTIVITY_RESET_PASSWORD = 0X004;

    //主界面
    public static final int ACTIVITY_MAIN = 0X010;


    /**===================================INTENT START 编号 END====================================*/

    /**
     * 使用bundle 传递信息 时使用的key
     */
    public static final String BUNDLE_KEY_LOGIN_ACCOUNT = "login_account";
    public static final String BUNDLE_KEY_LOGIN_PASSWOED = "login_password";
}
