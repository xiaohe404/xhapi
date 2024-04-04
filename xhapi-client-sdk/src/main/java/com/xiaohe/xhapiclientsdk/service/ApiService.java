package com.xiaohe.xhapiclientsdk.service;

import com.xiaohe.xhapiclientsdk.client.XhApiClient;
import com.xiaohe.xhapiclientsdk.exception.SdkException;
import com.xiaohe.xhapiclientsdk.model.request.*;
import com.xiaohe.xhapiclientsdk.model.response.*;

/**
 * API 接口服务
 */
public interface ApiService {

    /**
     * 通用请求
     *
     * @param request
     * @param <T>
     * @param <R>
     * @return
     * @throws SdkException
     */
    <T, R extends BaseResponse> R request(BaseRequest<T, R> request) throws SdkException;

    /**
     * 通用请求
     *
     * @param xhApiClient
     * @param request
     * @param <T>
     * @param <R>
     * @return
     * @throws SdkException
     */
    <T, R extends BaseResponse> R request(XhApiClient xhApiClient, BaseRequest<T, R> request) throws SdkException;

    /**
     * 获取名称
     * @param request
     * @return
     * @throws SdkException
     */
    NameResponse getName(NameRequest request) throws SdkException;

    /**
     * 获取名称
     * @param xhApiClient
     * @param request
     * @return
     * @throws SdkException
     */
    NameResponse getName(XhApiClient xhApiClient, NameRequest request) throws SdkException;

    /**
     * 获取毒鸡汤
     * @param request
     * @return
     * @throws SdkException
     */
    PoisonousChickenSoupResponse getPoisonousChickenSoup(PoisonousChickenSoupRequest request) throws SdkException;

    /**
     * 获取毒鸡汤
     * @param xhApiClient
     * @param request
     * @return
     * @throws SdkException
     */
    PoisonousChickenSoupResponse getPoisonousChickenSoup(XhApiClient xhApiClient, PoisonousChickenSoupRequest request) throws SdkException;

    /**
     * 获取随机壁纸
     * @param request
     * @return
     * @throws SdkException
     */
    RandomWallpaperResponse getRandomWallpaper(RandomWallpaperRequest request) throws SdkException;

    /**
     * 获取随机壁纸
     * @param xhApiClient
     * @param request
     * @return
     * @throws SdkException
     */
    RandomWallpaperResponse getRandomWallpaper(XhApiClient xhApiClient, RandomWallpaperRequest request) throws SdkException;

    /**
     * 获取随机情话
     * @param request
     * @return
     * @throws SdkException
     */
    LoveWordsResponse getLoveWords(LoveWordsRequest request) throws SdkException;

    /**
     * 获取随机情话
     * @param xhApiClient
     * @param request
     * @return
     * @throws SdkException
     */
    LoveWordsResponse getLoveWords(XhApiClient xhApiClient, LoveWordsRequest request) throws SdkException;

    /**
     * 获取星座运势
     * @param request
     * @return
     * @throws SdkException
     */
    BaseResponse getHoroscope(HoroscopeRequest request) throws SdkException;

    /**
     * 获取星座运势
     * @param xhApiClient
     * @param request
     * @return
     * @throws SdkException
     */
    BaseResponse getHoroscope(XhApiClient xhApiClient, HoroscopeRequest request) throws SdkException;

    /**
     * 获取ip
     * @param request
     * @return
     * @throws SdkException
     */
    BaseResponse getIpInfo(IpInfoRequest request) throws SdkException;

    /**
     * 获取ip
     * @param xhApiClient
     * @param request
     * @return
     * @throws SdkException
     */
    BaseResponse getIpInfo(XhApiClient xhApiClient, IpInfoRequest request) throws SdkException;

    /**
     * 获取天气
     * @param request
     * @return
     * @throws SdkException
     */
    BaseResponse getWeather(WeatherRequest request) throws SdkException;

    /**
     * 获取天气
     * @param xhApiClient
     * @param request
     * @return
     * @throws SdkException
     */
    BaseResponse getWeather(XhApiClient xhApiClient, WeatherRequest request) throws SdkException;
}
