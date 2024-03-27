package com.xiaohe.xhapiinterface;

import com.xiaohe.xhapiclientsdk.client.XhApiClient;
import com.xiaohe.xhapiclientsdk.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
public class XhApiInterfaceApplicationTest {

    @Resource
    private XhApiClient xhApiClient;

    @Test
    void contextLoads() {
        String result = xhApiClient.getNameGet("xiaohe");
        User user = new User();
        user.setUsername("xiaohebao");
        String usernameByPost = xhApiClient.getUsernameByPost(user);
        System.out.println(result);
        System.out.println(usernameByPost);
    }
}
