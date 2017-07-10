package com.shangbb.studysample.http;

import android.icu.util.VersionInfo;

import com.shangbb.studysample.http.entity.AddMemberResult;
import com.shangbb.studysample.http.entity.BloodData;
import com.shangbb.studysample.http.entity.UserInfo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @Fuction: 对retrofit实例化的简单封装, 包含同一个BASE_URL所有网络交互的方法
 * @Author: Shang
 * @Date: 2016/5/18  14:25
 */
public class HttpMethods {

    private static String TAG = "HttpMethods";

    private ApiService mApiService;

    private static volatile HttpMethods instance = null;

    //私有构造方法
    private HttpMethods() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .client(OkHttpClientHelper.getOkHttpClient())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();

        mApiService = retrofit.create(ApiService.class);
    }

    /**
     * 获取单例
     * @return 实例
     */
    public static HttpMethods getInstance() {

        // if already inited, no need to get lock everytime
        if (instance == null) {
            synchronized (HttpMethods.class) {
                if (instance == null) {
                    instance = new HttpMethods();
                }
            }
        }

        return instance;
    }

    /**
     * 用来统一处理Http的resultCode,并将HttpResult的Data部分剥离出来返回给subscriber
     * @param <T>
     *         Subscriber真正需要的数据类型，也就是Data部分的数据类型
     */
    private class HttpResultFunc<T> implements Function<HttpResult<T>, List<T>> {

        @Override
        public List<T> apply(@NonNull HttpResult<T> tHttpResult) throws Exception {
            if ((ResultStatus.HTTP_SUCCESS).equals(tHttpResult.getStatus())) {
                return tHttpResult.getValues();
            } else {
                throw new ApiException(tHttpResult.getDescritpion());
            }
        }
    }


    /**=====================================网络请求方法=========================================*/

    /**
     * 获取验证码
     * @param phone
     * @param type
     *         0:注册 1：重置密码
     *
     * @return
     */
    public Observable<HttpResult> getSecurityCode(String phone, String type) {
        return mApiService.getSecurityCode(phone, type, "jy")
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 注册
     * @return
     */
    public Observable<HttpResult> registerAccount(UserInfo userInfo, String time) {

        Map<String, String> params = new HashMap<>();
        params.put("phone", userInfo.getPhone());
        params.put("smscode", userInfo.getVerifyCode());
        params.put("phoneimei", userInfo.getImei());
        params.put("userType", String.valueOf(userInfo.getRole()));
        params.put("password", userInfo.getPassword());
        params.put("userPhone", userInfo.getPhone());
        params.put("memberName", userInfo.getUserName());
        params.put("loginTime", time);

        return mApiService.registerAccount(params)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

    }

    /**
     * 密码重置
     * @return
     */
    public Observable<HttpResult> resetAccount(UserInfo userInfo) {

        Map<String, String> params = new HashMap<>();
        params.put("phone", userInfo.getPhone());
        params.put("smsCode", userInfo.getVerifyCode());
        params.put("newPassword", userInfo.getPassword());
        params.put("oldPassword", "");

        return mApiService.resetAccount(params)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

    }

    /**
     * 添加游客
     * @return
     */
    public Observable<List<AddMemberResult>> addMember(String memberName, String memberAcc,
                                                       String linkmanPhone) {

        String memberPhone = "1" + memberAcc.substring(1);
        Map<String, String> params = new HashMap<>();
        params.put("phone", memberAcc);
        params.put("userPhone", memberPhone);
        params.put("memberName", memberName);
        params.put("guardianPhone", linkmanPhone);
        params.put("industry", "jy");

        return mApiService.addMember(params)
                .map(new HttpResultFunc<AddMemberResult>())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }


    /**
     * 查询血压测量记录
     * @param memberId
     * @return
     */
    public Observable<List<BloodData>> getBloodDatas(String memberId) {

        return mApiService.getBloodDatas(memberId, memberId, "", "", "jy")
                .map(new HttpResultFunc<BloodData>())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

    }


    /**
     * 查询版本信息
     * @param versioncode
     *
     * @return
     */
    public Observable<List<VersionInfo>> updateVersion(String versioncode) {
        return mApiService.updateVersion(versioncode, "cqsguide", "jy")
                .map(new HttpResultFunc<VersionInfo>())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

    }
}
