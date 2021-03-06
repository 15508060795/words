package com.hdl.words.model;

import com.hdl.words.Beans.ApiBean;
import com.hdl.words.Beans.ChangePasswordResultBean;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Date 2019/3/19 18:58
 * author HDL
 * Description:
 */
public interface IGetChangePasswordResult {
    @POST(ApiBean.CHANGE_PASSWORD_URL)
    @FormUrlEncoded
    Call<ChangePasswordResultBean> getCall(
            @Field("username") String username,
            @Field("prePassword") String prePassword,
            @Field("nowPassword") String nowPassword
    );
    // 注解里传入 网络请求 的部分URL地址
    // Retrofit把网络请求的URL分成了两部分：一部分放在Retrofit对象里，另一部分放在网络请求接口里
    // 如果接口里的url是一个完整的网址，那么放在Retrofit对象里的URL可以忽略
    // getCall()是接受网络请求数据的方法
}
