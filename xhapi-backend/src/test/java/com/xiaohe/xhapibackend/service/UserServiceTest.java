package com.xiaohe.xhapibackend.service;

import javax.annotation.Resource;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * 用户服务测试
 */
@SpringBootTest
public class UserServiceTest {

    @Resource
    private UserService userService;

    @Test
    void userRegister() {
        String userAccount = "zhangsan";
        String userPassword = "";
        String checkPassword = "123456";
        String userName = "zhangsan";
        try {
            long result = userService.userRegister(userAccount, userPassword, checkPassword, userName);
            Assertions.assertEquals(-1, result);
            userAccount = "zhang";
            result = userService.userRegister(userAccount, userPassword, checkPassword, userName);
            Assertions.assertEquals(-1, result);
        } catch (Exception e) {

        }
    }
}
