package com.hdl.words.model;

import com.hdl.words.Beans.ApiBean;
import com.hdl.words.Beans.SetPersonalBirthResultBean;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Date 2019/4/24 21:09
 * author hdl
 * Description:
 */
public interface IGETSetPersonalBirthResult {
    @POST(ApiBean.PERSONAL_INFO_SET_BIRTH_URL)
    @FormUrlEncoded
    Call<SetPersonalBirthResultBean> getCall(
            @Field("username") String username,
            @Field("birth") String birth
    );
}
