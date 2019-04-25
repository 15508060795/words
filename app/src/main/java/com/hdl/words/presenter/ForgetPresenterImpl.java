package com.hdl.words.presenter;

import android.annotation.SuppressLint;
import androidx.annotation.NonNull;
import android.util.Log;

import com.hdl.words.Beans.ApiBean;
import com.hdl.words.Beans.ChangePasswordResultBean;
import com.hdl.words.R;
import com.hdl.words.base.BasePresenter;
import com.hdl.words.fragment.ForgetFragment;
import com.hdl.words.model.IGetChangePasswordResult;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Date 2019/3/3 14:09
 * author HDL
 * Description:
 */
public class ForgetPresenterImpl extends BasePresenter<ForgetFragment> implements IForgetPresenter {
    public ForgetPresenterImpl(ForgetFragment view) {
        super(view);
    }

    @Override
    public void changePassword(String account, String prePassword, String nowPassword) {
        if (!(account.isEmpty() | prePassword.isEmpty() | nowPassword.isEmpty())) {
            mView.showLoading(R.string.toast_verify_account_information);
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(ApiBean.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            IGetChangePasswordResult request = retrofit.create(IGetChangePasswordResult.class);
            Call<ChangePasswordResultBean> call = request.getCall(account, prePassword, nowPassword);
            call.enqueue(new Callback<ChangePasswordResultBean>() {
                @SuppressLint("SetTextI18n")
                @Override
                public void onResponse(@NonNull Call<ChangePasswordResultBean> call, @NonNull Response<ChangePasswordResultBean> response) {
                    boolean result = false;
                    String message = "";
                    if (response.body() != null) {
                        result = response.body().isResult();
                        message = response.body().getMessage();
                        Log.e(TAG, "result:" + result + "  " + "message:" + message);
                    } else {
                        Log.e(TAG, "输入内容有误，返回结果为null");
                    }
                    if (result) {
                        mView.changePasswordSuccess(message);
                    } else {
                        mView.changePasswordFail(message);
                    }
                }

                @Override
                public void onFailure(@NonNull Call<ChangePasswordResultBean> call, @NonNull Throwable t) {
                    Log.e(TAG, ApiBean.BASE_URL + ApiBean.LOGIN_URL);
                    mView.hideDialog();
                    mView.showToast(R.string.network_request_error);
                }
            });
        } else {
            mView.showToast(R.string.toast_input_all_information);
        }
    }
}
