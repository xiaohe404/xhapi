package com.xiaohe.xhapiinterface.controller;

import cn.hutool.json.JSONUtil;
import com.xiaohe.xhapiclientsdk.model.params.NameParams;
import com.xiaohe.xhapiclientsdk.model.response.NameResponse;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")
public class ServiceController {

    @GetMapping("/name")
    public NameResponse getName(NameParams nameParams) {
        return JSONUtil.toBean(JSONUtil.toJsonStr(nameParams), NameResponse.class);
    }

    @PostMapping("/name")
    public NameResponse getNameByPost(@RequestBody NameParams nameParams) {
        return JSONUtil.toBean(JSONUtil.toJsonStr(nameParams), NameResponse.class);
    }

}
