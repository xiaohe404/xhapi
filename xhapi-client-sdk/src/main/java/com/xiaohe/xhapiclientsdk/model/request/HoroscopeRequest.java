package com.xiaohe.xhapiclientsdk.model.request;

import com.xiaohe.xhapiclientsdk.model.enums.RequestMethodEnum;
import com.xiaohe.xhapiclientsdk.model.params.HoroscopeParams;
import com.xiaohe.xhapiclientsdk.model.response.BaseResponse;
import lombok.experimental.Accessors;

/**
 * 获取星座运势请求
 */
@Accessors(chain = true)
public class HoroscopeRequest extends BaseRequest<HoroscopeParams, BaseResponse> {

    @Override
    public String getPath() {
        return "/horoscope";
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
