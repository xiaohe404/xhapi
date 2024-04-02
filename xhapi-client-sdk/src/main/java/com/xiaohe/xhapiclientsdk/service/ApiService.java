package com.xiaohe.xhapiclientsdk.service;

import com.xiaohe.xhapiclientsdk.client.XhApiClient;
import com.xiaohe.xhapiclientsdk.exception.SdkException;
import com.xiaohe.xhapiclientsdk.model.request.BaseRequest;
import com.xiaohe.xhapiclientsdk.model.request.NameRequest;
import com.xiaohe.xhapiclientsdk.model.response.BaseResponse;
import com.xiaohe.xhapiclientsdk.model.response.NameResponse;

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

    NameResponse getName(NameRequest request) throws SdkException;
}
