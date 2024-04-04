package com.xiaohe.xhapiinterface.utils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.xiaohe.xhapiclientsdk.exception.SdkException;
import com.xiaohe.xhapiclientsdk.model.response.BaseResponse;
import com.xiaohe.xhapiinterface.exception.BusinessException;
import com.xiaohe.xhapiinterface.exception.ErrorCode;

import java.util.Map;

import static com.xiaohe.xhapiinterface.utils.RequestUtils.get;

/**
 * 响应工具类
 */
public class ResponseUtils {

    public static Map<String, Object> responseToMap(String response) {
        return new Gson().fromJson(response, new TypeToken<Map<String, Object>>() {
        }.getType());
    }

    public static <T> BaseResponse baseResponse(String baseUrl, T params) {
        String response = null;
        try {
            response = get(baseUrl, params);
            Map<String, Object> fromResponse = responseToMap(response);
            boolean success = (boolean) fromResponse.get("success");
            BaseResponse baseResponse = new BaseResponse();
            if (!success) {
                baseResponse.setData(fromResponse);
                return baseResponse;
            }
            fromResponse.remove("success");
            baseResponse.setData(fromResponse);
            return baseResponse;
        } catch (SdkException e) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR, "构建url异常");
        }
    }
}
