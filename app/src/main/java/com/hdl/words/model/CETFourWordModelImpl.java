package com.hdl.words.model;

import android.support.annotation.NonNull;
import android.util.Log;

import com.hdl.words.Beans.ApiBean;
import com.hdl.words.Beans.WordResultBean;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Date 2019/4/18 21:36
 * author hdl
 * Description:
 */
public class CETFourWordModelImpl {
    private List<WordResultBean.DataBean> mDataList;
    private static CETFourWordModelImpl mModel = null;
    private static final String TAG = "CETFourWordModelImpl";

    private CETFourWordModelImpl() {
    }

    public static CETFourWordModelImpl getInstance() {
        if (mModel == null) {
            synchronized (CETFourWordModelImpl.class) {
                if (mModel == null) {
                    mModel = new CETFourWordModelImpl();
                }
            }
        }
        return mModel;
    }

    public void requestData(final OnRequestCallBack onRequestCallBack) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiBean.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        IGetWordsResult request = retrofit.create(IGetWordsResult.class);
        Call<WordResultBean> call = request.getCall();
        call.enqueue(new Callback<WordResultBean>() {
            @Override
            public void onResponse(@NonNull Call<WordResultBean> call, @NonNull Response<WordResultBean> response) {
                try {
                    setDataList(response.body().getData());
                    if (onRequestCallBack != null) {
                        onRequestCallBack.onSuccess();
                    }

                } catch (NullPointerException e) {
                    Log.e(TAG, e.getMessage());
                }

            }

            @Override
            public void onFailure(@NonNull Call<WordResultBean> call, @NonNull Throwable t) {
                Log.e(TAG, "网络错误");
                if (onRequestCallBack != null) {
                    onRequestCallBack.onFailed();
                }
            }
        });

    }

    public List<WordResultBean.DataBean> getDataList() {
        return mDataList;
    }

    public void setDataList(List<WordResultBean.DataBean> dataList) {
        this.mDataList = dataList;
    }

    public interface OnRequestCallBack {
        void onSuccess();

        void onFailed();
    }
}
