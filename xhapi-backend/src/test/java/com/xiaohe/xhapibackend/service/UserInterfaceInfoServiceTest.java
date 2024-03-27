package com.xiaohe.xhapibackend.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
public class UserInterfaceInfoServiceTest {

    @Resource
    private UserInterfaceInfoService userInterfaceInfoService;

    @Test
    public void invokeCount() {
        boolean b = userInterfaceInfoService.invokeCount(1L, 1L, 1L);
        Assertions.assertTrue(b);
    }

    @Test
    public void getLeftNum() {
        long interfaceInfoId = 2L;
        long userId = 1734227624684699649L;

    }
}