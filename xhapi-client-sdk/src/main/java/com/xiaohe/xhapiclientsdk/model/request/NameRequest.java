package com.xiaohe.xhapiclientsdk.model.request;

import com.xiaohe.xhapiclientsdk.model.enums.RequestMethodEnums;
import com.xiaohe.xhapiclientsdk.model.params.NameParams;
import com.xiaohe.xhapiclientsdk.model.response.NameResponse;

public class NameRequest extends BaseRequest<NameParams, NameResponse> {

    @Override
    public String getPath() {
        return "/name";
    }

    @Override
    public String getMethod() {
        return RequestMethodEnums.GET.getValue();
    }

    @Override
    public Class<NameResponse> getResponseClass() {
        return NameResponse.class;
    }
}
