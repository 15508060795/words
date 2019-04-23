package com.hdl.words.presenter.main.recite;

import android.support.annotation.NonNull;
import android.util.Log;

import com.hdl.words.Beans.ApiBean;
import com.hdl.words.Beans.WordResultBean;
import com.hdl.words.SharedPreferences.MySession;
import com.hdl.words.base.BasePresenter;
import com.hdl.words.fragment.main.recite.TypeFragment;
import com.hdl.words.model.IGetVocabWordsResult;
import com.hdl.words.model.WordModelImpl;
import com.hdl.words.model.IGetCETFourWordsResult;
import com.hdl.words.model.IGetCETSixWordsResult;
import com.hdl.words.model.IGetDailyWordsResult;

import java.util.Objects;

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
public class TypePresenterImpl extends BasePresenter<TypeFragment> implements ITypePresenter {
    private WordModelImpl model;

    public TypePresenterImpl(TypeFragment view) {
        super(view);
        model = WordModelImpl.getInstance();
    }

    @Override
    public void getWords(int type) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiBean.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Call<WordResultBean> call;
        String username = MySession.getUsername(Objects.requireNonNull(mView.getActivity()));
        switch (type) {
            case 0:
                IGetCETFourWordsResult request0 = retrofit.create(IGetCETFourWordsResult.class);
                call = request0.getCall(username);
                Log.e(TAG,"请求四级单词");
                break;
            case 1:
                IGetCETSixWordsResult request1 = retrofit.create(IGetCETSixWordsResult.class);
                call = request1.getCall(username);
                Log.e(TAG,"请求六级单词");
                break;
            case 2:
                IGetDailyWordsResult request2 = retrofit.create(IGetDailyWordsResult.class);
                call = request2.getCall(username);
                Log.e(TAG,"请求每日一词");
                break;
            case 3:
                IGetVocabWordsResult request3 = retrofit.create(IGetVocabWordsResult.class);
                call = request3.getCall(MySession.getUsername(Objects.requireNonNull(mView.getActivity())));
                Log.e(TAG,"请求生词本");
                break;
            default:
                IGetCETFourWordsResult request6 = retrofit.create(IGetCETFourWordsResult.class);
                call = request6.getCall(username);
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
