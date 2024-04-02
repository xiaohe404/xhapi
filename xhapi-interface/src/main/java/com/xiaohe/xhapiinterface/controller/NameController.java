package com.xiaohe.xhapiinterface.controller;

import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * 名称 API
 *
 */
@RestController
@RequestMapping("/old")
public class NameController {

    @GetMapping("/name")
    public String getNameGet(String name, HttpServletRequest request) {
        System.out.println(request.getHeader("xiaohe"));
        return "GET 你的名字是：" + name;
    }

    @PostMapping("/name")
    public String getNamePost(@RequestParam String name) {
        return "POST 你的名字是：" + name;
    }

}
