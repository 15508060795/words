package com.hdl.words.model;

import com.hdl.words.Beans.ApiBean;
import com.hdl.words.Beans.PersonalInfoBean;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Date 2019/4/24 21:09
 * author hdl
 * Description:
 */
public interface IGETPersonalInfoResult {
    @POST(ApiBean.PERSONAL_INFO_URL)
    @FormUrlEncoded
    Call<PersonalInfoBean> getCall(
            @Field("username") String username
    );
}
