package com.hdl.words.Beans;

import java.util.ArrayList;
import java.util.List;

public class LanguageBean {
    private static volatile LanguageBean mLanguageBean;
    private final List<String> mLanguageList = new ArrayList<String>() {
        {
            add("自动检测");
            add("中文");
            add("英语");
            add("粤语");
            add("文言文");
            add("日语");
            add("繁体中文");
        }
    };
    private final List<String> mLanguageCodeList = new ArrayList<String>() {
        {
            add("auto");
            add("zh");
            add("en");
            add("yue");
            add("wyw");
            add("jp");
            add("cht");
        }
    };

    private LanguageBean() {

    }

    public List<String> getLanguageList() {
        return mLanguageList;
    }

    public List<String> getLanguageCodeList() {
        return mLanguageCodeList;
    }

    public static LanguageBean getInstance() {
        if (mLanguageBean == null) {
            synchronized (LanguageBean.class) {
                if (mLanguageBean == null) {
                    mLanguageBean = new LanguageBean();
                }
            }
        }
        return mLanguageBean;
    }
}
