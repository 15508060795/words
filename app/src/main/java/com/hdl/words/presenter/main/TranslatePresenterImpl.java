package com.hdl.words.presenter.main;

import android.annotation.SuppressLint;
import androidx.annotation.NonNull;
import android.util.Log;

import com.hdl.words.Beans.ApiBean;
import com.hdl.words.Beans.LanguageBean;
import com.hdl.words.Beans.TranslateResultBean;
import com.hdl.words.R;
import com.hdl.words.base.BasePresenter;
import com.hdl.words.fragment.main.TranslateFragment;
import com.hdl.words.model.IGetTranslateRequest;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Date 2019/3/3 15:01
 * author HDL
 * Description:
 */
public class TranslatePresenterImpl extends BasePresenter<TranslateFragment> implements ITranslatePresenter {
    public TranslatePresenterImpl(TranslateFragment view) {
        super(view);
    }

    @Override
    public void translate(int from, int to, String str, final boolean fromUser) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiBean.TRANSLATE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        IGetTranslateRequest request = retrofit.create(IGetTranslateRequest.class);
        String sign = ApiBean.getStringMD5(
                ApiBean.TRANSLATE_APP_ID + str + ApiBean.TRANSLATE_SALT + ApiBean.TRANSLATE_KEY
        );
        Call<TranslateResultBean> call = request.getCall(
                str,
                LanguageBean.getInstance().getLanguageList().get(from),
                LanguageBean.getInstance().getLanguageCodeList().get(to),
                ApiBean.TRANSLATE_APP_ID,
                ApiBean.TRANSLATE_SALT,
                sign);
        call.enqueue(new Callback<TranslateResultBean>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(@NonNull Call<TranslateResultBean> call, @NonNull Response<TranslateResultBean> response) {
                String resultStr;
                try {
                    resultStr = response.body().getTrans_result().get(0).getDst();
                    Log.e(TAG, resultStr + "");
                } catch (NullPointerException e) {
                    resultStr = "";
                    Log.e(TAG, "输入内容有误，返回结果为null");
                }
                mView.translateResult(resultStr,fromUser);
            }

            @Override
            public void onFailure(@NonNull Call<TranslateResultBean> call, @NonNull Throwable t) {
                mView.showToast(R.string.network_request_error);
            }
        });
    }

    @Override
    public void fromTypeChange() {
        mView.fromTypeChange();
    }

    @Override
    public void toTypeChange() {
        mView.toTypeChange();
    }

    @Override
    public void typeSwitch() {
        mView.languageSwitch();
    }
}
