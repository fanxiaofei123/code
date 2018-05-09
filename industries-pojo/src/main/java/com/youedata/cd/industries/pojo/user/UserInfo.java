package com.youedata.cd.industries.pojo.user;

import com.youedata.cd.base.pojo.BaseDomain;
import javassist.SerialVersionUID;

import java.io.Serializable;
/**
 * Created by cdyoue on 2016/6/30.
 */
public class UserInfo extends BaseDomain{
    private static final long serialVersionUID = 125626467229333500L;
    private String name;

    private String userPassword;
    private String userPasswordNew;
    private String verifyCode;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getVerifyCode() {
        return verifyCode;
    }

    public void setVerifyCode(String verifyCode) {
        this.verifyCode = verifyCode;
    }

    public String getUserPasswordNew() {
        return userPasswordNew;
    }

    public void setUserPasswordNew(String userPasswordNew) {
        this.userPasswordNew = userPasswordNew;
    }

    @Override
    public String toString() {
        return "UserInfo{" +
                "name='" + name + '\'' +
                ", userPassword='" + userPassword + '\'' +
                ", userPasswordNew='" + userPasswordNew + '\'' +
                ", verifyCode='" + verifyCode + '\'' +
                '}';
    }
}
