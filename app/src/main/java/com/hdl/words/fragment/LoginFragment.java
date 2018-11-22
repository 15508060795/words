package com.hdl.words.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.hdl.words.R;
import com.hdl.words.SharedPreferences.MySession;
import com.hdl.words.base.BaseFragment;
import com.hdl.words.litepal.UserDbHelper;
import com.hdl.words.utils.ToastHelper;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Date 2018/8/2 10:06
 * auther HDL
 * Mail 229101253@qq.com
 */
public class LoginFragment extends BaseFragment {
    @BindView(R.id.et_login_ac)//帐号
            EditText accountEt;
    @BindView(R.id.et_login_pw)//密码
            EditText paaswordEt;
    String account,password;
    public static LoginFragment newInstance(){
        LoginFragment fragment=new LoginFragment();
        return fragment;
    }
    public static LoginFragment newInstance(Bundle bundle){
        LoginFragment fragment=new LoginFragment();
        fragment.setArguments(bundle);
        return fragment;
    }
    @OnClick({R.id.btn_login,R.id.tv_login_rePw,R.id.tv_login_newAdm})
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_login:
                getData();
                if(isNull()){
                    if(UserDbHelper.isCorrect(account,password)){
                        MySession.setLoginState(mActivity,true);
                        startWithPop(new MainFragment());
                    }else{
                        ToastHelper.shortToast(mActivity,R.string.toast_accountAndPassword);
                    }
                }else{
                    ToastHelper.shortToast(mActivity,R.string.toast_complete_information);
                }
                break;
            case R.id.tv_login_rePw:
                start(ForgetFragment.newInstance());
                break;
            case R.id.tv_login_newAdm:
                start(RegisterFragment.newInstance());
                break;
        }
    }
    /*    public void doLogin(final String username, final String password){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    OkHttpClient client=new OkHttpClient();
                    FormBody formBody=new FormBody.Builder()
                            .add("username",username)
                            .add("password",password)
                            .build();
                    Request request=new Request.Builder()
                            .url(ApiBean.BASE_URL+ApiBean.LOGIN_URL)
                            .post(formBody)
                            .build();
                    Response response = null;
                    try{
                        response=client.newCall(request).execute();
                        if(response.isSuccessful()) {
                            onLoginSuccess(response);
                        } else {
                            onLoginError(response);
                        }
                    }catch (IOException e){
                        e.printStackTrace();
                    }
                }
            }).start();
        }*/
    @Override
    public int bindLayout() {
        return R.layout.fragment_login;
    }

    @Override
    public void initData() {

    }

    @Override
    public void initTopBar() {

    }

    @Override
    public void initListener() {

    }
    private void getData(){
        account=accountEt.getText().toString().trim();
        password=paaswordEt.getText().toString().trim();
    }
    private boolean isNull(){
        if(account.isEmpty()|password.isEmpty()){
            return false;
        }else {
            return true;
        }
    }
}
