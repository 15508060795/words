package com.hdl.words.model;

import com.hdl.words.Beans.ApiBean;
import com.hdl.words.Beans.VocabAddResultBean;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Date 2019/4/12 16:35
 * author hdl
 * Description:
 */
public interface IGETAddVocabWordsResult {
    @POST(ApiBean.VOCAB_ADD_URL)
    @FormUrlEncoded
    Call<VocabAddResultBean> getCall(
            @Field("username") String username,
            @Field("word") String word
    );
}
