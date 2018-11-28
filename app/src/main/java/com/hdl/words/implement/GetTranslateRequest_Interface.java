package com.hdl.words.implement;

import com.hdl.words.Beans.ApiBean;
import com.hdl.words.Beans.TranslateResultBean;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface GetTranslateRequest_Interface {
    /*@GET("translate?q=apple&from=en&to=zh&appid="+ApiBean.TRANSLATE_APPID+"&salt=123&sign=7476c00651cec41851c08e8c54359207")*/
    @POST("translate?")
    @FormUrlEncoded
    Call<TranslateResultBean> getCall(
            @Field("q") String targetSentence,
            @Field("from") String from,
            @Field("to") String to,
            @Field("appid") String appid,
            @Field("salt") String salt,
            @Field("sign") String sign
    );
    // 注解里传入 网络请求 的部分URL地址
    // Retrofit把网络请求的URL分成了两部分：一部分放在Retrofit对象里，另一部分放在网络请求接口里
    // 如果接口里的url是一个完整的网址，那么放在Retrofit对象里的URL可以忽略
    // getCall()是接受网络请求数据的方法

}