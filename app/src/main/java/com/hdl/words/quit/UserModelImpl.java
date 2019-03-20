package com.hdl.words.quit;

import android.content.ContentValues;
import android.util.Log;

import com.hdl.words.Beans.UserBean;

import org.litepal.crud.DataSupport;

import java.util.List;

/**
 * Date 2018/10/22 12:42
 * author HDL
 * Mail 229101253@qq.com
 */
public class UserModelImpl implements IUserModel {
    private static volatile UserModelImpl mUserDbHelper;

    private UserModelImpl() {
    }

    public static UserModelImpl getInstance() {
        if (mUserDbHelper == null) {
            synchronized (UserModelImpl.class) {
                if (mUserDbHelper == null) {
                    mUserDbHelper = new UserModelImpl();
                }
            }
        }
        return mUserDbHelper;
    }

    private static final String TAG = "UserModelImpl";

    @Override
    public boolean isExist(String account) {
        List<UserBean> list = DataSupport.where("account=?", account).find(UserBean.class);
        if (list.size() != 0) {
            Log.e(TAG, "用户名存在");
            return true;
        } else {
            Log.e(TAG, "用户名不存在");
            return false;
        }
    }

    @Override
    public boolean isCorrect(String account, String password) {
        List<UserBean> list = DataSupport.where("account=? AND password=?", account, password).find(UserBean.class);
        if (list.size() != 0) {
            Log.e(TAG, "帐号密码正确");
            return true;
        } else {
            Log.e(TAG, "帐号密码错误");
            return false;
        }
    }

    @Override
    public void update(String account, String nowPassword) {
        ContentValues values = new ContentValues();
        values.put("password", nowPassword);
        DataSupport.updateAll(UserBean.class, values, "account=?", account);
        Log.e(TAG, "数据更新成功");
    }
}
