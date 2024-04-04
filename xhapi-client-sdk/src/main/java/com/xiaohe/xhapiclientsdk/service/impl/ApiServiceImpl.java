package com.xiaohe.xhapiclientsdk.service.impl;

import com.xiaohe.xhapiclientsdk.client.XhApiClient;
import com.xiaohe.xhapiclientsdk.exception.SdkException;
import com.xiaohe.xhapiclientsdk.model.request.*;
import com.xiaohe.xhapiclientsdk.model.response.*;
import com.xiaohe.xhapiclientsdk.service.ApiService;
import com.xiaohe.xhapiclientsdk.service.BaseService;

public class ApiServiceImpl extends BaseService implements ApiService {

    @Override
    public NameResponse getName(NameRequest request) throws SdkException {
        return request(request);
    }

    @Override
    public NameResponse getName(XhApiClient xhApiClient, NameRequest request) throws SdkException {
        return request(xhApiClient, request);
    }

    @Override
    public PoisonousChickenSoupResponse getPoisonousChickenSoup(PoisonousChickenSoupRequest request) throws SdkException {
        return request(request);
    }

    @Override
    public PoisonousChickenSoupResponse getPoisonousChickenSoup(XhApiClient xhApiClient, PoisonousChickenSoupRequest request) throws SdkException {
        return request(xhApiClient, request);
    }

    @Override
    public RandomWallpaperResponse getRandomWallpaper(RandomWallpaperRequest request) throws SdkException {
        return request(request);
    }

    @Override
    public RandomWallpaperResponse getRandomWallpaper(XhApiClient xhApiClient, RandomWallpaperRequest request) throws SdkException {
        return request(xhApiClient, request);
    }

    @Override
    public LoveWordsResponse getLoveWords(LoveWordsRequest request) throws SdkException {
        return request(request);
    }

    @Override
    public LoveWordsResponse getLoveWords(XhApiClient xhApiClient, LoveWordsRequest request) throws SdkException {
        return request(xhApiClient, request);
    }

    @Override
    public BaseResponse getHoroscope(HoroscopeRequest request) throws SdkException {
        return request(request);
    }

    @Override
    public BaseResponse getHoroscope(XhApiClient xhApiClient, HoroscopeRequest request) throws SdkException {
        return request(xhApiClient, request);
    }

    @Override
    public BaseResponse getIpInfo(IpInfoRequest request) throws SdkException {
        return request(request);
    }

    @Override
    public BaseResponse getIpInfo(XhApiClient xhApiClient, IpInfoRequest request) throws SdkException {
        return request(xhApiClient, request);
    }

    @Override
    public BaseResponse getWeather(WeatherRequest request) throws SdkException {
        return request(request);
    }

    @Override
    public BaseResponse getWeather(XhApiClient xhApiClient, WeatherRequest request) throws SdkException {
        return request(xhApiClient, request);
    }

}
