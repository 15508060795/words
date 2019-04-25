package com.hdl.words.presenter;

import android.annotation.SuppressLint;
import androidx.annotation.NonNull;
import android.util.Log;

import com.hdl.words.Beans.ApiBean;
import com.hdl.words.Beans.RegisterResultBean;
import com.hdl.words.R;
import com.hdl.words.base.BasePresenter;
import com.hdl.words.fragment.RegisterFragment;
import com.hdl.words.model.IGetRegisterRequest;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Date 2019/3/3 14:38
 * author HDL
 * Description:
 */
public class RegisterPresenterImpl extends BasePresenter<RegisterFragment> implements IRegisterPresenter {
    public RegisterPresenterImpl(RegisterFragment view) {
        super(view);
    }

    @Override
    public void register(final String account, String password1, String password2) {
        if (!(account.isEmpty() | password1.isEmpty() | password2.isEmpty())) {
            if (password1.equals(password2)) {
                mView.showLoadingDialog(R.string.registering);
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(ApiBean.BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                IGetRegisterRequest request = retrofit.create(IGetRegisterRequest.class);
                Call<RegisterResultBean> call = request.getCall(account, password1);
                call.enqueue(new Callback<RegisterResultBean>() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onResponse(@NonNull Call<RegisterResultBean> call, @NonNull Response<RegisterResultBean> response) {
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
                            mView.registerSucceed(message,account);
                        } else {
                            mView.registerFail(message);
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<RegisterResultBean> call, @NonNull Throwable t) {
                        Log.e(TAG, ApiBean.BASE_URL + ApiBean.LOGIN_URL);
                        mView.hideDialog();
                        mView.showToast(R.string.network_request_error);
                    }
                });
            } else {
                mView.showToast(R.string.toast_password_disagree);
            }

        } else {
            mView.showToast(R.string.toast_complete_information);
        }
    }
}
