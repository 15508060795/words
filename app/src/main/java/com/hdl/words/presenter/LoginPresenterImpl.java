package com.hdl.words.presenter;

import android.annotation.SuppressLint;
import androidx.annotation.NonNull;
import android.util.Log;

import com.hdl.words.Beans.ApiBean;
import com.hdl.words.Beans.LoginResultBean;
import com.hdl.words.R;
import com.hdl.words.base.BasePresenter;
import com.hdl.words.fragment.LoginFragment;
import com.hdl.words.model.IGetLoginResult;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Date 2019/3/2 16:24
 * author HDL
 * Description:
 */
public class LoginPresenterImpl extends BasePresenter<LoginFragment> implements ILoginPresenter {
    public LoginPresenterImpl(LoginFragment view) {
        super(view);
    }

    @Override
    public void login(final String account, String password) {
        if (!(account.isEmpty() | password.isEmpty())) {
            mView.showLoadingDialog(R.string.logging);
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(ApiBean.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            IGetLoginResult request = retrofit.create(IGetLoginResult.class);
            Call<LoginResultBean> call = request.getCall(account, password);
            call.enqueue(new Callback<LoginResultBean>() {
                @SuppressLint("SetTextI18n")
                @Override
                public void onResponse(@NonNull Call<LoginResultBean> call, @NonNull Response<LoginResultBean> response) {
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
                        mView.loginSucceed(message,account);
                    } else {
                        mView.loginFail(message);
                    }
                }

                @Override
                public void onFailure(@NonNull Call<LoginResultBean> call, @NonNull Throwable t) {
                    Log.e(TAG, ApiBean.BASE_URL + ApiBean.LOGIN_URL);
                    mView.hideDialog();
                    mView.showToast(R.string.network_request_error);
                }
            });
        } else {
            mView.showToast(R.string.toast_complete_information);
        }
    }

    @Override
    public void skipRegister() {
        mView.skipRegister();
    }

    @Override
    public void skipForget() {
        mView.skipForget();
    }
}
