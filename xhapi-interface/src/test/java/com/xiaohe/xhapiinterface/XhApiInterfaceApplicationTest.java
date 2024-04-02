package com.xiaohe.xhapiinterface;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.xiaohe.xhapiclientsdk.client.XhApiClient;
import com.xiaohe.xhapiclientsdk.exception.SdkException;
import com.xiaohe.xhapiclientsdk.model.request.PointsRequest;
import com.xiaohe.xhapiclientsdk.model.response.BaseResponse;
import com.xiaohe.xhapiclientsdk.service.ApiService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.Map;

@SpringBootTest
public class XhApiInterfaceApplicationTest {

    @Resource
    private XhApiClient xhApiClient;

    @Resource
    private ApiService apiService;

    @Test
    void contextLoads() {

    }


    @Test
    void newSdk() throws SdkException {
        // 构建请求参数
        String requestParams = "{\"name\":\"xiaohe\"}";
        Map<String, Object> params = new Gson().fromJson(requestParams, new TypeToken<Map<String, Object>>() {
        }.getType());
        System.out.println(params);
        PointsRequest pointsRequest = new PointsRequest();
        pointsRequest.setMethod("GET");
        pointsRequest.setPath("http://localhost:8123/api/name");
        pointsRequest.setRequestParams(params);
        BaseResponse response = apiService.request(pointsRequest);
        System.out.println(response.getData());

    }

}
