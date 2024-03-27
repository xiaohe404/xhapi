package com.xiaohe.xhapiclientsdk;

import com.xiaohe.xhapiclientsdk.client.XhApiClient;
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

    private String accessKey;

    private String secretKey;

    @Bean
    public XhApiClient xhApiClient() {
        return new XhApiClient(accessKey, secretKey);
    }

}
