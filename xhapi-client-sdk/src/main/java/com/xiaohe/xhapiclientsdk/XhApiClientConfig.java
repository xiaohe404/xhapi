package com.xiaohe.xhapiclientsdk;

import cn.hutool.core.util.StrUtil;
import com.xiaohe.xhapiclientsdk.client.XhApiClient;
import com.xiaohe.xhapiclientsdk.service.ApiService;
import com.xiaohe.xhapiclientsdk.service.impl.ApiServiceImpl;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties("xhapi.client")
@Data
@ComponentScan
public class XhApiClientConfig {

    /**
     * ak
     */
    private String accessKey;

    /**
     * sk
     */
    private String secretKey;

    /**
     * 网关
     */
    private String host;

    @Bean
    public XhApiClient xhApiClient() {
        return new XhApiClient(accessKey, secretKey);
    }

    @Bean
    public ApiService apiService() {
        ApiServiceImpl apiService = new ApiServiceImpl();
        apiService.setXhApiClient(new XhApiClient(accessKey, secretKey));
        if (StrUtil.isNotBlank(host)) {
            apiService.setGatewayHost(host);
        }
        return apiService;
    }

}
