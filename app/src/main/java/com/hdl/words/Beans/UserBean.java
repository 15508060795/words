package com.hdl.words.Beans;

import org.litepal.crud.DataSupport;

import java.io.Serializable;

/**
 * Date 2018/10/22 12:19
 * author HDL
 * Mail 229101253@qq.com
 */
public class UserBean extends DataSupport implements Serializable {
    private static final long serialVersionUID = -2642333866421319389L;
    private String account;
    private String password;
    public UserBean(){

    }
    public UserBean(String account, String password){
        this.account = account;
        this.password = password;
    }
    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
