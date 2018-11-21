package com.hdl.words.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.EditText;

import com.hdl.words.Beans.UserBean;
import com.hdl.words.R;
import com.hdl.words.base.BaseFragment;
import com.hdl.words.implement.IRegister;
import com.hdl.words.litepal.UserDbHelper;
import com.hdl.words.utils.QmuiDialogHelper;
import com.hdl.words.utils.ToastHelper;
import com.qmuiteam.qmui.widget.QMUITopBar;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * time: 11/1/2018
 * auther: CHe
 */
public class RegisterFragment extends BaseFragment implements IRegister {
    @BindView(R.id.topbar)
    QMUITopBar topBar;
    @BindView(R.id.et_account)
    EditText accountEt;
    @BindView(R.id.et_password)
    EditText passwordEt;
    @BindView(R.id.et_rePassword)
    EditText rePasswordEt;
    String account,password,rePassword;
    public RegisterFragment(){
        newInstance();
    }
    public static RegisterFragment newInstance(){
        RegisterFragment fragment=new RegisterFragment();
        return fragment;
    }
    public static RegisterFragment newInstance(Bundle bundle){
        RegisterFragment fragment=new RegisterFragment();
        fragment.setArguments(bundle);
        return fragment;
    }
    @OnClick({R.id.btn_register})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.btn_register:
                getData();
                if(dataIsNull()){
                    if(password.equals(rePassword)){
                        if(dataIsExist()){
                            ToastHelper.shortToast(mActivity,"该账号已存在");
                        } else{
                            new UserBean(account,password).save();
                            setNull();
                            QmuiDialogHelper.showMailSuccess(mActivity,"注册成功");
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    QmuiDialogHelper.hideLoading();
                                    pop();
                                }
                            },1000);
                        }
                    }else{
                        ToastHelper.shortToast(mActivity,"两次密码不一致");
                    }
                }else{
                    ToastHelper.shortToast(mActivity,"请填写所有信息");
                }
                break;
        }
    }
    @Override
    public int bindLayout() {
        return R.layout.fragment_register;
    }

    @Override
    public void initTopBar() {
//        topBar.setBackgroundColor(getThemeColor());
        topBar.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        topBar.addLeftBackImageButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pop();
            }
        });
        topBar.setTitle("用户注册").setTextColor(getResources().getColor(R.color.white));
    }

    @Override
    public void initData() {

    }

    @Override
    public void initListener() {

    }
    @Override
    public void getData(){
        account=accountEt.getText().toString().trim();
        password=passwordEt.getText().toString().trim();
        rePassword=rePasswordEt.getText().toString().trim();
    }

    @Override
    public boolean dataIsNull() {
        if(account.isEmpty()|password.isEmpty()|rePassword.isEmpty()){
            return false;
        }else {
            return true;
        }
    }

    @Override
    public boolean dataIsExist() {
        if(UserDbHelper.isExist(account))
            return true;
        else
            return false;
    }

    private void setNull(){
        accountEt.setText("");
        passwordEt.setText("");
        rePasswordEt.setText("");
    }
}
