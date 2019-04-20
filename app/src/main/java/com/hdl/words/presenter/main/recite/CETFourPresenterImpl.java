package com.hdl.words.presenter.main.recite;

import android.support.annotation.NonNull;
import android.util.Log;

import com.hdl.words.Beans.ApiBean;
import com.hdl.words.Beans.WordResultBean;
import com.hdl.words.base.BasePresenter;
import com.hdl.words.fragment.main.recite.TypeFragment;
import com.hdl.words.model.CETFourWordModelImpl;
import com.hdl.words.model.IGetCETFourWordsResult;
import com.hdl.words.model.IGetCETSixWordsResult;
import com.hdl.words.model.IGetEveryDayWordsResult;

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
public class CETFourPresenterImpl extends BasePresenter<TypeFragment> implements ICETFourPresenter {
    private CETFourWordModelImpl model;

    public CETFourPresenterImpl(TypeFragment view) {
        super(view);
        model = CETFourWordModelImpl.getInstance();
    }

    @Override
    public void getWords(int type) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiBean.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Call<WordResultBean> call;
        switch (type) {
            case 0:
                IGetCETFourWordsResult request0 = retrofit.create(IGetCETFourWordsResult.class);
                call = request0.getCall();
                Log.e(TAG,"请求四级单词");
                break;
            case 1:
                IGetCETSixWordsResult request1 = retrofit.create(IGetCETSixWordsResult.class);
                call = request1.getCall();
                Log.e(TAG,"请求六级单词");
                break;
            case 2:
                IGetEveryDayWordsResult request2 = retrofit.create(IGetEveryDayWordsResult.class);
                call = request2.getCall();
                Log.e(TAG,"请求每日一词");
                break;
            case 3:
                IGetCETFourWordsResult request3 = retrofit.create(IGetCETFourWordsResult.class);
                call = request3.getCall();
                Log.e(TAG,"请求四级单词3");
                break;
            case 4:
                IGetCETFourWordsResult request4 = retrofit.create(IGetCETFourWordsResult.class);
                call = request4.getCall();
                Log.e(TAG,"请求四级单词4");
                break;
            case 5:
                IGetCETFourWordsResult request5 = retrofit.create(IGetCETFourWordsResult.class);
                call = request5.getCall();
                Log.e(TAG,"请求四级单词5");
                break;
            default:
                IGetCETFourWordsResult request6 = retrofit.create(IGetCETFourWordsResult.class);
                call = request6.getCall();
                Log.e(TAG,"请求四级单词6");
                break;
        }
        call.enqueue(new Callback<WordResultBean>() {
            @Override
            public void onResponse(@NonNull Call<WordResultBean> call, @NonNull Response<WordResultBean> response) {
                try {
                    Log.e(TAG, "词汇获取成功");
                    model.setDataList(response.body().getData());
                    mView.dataRequestCompleted();
                } catch (NullPointerException e) {
                    Log.e(TAG, e.getMessage());
                    mView.showRequestFailDialog();
                }
            }

            @Override
            public void onFailure(@NonNull Call<WordResultBean> call, @NonNull Throwable t) {
                Log.e(TAG, "网络错误");
            }
        });
    }
}
