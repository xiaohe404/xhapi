package com.xiaohe.xhapiinterface.controller;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.xiaohe.xhapiclientsdk.exception.SdkException;
import com.xiaohe.xhapiclientsdk.model.params.*;
import com.xiaohe.xhapiclientsdk.model.response.BaseResponse;
import com.xiaohe.xhapiclientsdk.model.response.NameResponse;
import com.xiaohe.xhapiclientsdk.model.response.RandomWallpaperResponse;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

import static com.xiaohe.xhapiinterface.utils.RequestUtils.buildUrl;
import static com.xiaohe.xhapiinterface.utils.RequestUtils.get;
import static com.xiaohe.xhapiinterface.utils.ResponseUtils.baseResponse;
import static com.xiaohe.xhapiinterface.utils.ResponseUtils.responseToMap;

@RestController
@RequestMapping("/")
public class ServiceController {

    @GetMapping("/name")
    public NameResponse getName(NameParams nameParams) {
        return JSONUtil.toBean(JSONUtil.toJsonStr(nameParams), NameResponse.class);
    }

    @PostMapping("/name")
    public NameResponse getNameByPost(@RequestBody NameParams nameParams) {
        return JSONUtil.toBean(JSONUtil.toJsonStr(nameParams), NameResponse.class);
    }

    @GetMapping("/loveTalk")
    public String randomLoveTalk() {
        return get("https://api.vvhan.com/api/text/love");
    }

    @GetMapping("/poisonousChickenSoup")
    public String getPoisonousChickenSoup() {
        return get("https://api.btstu.cn/yan/api.php?charset=utf-8&encode=json");
    }

    @GetMapping("/randomWallpaper")
    public RandomWallpaperResponse randomWallpaper(RandomWallpaperParams randomWallpaperParams) throws SdkException {
        String baseUrl = "https://api.btstu.cn/sjbz/api.php";
        String url = buildUrl(baseUrl, randomWallpaperParams);
        if (StrUtil.isAllBlank(randomWallpaperParams.getLx(), randomWallpaperParams.getMethod())) {
            url = url + "?format=json";
        } else {
            url = url + "&format=json";
        }
        return JSONUtil.toBean(get(url), RandomWallpaperResponse.class);
    }

    @GetMapping("/horoscope")
    public BaseResponse getHoroscope(HoroscopeParams horoscopeParams) throws SdkException {
        String response = get("https://api.vvhan.com/api/horoscope", horoscopeParams);
        Map<String, Object> fromResponse = responseToMap(response);
        boolean success = (boolean) fromResponse.get("success");
        if (!success) {
            BaseResponse baseResponse = new BaseResponse();
            baseResponse.setData(fromResponse);
            return baseResponse;
        }
        return JSONUtil.toBean(response, BaseResponse.class);
    }

    @GetMapping("/ipInfo")
    public BaseResponse getIpInfo(IpInfoParams ipInfoParams) {
        return baseResponse("https://api.vvhan.com/api/ipInfo", ipInfoParams);
    }

    @GetMapping("/weather")
    public BaseResponse getWeatherInfo(WeatherParams weatherParams) {
        return baseResponse("https://api.vvhan.com/api/weather", weatherParams);
    }
}
