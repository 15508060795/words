package com.hdl.words.Beans;

/**
 * Date 2018/12/28 22:48
 * author HDL
 * Mail 229101253@qq.com
 */
public class ItemDescription {
    private String mName;
    private int mIconRes;

    public ItemDescription() {

    }

    public ItemDescription(String name, int resId) {
        this.mName = name;
        this.mIconRes = resId;
    }

    public String getName() {
        return mName;
    }

    public void setName(String mName) {
        this.mName = mName;
    }

    public int getIconRes() {
        return mIconRes;
    }

    public void setIconRes(int mIconRes) {
        this.mIconRes = mIconRes;
    }
}
