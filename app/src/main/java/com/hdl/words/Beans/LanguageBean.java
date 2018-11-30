package com.hdl.words.Beans;

import android.util.Log;

import java.util.ArrayList;

public class LanguageBean {
    private static volatile LanguageBean mLanguageBean;
    private static final ArrayList<String>Language=new ArrayList<>();
    private static final ArrayList<String>LanguageCode=new ArrayList<>();
    private LanguageBean(){

    }
    public ArrayList<String> getLanguage() {
        return Language;
    }

    public ArrayList<String> getLanguageCode() {
        return LanguageCode;
    }

    public static LanguageBean getInstance(){
        if(mLanguageBean==null){
            synchronized (LanguageBean.class){
                if(mLanguageBean==null){
                    mLanguageBean=new LanguageBean();
                }
            }
        }
        initData();
        return mLanguageBean;
    }
    private static void initData(){
        if(Language.isEmpty()&&LanguageCode.isEmpty()){
            Language.add("自动检测");
            Language.add("中文");
            Language.add("英语");
            Language.add("粤语");
            Language.add("文言文");
            Language.add("日语");
            Language.add("韩语");
            Language.add("法语");
            Language.add("西班牙语");
            Language.add("泰语");
            Language.add("阿拉伯语");
            Language.add("俄语");
            Language.add("葡萄牙语");
            Language.add("德语");
            Language.add("意大利语");
            Language.add("希腊语");
            Language.add("荷兰语");
            Language.add("波兰语");
            Language.add("保加利亚语");
            Language.add("爱沙尼亚语");
            Language.add("丹麦语");
            Language.add("芬兰语");
            Language.add("捷克语");
            Language.add("罗马尼亚语");
            Language.add("斯洛文尼亚语");
            Language.add("瑞典语");
            Language.add("匈牙利语");
            Language.add("繁体中文");
            Language.add("越南语");
            LanguageCode.add("auto");
            LanguageCode.add("zh");
            LanguageCode.add("en");
            LanguageCode.add("yue");
            LanguageCode.add("wyw");
            LanguageCode.add("jp");
            LanguageCode.add("kor");
            LanguageCode.add("fra");
            LanguageCode.add("spa");
            LanguageCode.add("th");
            LanguageCode.add("ara");
            LanguageCode.add("ru");
            LanguageCode.add("pt");
            LanguageCode.add("de");
            LanguageCode.add("it");
            LanguageCode.add("el");
            LanguageCode.add("nl");
            LanguageCode.add("pl");
            LanguageCode.add("bul");
            LanguageCode.add("est");
            LanguageCode.add("dan");
            LanguageCode.add("fin");
            LanguageCode.add("cs");
            LanguageCode.add("rom");
            LanguageCode.add("slo");
            LanguageCode.add("swe");
            LanguageCode.add("hu");
            LanguageCode.add("cht");
            LanguageCode.add("vie");
            Log.e("LanguageBean","List init success");
        }else{
            Log.e("LanguageBean","Language.size:"+Language.size()+"Language.size:"+LanguageCode.size());
        }

    }
}
