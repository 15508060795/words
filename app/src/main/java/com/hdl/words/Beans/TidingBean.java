package com.hdl.words.Beans;

/**
 * Created by HDL on 2018/1/11.
 */

public class TidingBean {
    private String img;
    private String name;
    private String msg;
    private String time;
    private int count;
    private boolean isTop;

    public String getImg() {
        return img;
    }

    public String getMsg() {
        return msg;
    }

    public String getName() {
        return name;
    }

    public String getTime() {
        return time;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public boolean getIsTop() {
        return isTop;
    }

    public void setIstop(boolean isTop) {
        this.isTop = isTop;
    }
}
