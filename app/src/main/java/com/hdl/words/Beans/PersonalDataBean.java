package com.hdl.words.Beans;

import org.litepal.crud.DataSupport;

import java.io.Serializable;

/**
 * Date 2019/1/20 17:40
 * author HDL
 * Description:
 */
public class PersonalDataBean extends DataSupport implements Serializable {
    private static final long serialVersionUID = 2642333866421319389L;
    private String name;
    private String headIcon;
    private String gender;
    private String signature;
    private String address;
    private UserBean userBean;

    public PersonalDataBean() {

    }

    public PersonalDataBean(UserBean userBean) {
        this.userBean = userBean;
    }

    public UserBean getUserBean() {
        return userBean;
    }

    public void setUserBean(UserBean userBean) {
        this.userBean = userBean;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHeadIcon() {
        return headIcon;
    }

    public void setHeadIcon(String headIcon) {
        this.headIcon = headIcon;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}

