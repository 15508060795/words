package com.hdl.words.model;

import com.hdl.words.Beans.ApiBean;
import com.hdl.words.Beans.SetPersonalSignResultBean;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Date 2019/4/24 21:09
 * author hdl
 * Description:
 */
public interface IGETSetPersonalSignResult {
    @POST(ApiBean.PERSONAL_INFO_SET_SIGN_URL)
    @FormUrlEncoded
    Call<SetPersonalSignResultBean> getCall(
            @Field("username") String username,
            @Field("sign") String sign
    );
}
