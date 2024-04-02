package com.xiaohe.xhapiclientsdk.common;

import lombok.Data;

@Data
public class ErrorResponse {
    private String message;
    private int code;
}
