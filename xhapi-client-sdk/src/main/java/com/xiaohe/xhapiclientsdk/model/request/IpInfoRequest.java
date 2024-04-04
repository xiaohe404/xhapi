package com.xiaohe.xhapiclientsdk.model.request;

import com.xiaohe.xhapiclientsdk.model.enums.RequestMethodEnum;
import com.xiaohe.xhapiclientsdk.model.params.IpInfoParams;
import com.xiaohe.xhapiclientsdk.model.response.BaseResponse;
import lombok.experimental.Accessors;

/**
 * 获取ip地址请求
 */
@Accessors(chain = true)
public class IpInfoRequest extends BaseRequest<IpInfoParams, BaseResponse> {

    @Override
    public String getPath() {
        return "/ipInfo";
    }

    /**
     * 获取响应类
     *
     * @return
     */
    @Override
    public Class<BaseResponse> getResponseClass() {
        return BaseResponse.class;
    }


    @Override
    public String getMethod() {
        return RequestMethodEnum.GET.getValue();
    }
}
