package com.bamboo.firstdemo.controller.bean;

/**
 * @author FarryNiu 2022/10/30
 * 从微信平台获取的openid对象
 */
public class Code {
    private String code;
    public Code(){

    }
    public Code(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }


}
