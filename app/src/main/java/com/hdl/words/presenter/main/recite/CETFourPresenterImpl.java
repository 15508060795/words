package com.hdl.words.presenter.main.recite;

import android.support.annotation.NonNull;
import android.util.Log;

import com.hdl.words.Beans.ApiBean;
import com.hdl.words.Beans.WordResultBean;
import com.hdl.words.base.BasePresenter;
import com.hdl.words.fragment.main.recite.CETFourFragment;
import com.hdl.words.model.CETFourWordModelImpl;
import com.hdl.words.model.IGetWordsResult;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Date 2019/4/12 16:27
 * author HDL
 * Description:
 */
public class CETFourPresenterImpl extends BasePresenter<CETFourFragment> implements ICETFourPresenter {
    private CETFourWordModelImpl model;
    public CETFourPresenterImpl(CETFourFragment view) {
        super(view);
        model = CETFourWordModelImpl.getInstance();
    }

    @Override
    public void getAllWords() {
        model.requestData(new CETFourWordModelImpl.OnRequestCallBack() {
            @Override
            public void onSuccess() {
                mView.dataRequestCompleted();
            }

            @Override
            public void onFailed() {
                mView.showRequestFailDialog();
            }
        });
        /*Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiBean.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        IGetWordsResult request = retrofit.create(IGetWordsResult.class);
        Call<WordResultBean> call = request.getCall();
        call.enqueue(new Callback<WordResultBean>() {
            @Override
            public void onResponse(@NonNull Call<WordResultBean> call, @NonNull Response<WordResultBean> response) {
                List<WordResultBean.DataBean> dataList;
                try {
                    dataList = response.body().getData();
                    Log.e(TAG, dataList.size() + " ");
                } catch (NullPointerException e) {
                    dataList = null;
                    Log.e(TAG, "输入内容有误，返回结果为null");
                }

            }

            @Override
            public void onFailure(@NonNull Call<WordResultBean> call, @NonNull Throwable t) {
                mView.showRequestFailDialog();
            }
        });*/
    }
}
