package com.hdl.words.Beans;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class LanguageBean {
    private static volatile LanguageBean mLanguageBean;
    private final List<String> Language=new ArrayList<String>(){
        {
            add("自动检测");
            add("中文");
            add("英语");
            add("粤语");
            add("文言文");
            add("日语");
            add("韩语");
            add("法语");
            add("西班牙语");
            add("泰语");
            add("阿拉伯语");
            add("俄语");
            add("葡萄牙语");
            add("德语");
            add("意大利语");
            add("希腊语");
            add("荷兰语");
            add("波兰语");
            add("保加利亚语");
            add("爱沙尼亚语");
            add("丹麦语");
            add("芬兰语");
            add("捷克语");
            add("罗马尼亚语");
            add("斯洛文尼亚语");
            add("瑞典语");
            add("匈牙利语");
            add("繁体中文");
            add("越南语");
        }
    };
    private  final List<String>LanguageCode=new ArrayList<String>(){
        {
            add("auto");
            add("zh");
            add("en");
            add("yue");
            add("wyw");
            add("jp");
            add("kor");
            add("fra");
            add("spa");
            add("th");
            add("ara");
            add("ru");
            add("pt");
            add("de");
            add("it");
            add("el");
            add("nl");
            add("pl");
            add("bul");
            add("est");
            add("dan");
            add("fin");
            add("cs");
            add("rom");
            add("slo");
            add("swe");
            add("hu");
            add("cht");
            add("vie");
        }
    };
    private LanguageBean(){

    }
    public List<String> getLanguage() {
        return Language;
    }

    public List<String> getLanguageCode() {
        return LanguageCode;
    }

    public static LanguageBean getInstance(){
        if(mLanguageBean == null){
            synchronized (LanguageBean.class){
                if(mLanguageBean == null){
                    mLanguageBean = new LanguageBean();
                }
            }
        }
        return mLanguageBean;
    }
    private void initData(){
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
