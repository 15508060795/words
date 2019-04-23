package com.hdl.words.Beans;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by HDL on 2018/1/11.
 */

public class ApiBean {
    //http://118.24.107.35:8080/
    public static final String BASE_URL = "http://192.168.1.111:8555/";//服务器默认URL
    public static final String LOGIN_URL = "user/login?";
    public static final String REGISTER_URL = "user/register?";
    public static final String CHANGE_PASSWORD_URL = "user/changePassword?";
    public static final String CET_FOUR_WORD_URL = "word/cet_4/getWords?";
    public static final String CET_SIX_WORD_URL = "word/cet_6/getWords?";
    public static final String DAILY_WORD_URL = "word/dailyWord/getWords?";
    public static final String VOCAB_GET_URL = "word/vocab/getWords?";
    public static final String VOCAB_DELETE_URL = "word/vocab/deleteWords?";
    public static final String VOCAB_ADD_URL = "word/vocab/addWords?";
    public static final String TRANSLATE_URL = "https://fanyi-api.baidu.com/api/trans/vip/";//百度翻译URL
    public static final String TRANSLATE_APP_ID = "20181126000239627";
    public static final String TRANSLATE_SALT = "123";
    public static final String TRANSLATE_KEY = "tks1gcJHxPWfi2ZVR4AO";

    public static String getStringMD5(String sourceStr) {
        String s = null;
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            //这两行代码的作用是：
            // 将bytes数组转换为BigInteger类型。1，表示 +，即正数。
            BigInteger bigInt = new BigInteger(1, md.digest(sourceStr.getBytes()));
            // 通过format方法，获取32位的十六进制的字符串。032,代表高位补0 32位，X代表十六进制的整形数据。
            //为什么是32位？因为MD5算法返回的时一个128bit的整数，我们习惯于用16进制来表示，那就是32位。
            s = String.format("%032x", bigInt);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return s;
    }
}
