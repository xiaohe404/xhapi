package com.xiaohe.xhapiinterface.controller;

import com.xiaohe.xhapiclientsdk.model.User;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * 名称 API
 *
 */
@RestController
@RequestMapping("/name")
public class NameController {

    @GetMapping
    public String getNameGet(String name, HttpServletRequest request) {
        System.out.println(request.getHeader("xiaohe"));
        return "GET 你的名字是：" + name;
    }

    @PostMapping
    public String getNamePost(@RequestParam String name) {
        return "POST 你的名字是：" + name;
    }

    @PostMapping("/user")
    public String getUsernameByPost(@RequestBody User user, HttpServletRequest request) {
        return "POST 你的名字是：" + user.getUsername();
    }

}
