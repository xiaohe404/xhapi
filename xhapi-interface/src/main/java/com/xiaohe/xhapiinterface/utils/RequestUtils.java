package com.xiaohe.xhapiinterface.utils;

import cn.hutool.http.HttpRequest;
import com.xiaohe.xhapiclientsdk.exception.ErrorCode;
import com.xiaohe.xhapiclientsdk.exception.SdkException;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;

/**
 * 请求工具类
 */
@Slf4j
public class RequestUtils {

    /**
     * 生成url
     *
     * @param baseUrl
     * @param params
     * @param <T>
     * @return
     * @throws SdkException
     */
    public static <T> String buildUrl(String baseUrl, T params) throws SdkException {
        StringBuilder url = new StringBuilder(baseUrl);
        Field[] fields = params.getClass().getDeclaredFields();
        boolean isFirstParam = true;
        for (Field field : fields) {
            field.setAccessible(true);
            String name = field.getName();
            // 跳过serialVersionUID属性
            if ("serialVersionUID".equals(name)) {
                continue;
            }
            try {
                Object value = field.get(params);
                if (value != null) {
                    if (isFirstParam) {
                        url.append("?").append(name).append("=").append(value);
                        isFirstParam = false;
                    } else {
                        url.append("&").append(name).append("=").append(value);
                    }
                }
            } catch (IllegalAccessException e) {
                throw new SdkException(ErrorCode.OPERATION_ERROR, "构建url异常");
            }
        }
        return url.toString();
    }

    /**
     * get请求
     *
     * @param baseUrl
     * @param params
     * @param <T>
     * @return
     * @throws SdkException
     */
    public static <T> String get(String baseUrl, T params) throws SdkException {
        return get(buildUrl(baseUrl, params));
    }

    /**
     * get请求
     *
     * @param url
     * @return
     */
    public static String get(String url) {
        String body = HttpRequest.get(url).execute().body();
        log.info("【interface】：请求地址：{}，响应数据：{}", url, body);
        return body;
    }
}
