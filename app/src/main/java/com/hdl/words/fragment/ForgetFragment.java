package com.hdl.words.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.EditText;

import com.hdl.words.R;
import com.hdl.words.base.BaseFragment;
import com.hdl.words.implement.ILogin;
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
public class ForgetFragment extends BaseFragment implements ILogin {
    @BindView(R.id.topBar)
    QMUITopBar topBar;
    @BindView(R.id.et_account)
    EditText accountEt;
    @BindView(R.id.et_prePassword)
    EditText prePasswordEt;
    @BindView(R.id.et_nowPassword)
    EditText nowPasswordEt;
    String account,prePassword,nowPassword;
    public static ForgetFragment newInstance(){
        ForgetFragment fragment=new ForgetFragment();
        return fragment;
    }
    public static ForgetFragment newInstance(Bundle bundle){
        ForgetFragment fragment=new ForgetFragment();
        fragment.setArguments(bundle);
        return fragment;
    }
    @OnClick({R.id.btn_submit})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.btn_submit:
                getData();
                if(dataIsNull()){
                    if(UserDbHelper.isExist(account)){
                        if(dataIsCurrent()){
                            UserDbHelper.update(account,nowPassword);
                            QmuiDialogHelper.showMailSuccess(mActivity,R.string.toast_change_password_success);
                            QmuiDialogHelper.hide(1500);
                        }else{
                            ToastHelper.shortToast(mActivity,R.string.toast_password_error);
                        }
                    }else{
                        ToastHelper.shortToast(mActivity,R.string.toast_account_not_exist);
                    }
                }else{
                    ToastHelper.shortToast(mActivity,R.string.toast_input_all_information);
                }
                break;
            default:
                break;
        }

    }
    @Override
    public int bindLayout() {
        return R.layout.fragment_forget;
    }

    @Override
    public void initTopBar() {
        topBar.setBackgroundColor(getResources().getColor(R.color.color_topBar_bg));
        topBar.addLeftBackImageButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pop();
            }
        });
        topBar.setTitle(R.string.reset_title).setTextColor(getResources().getColor(R.color.color_topBar_title_white));
    }

    @Override
    public void initData() {

    }

    @Override
    public void initListener() {

    }



    @Override
    public void getData() {
        account=accountEt.getText().toString().trim();
        prePassword=prePasswordEt.getText().toString().trim();
        nowPassword=nowPasswordEt.getText().toString().trim();
    }
    @Override
    public boolean dataIsNull() {
        if(account.isEmpty()|prePassword.isEmpty()|nowPassword.isEmpty()){
            return false;
        }else {
            return true;
        }
    }

    @Override
    public boolean dataIsCurrent() {
        if(UserDbHelper.isCorrect(account,prePassword))
            return true;
        else
            return false;
    }

}
