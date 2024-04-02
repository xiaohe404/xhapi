package com.xiaohe.xhapiclientsdk.exception;

import com.xiaohe.xhapiclientsdk.common.ErrorCode;

/**
 * 自定义异常类
 */
public class SdkException extends Exception {

    private static final long serialVersionUID = 2942420535500634982L;
    private int code;

    public SdkException(int code, String message) {
        super(message);
        this.code = code;
    }

    public SdkException(String message, Throwable cause) {
        super(message, cause);
    }

    public SdkException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.code = errorCode.getCode();
    }

    public SdkException(ErrorCode errorCode, String message) {
        super(message);
        this.code = errorCode.getCode();
    }

    public int getCode() {
        return code;
    }
}
