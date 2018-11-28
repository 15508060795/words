package com.hdl.words.Beans;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by HDL on 2018/1/11.
 */

public class ApiBean {
    public static final String TRANSLATE_URL = "https://fanyi-api.baidu.com/api/trans/vip/";//百度翻译URL
    public static final String TRANSLATE_APPID="20181126000239627";
    public static final String TRANSLATE_KEY="tks1gcJHxPWfi2ZVR4AO";
    public static String getStringMD5(String sourceStr) {
        String s = null;
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            //这两行代码的作用是：
            // 将bytes数组转换为BigInterger类型。1，表示 +，即正数。
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
