package com.hdl.words.model;

import android.util.Log;

import com.hdl.words.Beans.PersonalInfoBean;

/**
 * Date 2019/4/18 21:36
 * author hdl
 * Description:
 */
public class PersonalInfoModelImpl {
    private PersonalInfoBean.DataBean mBean;
    private static PersonalInfoModelImpl mModel = null;
    private static final String TAG = PersonalInfoModelImpl.class.getSimpleName();

    private PersonalInfoModelImpl() {
    }

    public static PersonalInfoModelImpl getInstance() {
        if (mModel == null) {
            synchronized (PersonalInfoModelImpl.class) {
                if (mModel == null) {
                    mModel = new PersonalInfoModelImpl();
                }
            }
        }
        return mModel;
    }

    public PersonalInfoBean.DataBean getBean() {
        return mBean;
    }

    public void setBean(PersonalInfoBean.DataBean bean) {
        mBean = bean;
        Log.e(TAG,"setBean");
    }

}
