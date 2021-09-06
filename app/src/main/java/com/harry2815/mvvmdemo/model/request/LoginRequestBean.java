package com.harry2815.mvvmdemo.model.request;

/**
 * Created by zhanghai on 2019/5/30.
 * function：
 */
public class LoginRequestBean {

    /**
     * account : 510101201703290022
     * accountType : 1
     * app : MDM
     * client : STUDENT
     * password : LTHdH1TIDf6JilFQ6vqyCQ==
     * platform : ANDROID
     * twoFa : false
     * userType : 1
     */

    private String account = "510101201703290022";
    private int accountType = 1;
    private String app = "MDM";
    private String client = "STUDENT";
    private String password = "LTHdH1TIDf6JilFQ6vqyCQ==";
    private String platform = "ANDROID";
    private boolean twoFa = false;
    private String userType = "1";

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public int getAccountType() {
        return accountType;
    }

    public void setAccountType(int accountType) {
        this.accountType = accountType;
    }

    public String getApp() {
        return app;
    }

    public void setApp(String app) {
        this.app = app;
    }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public boolean isTwoFa() {
        return twoFa;
    }

    public void setTwoFa(boolean twoFa) {
        this.twoFa = twoFa;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }
}
