package com.xiaohe.xhapiclientsdk.model.request;

import com.xiaohe.xhapiclientsdk.model.enums.RequestMethodEnum;
import com.xiaohe.xhapiclientsdk.model.params.RandomWallpaperParams;
import com.xiaohe.xhapiclientsdk.model.response.RandomWallpaperResponse;
import lombok.experimental.Accessors;

/**
 * 获取随机壁纸请求
 */
@Accessors(chain = true)
public class RandomWallpaperRequest extends BaseRequest<RandomWallpaperParams, RandomWallpaperResponse> {
    @Override
    public String getPath() {
        return "/randomWallpaper";
    }

    /**
     * 获取响应类
     *
     * @return
     */
    @Override
    public Class<RandomWallpaperResponse> getResponseClass() {
        return RandomWallpaperResponse.class;
    }


    @Override
    public String getMethod() {
        return RequestMethodEnum.GET.getValue();
    }
}
