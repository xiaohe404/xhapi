package com.xiaohe.xhapiclientsdk.model.request;

import com.xiaohe.xhapiclientsdk.model.enums.RequestMethodEnum;
import com.xiaohe.xhapiclientsdk.model.params.NameParams;
import com.xiaohe.xhapiclientsdk.model.response.NameResponse;

/**
 * 获取名称请求
 */
public class NameRequest extends BaseRequest<NameParams, NameResponse> {

    @Override
    public String getPath() {
        return "/name";
    }

    @Override
    public String getMethod() {
        return RequestMethodEnum.GET.getValue();
    }

    @Override
    public Class<NameResponse> getResponseClass() {
        return NameResponse.class;
    }
}
