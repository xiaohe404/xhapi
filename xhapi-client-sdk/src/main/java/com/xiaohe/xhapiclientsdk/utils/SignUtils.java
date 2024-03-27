package com.xiaohe.xhapiclientsdk.utils;

import cn.hutool.crypto.digest.DigestUtil;

public class SignUtils {

    public static String genSign(String body, String secretKey) {
        return DigestUtil.sha256Hex(body + "." + secretKey);
    }

}
