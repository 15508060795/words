package com.hdl.words.fragment;

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
 * author: CHe
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
    private String account,prePassword,nowPassword;
    public static ForgetFragment newInstance(){
        return new ForgetFragment();
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
                            QmuiDialogHelper.showMailSuccess(_mActivity,R.string.toast_change_password_success);
                            QmuiDialogHelper.hide(1500);
                        }else{
                            ToastHelper.shortToast(_mActivity,R.string.toast_password_error);
                        }
                    }else{
                        ToastHelper.shortToast(_mActivity,R.string.toast_account_not_exist);
                    }
                }else{
                    ToastHelper.shortToast(_mActivity,R.string.toast_input_all_information);
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
        topBar.setTitle(R.string.reset_title);
    }

    @Override
    public void initData() {
        setSwipeBackEnable(true);
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
