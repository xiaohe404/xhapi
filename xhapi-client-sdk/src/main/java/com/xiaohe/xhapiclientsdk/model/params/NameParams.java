package com.xiaohe.xhapiclientsdk.model.params;

import lombok.Data;

import java.io.Serializable;

/**
 * 获取名称请求参数
 */
@Data
public class NameParams implements Serializable {
    private static final long serialVersionUID = 3815188540434269370L;
    private String name;
}
