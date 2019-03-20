package com.hdl.words.quit;

/**
 * Date 2019/3/3 14:18
 * author HDL
 * Description:
 */
public interface IUserModel {
    boolean isExist(String account);

    boolean isCorrect(String account, String password);

    void update(String account, String nowPassword);
}
