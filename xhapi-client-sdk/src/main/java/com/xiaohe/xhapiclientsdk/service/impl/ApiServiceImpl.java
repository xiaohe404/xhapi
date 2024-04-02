package com.xiaohe.xhapiclientsdk.service.impl;

import cn.hutool.core.net.URLEncodeUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.json.JSONUtil;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.xiaohe.xhapiclientsdk.client.XhApiClient;
import com.xiaohe.xhapiclientsdk.common.ErrorCode;
import com.xiaohe.xhapiclientsdk.common.ErrorResponse;
import com.xiaohe.xhapiclientsdk.exception.SdkException;
import com.xiaohe.xhapiclientsdk.model.request.BaseRequest;
import com.xiaohe.xhapiclientsdk.model.request.NameRequest;
import com.xiaohe.xhapiclientsdk.model.response.BaseResponse;
import com.xiaohe.xhapiclientsdk.model.response.NameResponse;
import com.xiaohe.xhapiclientsdk.service.ApiService;
import com.xiaohe.xhapiclientsdk.utils.SignUtils;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Data
public class ApiServiceImpl implements ApiService {

    private XhApiClient xhApiClient;

    private String gatewayHost = "http://localhost:8090/api";
//    private String gatewayHost = "http://10.0.16.2:8090/api";

    @Override
    public <T, R extends BaseResponse> R request(BaseRequest<T, R> request) throws SdkException {
        if (xhApiClient == null || StrUtil.hasBlank(xhApiClient.getAccessKey(), xhApiClient.getSecretKey())) {
            throw new SdkException(ErrorCode.NO_AUTH_ERROR, "请先配置密钥 ak / sk");
        }
        R response;
        try {
            Class<R> clazz = request.getResponseClass();
            response = clazz.newInstance();
        } catch (Exception e) {
            throw new SdkException(ErrorCode.OPERATION_ERROR, e.getMessage());
        }
        HttpResponse httpResponse = doRequest(request);
        String body = httpResponse.body();
        Map<String, Object> data = new HashMap<>();
        if (httpResponse.getStatus() == 200) {
            try {
                data = new Gson().fromJson(body, new TypeToken<Map<String, Object>>() {
                }.getType());
            } catch (JsonSyntaxException e) {
                data.put("value", body);
            }
        } else {
            ErrorResponse errorResponse = JSONUtil.toBean(body, ErrorResponse.class);
            data.put("errorMessage", errorResponse.getMessage());
            data.put("code", errorResponse.getCode());
        }
        response.setData(data);
        return response;
    }

    @Override
    public <T, R extends BaseResponse> R request(XhApiClient xhApiClient, BaseRequest<T, R> request) throws SdkException {
        checkConfig(xhApiClient);
        return request(request);
    }

    /**
     * 配置校验
     *
     * @param xhApiClient
     * @throws SdkException
     */
    private void checkConfig(XhApiClient xhApiClient) throws SdkException {
        if (xhApiClient == null && this.getXhApiClient() == null) {
            throw new SdkException(ErrorCode.NO_AUTH_ERROR, "请先配置密钥 ak / sk");
        }
        if (xhApiClient != null && StrUtil.isAllNotBlank(xhApiClient.getAccessKey(), xhApiClient.getSecretKey())) {
            this.setXhApiClient(xhApiClient);
        }
    }

    /**
     * 执行请求
     *
     * @param request
     * @param <T>
     * @param <R>
     * @return
     * @throws SdkException
     */
    private <T, R extends BaseResponse> HttpResponse doRequest(BaseRequest<T, R> request) throws SdkException {
        try (HttpResponse httpResponse = getHttpRequestByRequestMethod(request).execute()) {
            return httpResponse;
        } catch (Exception e) {
            throw new SdkException(ErrorCode.OPERATION_ERROR, e.getMessage());
        }
    }

    /**
     * 根据请求方法获取 http 响应
     *
     * @param request
     * @param <T>
     * @param <R>
     * @return
     * @throws SdkException
     */
    private <T, R extends BaseResponse> HttpRequest getHttpRequestByRequestMethod(BaseRequest<T, R> request) throws SdkException {
        if (ObjectUtil.isEmpty(request)) {
            throw new SdkException(ErrorCode.OPERATION_ERROR, "请求参数错误");
        }
        String method = request.getMethod().trim().toUpperCase();
        String path = request.getPath().trim();
        if (StrUtil.isBlank(method)) {
            throw new SdkException(ErrorCode.OPERATION_ERROR, "请求方法不存在");
        }
        if (StrUtil.isBlank(path)) {
            throw new SdkException(ErrorCode.OPERATION_ERROR, "请求路径不存在");
        }

        if (path.startsWith(gatewayHost)) {
            path = path.substring(gatewayHost.length());
        }
        log.info("请求方法：{}，请求路径：{}，请求参数：{}", method, path, request.getRequestParams());
        HttpRequest httpRequest;
        switch (method) {
            case "GET": {
                httpRequest = HttpRequest.get(splicingGetRequest(request, path));
                break;
            }
            case "POST": {
                httpRequest = HttpRequest.post(gatewayHost + path);
                break;
            }
            default: {
                throw new RuntimeException("不支持该请求");
            }
        }
        return httpRequest.addHeaders(getHeaderMap(JSONUtil.toJsonStr(request), xhApiClient))
                .body(JSONUtil.toJsonStr(request.getRequestParams()));
    }

    /**
     * 拼接 GET 请求 url
     *
     * @param request
     * @param path
     * @param <T>
     * @param <R>
     * @return
     */
    private <T, R extends BaseResponse> String splicingGetRequest(BaseRequest<T, R> request, String path) {
        StringBuilder urlBuilder = new StringBuilder(gatewayHost);
        // urlBuilder 最后是 / 结尾且 path 以 / 开头的情况下，去掉 urlBuilder 结尾的 /
        if (urlBuilder.toString().endsWith("/") && path.startsWith("/")) {
            urlBuilder.setLength(urlBuilder.length() - 1);
        }
        urlBuilder.append(path);
        if (!request.getRequestParams().isEmpty()) {
            urlBuilder.append("?");
            for (Map.Entry<String, Object> entry : request.getRequestParams().entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue().toString();
                urlBuilder.append(key).append("=").append(value).append("&");
            }
            urlBuilder.deleteCharAt(urlBuilder.length() - 1);
        }
        log.info("GET请求路径：{}", urlBuilder);
        return urlBuilder.toString();
    }

    /**
     * 封装请求头
     *
     * @param body
     * @return
     */
    private Map<String, String> getHeaderMap(String body, XhApiClient xhApiClient) {
        Map<String, String> hashMap = new HashMap<>();
        hashMap.put("accessKey", xhApiClient.getAccessKey());
        hashMap.put("nonce", RandomUtil.randomNumbers(4));
        hashMap.put("timestamp", String.valueOf(System.currentTimeMillis()));
        hashMap.put("body", URLEncodeUtil.encode(body));
        hashMap.put("sign", SignUtils.genSign(body, xhApiClient.getSecretKey()));
        return hashMap;
    }


    @Override
    public NameResponse getName(NameRequest request) throws SdkException {
        return request(request);
    }
}
