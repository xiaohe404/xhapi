package com.xiaohe.xhapiclientsdk.model.request;

import com.xiaohe.xhapiclientsdk.model.enums.RequestMethodEnum;
import com.xiaohe.xhapiclientsdk.model.params.LoveWordsParams;
import com.xiaohe.xhapiclientsdk.model.response.LoveWordsResponse;
import lombok.experimental.Accessors;

/**
 * 获取随机情话请求
 */
@Accessors(chain = true)
public class LoveWordsRequest extends BaseRequest<LoveWordsParams, LoveWordsResponse> {

    @Override
    public String getPath() {
        return "/loveTalk";
    }

    /**
     * 获取响应类
     *
     * @return
     */
    @Override
    public Class<LoveWordsResponse> getResponseClass() {
        return LoveWordsResponse.class;
    }


    @Override
    public String getMethod() {
        return RequestMethodEnum.GET.getValue();
    }
}
