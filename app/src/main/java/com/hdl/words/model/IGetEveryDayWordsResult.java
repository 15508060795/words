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
public interface IGetEveryDayWordsResult {
    @GET(ApiBean.EVERY_DAY_WORD_URL)
    Call<WordResultBean> getCall(
    );
}
