package com.xiaohe.xhapibackend;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * 主类（项目启动入口）
 *
 */
@SpringBootApplication
@MapperScan("com.xiaohe.xhapibackend.mapper")
@EnableAspectJAutoProxy(proxyTargetClass = true, exposeProxy = true)
@EnableDubbo
public class XhApiBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(XhApiBackendApplication.class, args);
    }

}
