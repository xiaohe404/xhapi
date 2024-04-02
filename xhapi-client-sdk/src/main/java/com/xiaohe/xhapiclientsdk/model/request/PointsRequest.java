package com.xiaohe.xhapiclientsdk.model.request;

import com.xiaohe.xhapiclientsdk.model.response.BaseResponse;

public class PointsRequest extends BaseRequest<Object, BaseResponse> {

    private String path;

    private String method;

    @Override
    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @Override
    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    @Override
    public Class<BaseResponse> getResponseClass() {
        return BaseResponse.class;
    }
}
