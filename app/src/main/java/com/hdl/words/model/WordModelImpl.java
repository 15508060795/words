package com.hdl.words.model;

import android.util.Log;

import com.hdl.words.Beans.WordResultBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Date 2019/4/18 21:36
 * author hdl
 * Description:
 */
public class WordModelImpl {
    private List<WordResultBean.DataBean> mDataList;
    private static WordModelImpl mModel = null;
    private static final String TAG = "WordModelImpl";

    private WordModelImpl() {
    }

    public static WordModelImpl getInstance() {
        if (mModel == null) {
            synchronized (WordModelImpl.class) {
                if (mModel == null) {
                    mModel = new WordModelImpl();
                }
            }
        }
        return mModel;
    }

    public List<WordResultBean.DataBean> getDataList() {
        return mDataList;
    }

    public void setDataList(List<WordResultBean.DataBean> dataList) {
        mDataList = new ArrayList<>();
        mDataList.clear();
        mDataList.addAll(dataList);
        Log.e(TAG,"setDataList");
    }

}
