package com.xiaohe.xhapigateway;

import cn.hutool.core.net.URLDecoder;
import com.xiaohe.xhapiclientsdk.utils.SignUtils;
import com.xiaohe.xhapicommon.model.entity.InterfaceInfo;
import com.xiaohe.xhapicommon.model.entity.User;
import com.xiaohe.xhapicommon.service.InnerInterfaceInfoService;
import com.xiaohe.xhapicommon.service.InnerUserInterfaceInfoService;
import com.xiaohe.xhapicommon.service.InnerUserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.DubboReference;
import org.reactivestreams.Publisher;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.http.server.reactive.ServerHttpResponseDecorator;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Slf4j
@Component
public class CustomGlobalFilter implements GlobalFilter, Ordered {

    private static final List<String> IP_WHITE_LIST
            = new ArrayList<>(Arrays.asList("127.0.0.1", "10.0.16.2", "172.17.0.7"));

//    private static final String INTERFACE_HOST = "http://10.0.16.2:8123";
    private static final String INTERFACE_HOST = "http://localhost:8123";

    @DubboReference
    private InnerUserService innerUserService;

    @DubboReference
    private InnerInterfaceInfoService innerInterfaceInfoService;

    @DubboReference
    private InnerUserInterfaceInfoService innerUserInterfaceInfoService;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        log.info("custom global filter");
        // 1 请求日志
        ServerHttpRequest request = exchange.getRequest();
        String path = INTERFACE_HOST + request.getPath().value();
        String method = request.getMethod().toString();
        String sourceAddress = request.getLocalAddress().getHostString();
        log.info("请求唯一标识：{}", request.getId());
        log.info("请求路径：{}", path);
        log.info("请求方法：{}", method);
        log.info("请求参数：{}", request.getQueryParams());
        log.info("请求来源地址：{}", sourceAddress);
        // 2 访问控制 - 黑白名单
        ServerHttpResponse response = exchange.getResponse();
        if (!IP_WHITE_LIST.contains(sourceAddress)) {
            return handleNoAuth(response);
        }
        log.info("IP白名单校验通过~");
        // 3 用户鉴权（判断 ak、sk 是否合法）
        HttpHeaders headers = request.getHeaders();
        String accessKey = headers.getFirst("accessKey");
        long nonce = Long.parseLong(Objects.requireNonNull(headers.getFirst("nonce")));
        long timestamp = Long.parseLong(Objects.requireNonNull(headers.getFirst("timestamp")));
        String body = URLDecoder.decode(headers.getFirst("body"), StandardCharsets.UTF_8);
        String sign = headers.getFirst("sign");
        // 从数据库查出是否已分配给用户 accessKey
        User invokeUser = null;
        try {
            invokeUser = innerUserService.getInvokeUser(accessKey);
        } catch (Exception e) {
            log.error("getInvokeUser error", e);
        }
        if (invokeUser == null) {
            return handleNoAuth(response);
        }
        log.info("用户校验通过~");
        // nonce 是一个长度为 4 的整数
        if (nonce > 9999) {
            return handleNoAuth(response);
        }
        log.info("随机数加密校验通过~");
        //时间和当前时间不能超过 5 分钟
        long FIVE_MINUTES = 5 * 60 * 1000;
        if (System.currentTimeMillis() - timestamp > FIVE_MINUTES) {
            return handleNoAuth(response);
        }
        log.info("时限校验通过~");
        // 从数据库查出 secretKey
        String secretKey = invokeUser.getSecretKey();
        if (StringUtils.isBlank(secretKey)) {
            return handleNoAuth(response);
        }
        log.info("密钥校验通过~");
        String serverSign = SignUtils.genSign(body, secretKey);
        if (!serverSign.equals(sign)) {
            return handleNoAuth(response);
        }
        log.info("签名校验通过~");
        // 4 从数据库查询模拟接口是否存在，以及请求方法是否匹配（还可以校验请求参数）
        InterfaceInfo interfaceInfo = null;
        try {
            interfaceInfo = innerInterfaceInfoService.getInterfaceInfo(path, method);
        } catch (Exception e) {
            log.error("getInterfaceInfo error", e);
        }
        if (interfaceInfo == null) {
            return handleNoAuth(response);
        }
        log.info("接口校验通过~");
        // 判断是否有调用次数
        Long consumePoints = interfaceInfo.getConsumePoints();
        Long points = invokeUser.getPoints();
        if (points.compareTo(consumePoints) < 0) {
            return handleInvokeError(response);
        }
        log.info("用户剩余积分为：{}次", points);
        return handleResponse(exchange, chain, interfaceInfo, invokeUser);
    }

    /**
     * 处理响应
     * @param exchange
     * @param chain
     * @return
     */
    public Mono<Void> handleResponse(ServerWebExchange exchange, GatewayFilterChain chain,
                                     InterfaceInfo interfaceInfo, User user) {
        try {
            ServerHttpResponse originalResponse = exchange.getResponse();
            // 缓存数据的工厂
            DataBufferFactory bufferFactory = originalResponse.bufferFactory();
            // 拿到响应码
            HttpStatus statusCode = originalResponse.getStatusCode();
            if (statusCode == HttpStatus.OK) {
                // 装饰，增强能力
                ServerHttpResponseDecorator decoratedResponse = new ServerHttpResponseDecorator(originalResponse) {
                    // 等调用完转发的接口后才会执行
                    @Override
                    public Mono<Void> writeWith(Publisher<? extends DataBuffer> body) {
                        log.info("body instanceof Flux: {}", (body instanceof Flux));
                        if (body instanceof Flux) {
                            Flux<? extends DataBuffer> fluxBody = Flux.from(body);
                            // 往返回值里写数据
                            // 拼接字符串
                            return super.writeWith(fluxBody.map(dataBuffer -> {
                                // 7.调用成功，接口调用次数 + 1 invokeCount
                                try {
                                    innerUserInterfaceInfoService.invokeCount(interfaceInfo.getId(), user.getId(), interfaceInfo.getConsumePoints());
                                } catch (Exception e) {
                                    log.error("invokeCount error", e);
                                }
                                byte[] content = new byte[dataBuffer.readableByteCount()];
                                dataBuffer.read(content);
                                DataBufferUtils.release(dataBuffer);//释放掉内存
                                // 构建日志
                                StringBuilder sb2 = new StringBuilder(200);
                                List<Object> rspArgs = new ArrayList<>();
                                rspArgs.add(originalResponse.getStatusCode());
                                String data = new String(content, StandardCharsets.UTF_8); //data
                                sb2.append(data);
                                // 打印日志
                                log.info("响应结果：{}", data);
                                return bufferFactory.wrap(content);
                            }));
                        } else {
                            // 8. 调用失败，返回一个规范的错误码
                            log.error("<--- {} 响应code异常", getStatusCode());
                        }
                        return super.writeWith(body);
                    }
                };
                // 设置 response 对象为装饰过的
                return chain.filter(exchange.mutate().response(decoratedResponse).build());
            }
            return chain.filter(exchange); // 降级处理返回数据
        } catch (Exception e) {
            log.error("网关处理响应异常" + e);
            return chain.filter(exchange);
        }
    }


    @Override
    public int getOrder() {
        return -1;
    }

    public Mono<Void> handleNoAuth(ServerHttpResponse response) {
        response.setStatusCode(HttpStatus.FORBIDDEN);
        return response.setComplete();
    }

    public Mono<Void> handleInvokeError(ServerHttpResponse response) {
        response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
        return response.setComplete();
    }

}
