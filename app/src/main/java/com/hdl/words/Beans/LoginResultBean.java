package com.hdl.words.Beans;

/**
 * Created by HDL on 2018/1/11.
 */

public class LoginResultBean {

    /**
     * error : 1
     * message : 登录失败
     */

    private int error;
    private String message;

    public int getError() {
        return error;
    }

    public void setError(int error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
