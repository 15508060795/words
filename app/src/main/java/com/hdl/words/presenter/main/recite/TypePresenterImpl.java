package com.hdl.words.presenter.main.recite;

import androidx.annotation.NonNull;
import android.util.Log;

import com.hdl.words.Beans.ApiBean;
import com.hdl.words.Beans.VocabAddResultBean;
import com.hdl.words.Beans.VocabDeleteResultBean;
import com.hdl.words.Beans.WordResultBean;
import com.hdl.words.SharedPreferences.MySession;
import com.hdl.words.base.BasePresenter;
import com.hdl.words.fragment.main.recite.TypeFragment;
import com.hdl.words.model.IGETAddVocabWordsResult;
import com.hdl.words.model.IGETDeleteVocabWordsResult;
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
                Log.e(TAG, "请求四级单词");
                break;
            case 1:
                IGetCETSixWordsResult request1 = retrofit.create(IGetCETSixWordsResult.class);
                call = request1.getCall(username);
                Log.e(TAG, "请求六级单词");
                break;
            case 2:
                IGetDailyWordsResult request2 = retrofit.create(IGetDailyWordsResult.class);
                call = request2.getCall(username);
                Log.e(TAG, "请求每日一词");
                break;
            case 3:
                IGetVocabWordsResult request3 = retrofit.create(IGetVocabWordsResult.class);
                call = request3.getCall(MySession.getUsername(Objects.requireNonNull(mView.getActivity())));
                Log.e(TAG, "请求生词本");
                break;
            default:
                IGetCETFourWordsResult request6 = retrofit.create(IGetCETFourWordsResult.class);
                call = request6.getCall(username);
                Log.e(TAG, "请求四级单词6");
                break;
        }
        call.enqueue(new Callback<WordResultBean>() {
            @Override
            public void onResponse(@NonNull Call<WordResultBean> call, @NonNull Response<WordResultBean> response) {
                try {
                    Log.e(TAG, "词汇获取成功");
                    model.setDataList(response.body().getData());
                    mView.dataRequestCompleted(model.getDataList().get(0), model.getDataList().size());
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

    @Override
    public void requestState(String username, final int pos) {
        Log.e(TAG, "state:" + model.getDataList().get(pos).getState());
        if (model.getDataList().get(pos).getState() == 0) {
            requestLike(username, pos);
        } else {
            requestDislike(username, pos);
        }
    }

    @Override
    public void requestLike(String username, final int pos) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiBean.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        IGETAddVocabWordsResult request = retrofit.create(IGETAddVocabWordsResult.class);
        Call<VocabAddResultBean> call = request.getCall(username, model.getDataList().get(pos).getWord());
        call.enqueue(new Callback<VocabAddResultBean>() {
            @Override
            public void onResponse(@NonNull Call<VocabAddResultBean> call, @NonNull Response<VocabAddResultBean> response) {
                VocabAddResultBean bean = response.body();
                try {
                    if (bean.isResult()) {
                        model.getDataList().get(pos).setState(1);
                        mView.setLike(pos);
                    } else {
                        model.getDataList().get(pos).setState(0);
                        mView.setDislike(pos);
                    }
                    mView.showToast(bean.getMessage());
                } catch (NullPointerException e) {
                    Log.e(TAG, e.getMessage());
                }
            }

            @Override
            public void onFailure(@NonNull Call<VocabAddResultBean> call, @NonNull Throwable t) {
                mView.showToast("网络错误");
            }
        });
    }

    @Override
    public void requestDislike(String username, final int pos) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiBean.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        IGETDeleteVocabWordsResult request = retrofit.create(IGETDeleteVocabWordsResult.class);
        Call<VocabDeleteResultBean> call = request.getCall(username, model.getDataList().get(pos).getWord());
        call.enqueue(new Callback<VocabDeleteResultBean>() {
            @Override
            public void onResponse(@NonNull Call<VocabDeleteResultBean> call, @NonNull Response<VocabDeleteResultBean> response) {
                VocabDeleteResultBean bean = response.body();
                try {
                    if (bean.isResult()) {
                        model.getDataList().get(pos).setState(0);
                        mView.setDislike(pos);
                    } else {
                        model.getDataList().get(pos).setState(1);
                        mView.setLike(pos);
                    }
                    mView.showToast(bean.getMessage());
                } catch (NullPointerException e) {
                    Log.e(TAG, e.getMessage());
                }
            }

            @Override
            public void onFailure(@NonNull Call<VocabDeleteResultBean> call, @NonNull Throwable t) {
                mView.showToast("网络错误");
            }
        });
    }

    @Override
    public void skipNext(int pos) {
        int size = model.getDataList().size();
        pos = pos < size - 1 ? pos + 1 : size - 1;
        mView.changeWordView(pos, model.getDataList().get(pos), size);
    }

    @Override
    public void skipLast(int pos) {
        int size = model.getDataList().size();
        pos = pos > 0 ? pos - 1 : 0;
        mView.changeWordView(pos, model.getDataList().get(pos), size);
    }

    @Override
    public void refreshView(int pos) {
        int size = model.getDataList().size();
        mView.changeWordView(pos, model.getDataList().get(pos), size);
    }

}
