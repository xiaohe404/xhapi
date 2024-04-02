package com.xiaohe.xhapiclientsdk.model.enums;

/**
 * 请求方法枚举
 */
public enum RequestMethodEnums {

    GET("GET", "GET"),
    POST("POST", "POST");

    private final String text;

    private final String value;

    RequestMethodEnums(String text, String value) {
        this.text = text;
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public String getText() {
        return text;
    }
}
