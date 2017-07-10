package com.shangbb.studysample.http;


import android.icu.util.VersionInfo;

import com.shangbb.studysample.http.entity.AddMemberResult;
import com.shangbb.studysample.http.entity.BloodData;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * @Fuction: 请求数据服务，这里是所有网络请求的接口方法
 * @Author: Shang
 * @Date: 2016/4/11  14:21
 */
public interface ApiService {


    //获取验证码
    @GET(Api.SEURITYCODE_URL + "/{phone}&{type}&{industry}")
    Observable<HttpResult> getSecurityCode(@Path("phone") String phone,
                                           @Path("type") String type,
                                           @Path("industry") String industry);

    //注册
    @POST(Api.REGISTER_URL)
    Observable<HttpResult> registerAccount(@Body Map<String, String> params);

    //重置密码
    @PUT(Api.RESET_URL)
    Observable<HttpResult> resetAccount(@Body Map<String, String> params);

    //添加游客
    @POST(Api.MEMBER_ADD_URL)
    Observable<HttpResult<AddMemberResult>> addMember(@Body Map<String, String> params);


    //查询数据
    @GET(Api.GET_BLOOD_PRESSURE_URL + "/getboolprnewlist")
    Observable<HttpResult<BloodData>> getBloodDatas(@Query("memberId") String memberId,
                                                    @Query("setMemberId") String setMemberId,
                                                    @Query("startTime") String startTime,
                                                    @Query("endTime") String endTime,
                                                    @Query("industry") String industry);


    //版本更新
    @GET(Api.UPDATE_URL + "/{versioncode}&{versiontype}&{industry}")
    Observable<HttpResult<VersionInfo>> updateVersion(@Path("versioncode") String versioncode,
                                                      @Path("versiontype") String versiontype,
                                                      @Path("industry") String industry);

}
