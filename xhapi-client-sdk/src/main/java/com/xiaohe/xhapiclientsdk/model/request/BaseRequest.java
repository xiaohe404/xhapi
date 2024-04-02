package com.xiaohe.xhapiclientsdk.model.request;

import cn.hutool.json.JSONUtil;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.xiaohe.xhapiclientsdk.model.response.BaseResponse;

import java.util.HashMap;
import java.util.Map;

public abstract class BaseRequest<T, R extends BaseResponse> {

    private Map<String, Object> requestParams = new HashMap<>();

    /**
     * 获取路径
     *
     * @return
     */
    public abstract String getPath();

    /**
     * 获取方法
     *
     * @return
     */
    public abstract String getMethod();

    /**
     * 获取响应类
     *
     * @return
     */
    public abstract Class<R> getResponseClass();

    @JsonAnyGetter
    public Map<String, Object> getRequestParams() {
        return requestParams;
    }

    public void setRequestParams(T params) {
        this.requestParams = new Gson().fromJson(JSONUtil.toJsonStr(params), new TypeToken<Map<String, Object>>() {
        }.getType());
    }
}
