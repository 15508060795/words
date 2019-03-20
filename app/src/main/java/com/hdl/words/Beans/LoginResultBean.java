package com.hdl.words.Beans;

/**
 * Created by HDL on 2018/1/11.
 */

public class LoginResultBean {

    /**
     * result : true
     * message : 登录成功
     */

    private boolean result;
    private String message;

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
