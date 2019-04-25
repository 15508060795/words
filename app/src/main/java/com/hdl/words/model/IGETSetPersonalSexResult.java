package com.hdl.words.model;

import com.hdl.words.Beans.ApiBean;
import com.hdl.words.Beans.SetPersonalSexResultBean;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Date 2019/4/24 21:09
 * author hdl
 * Description:
 */
public interface IGETSetPersonalSexResult {
    @POST(ApiBean.PERSONAL_INFO_SET_SEX_URL)
    @FormUrlEncoded
    Call<SetPersonalSexResultBean> getCall(
            @Field("username") String username,
            @Field("sex") int sex
    );
}
