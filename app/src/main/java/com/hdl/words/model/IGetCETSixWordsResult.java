package com.hdl.words.model;

import com.hdl.words.Beans.ApiBean;
import com.hdl.words.Beans.WordResultBean;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Date 2019/4/12 16:35
 * author hdl
 * Description:
 */
public interface IGetCETSixWordsResult {
    @GET(ApiBean.CET_SIX_WORD_URL)
    Call<WordResultBean> getCall(
    );
}
