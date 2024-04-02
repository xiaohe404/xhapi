package com.xiaohe.xhapibackend.model.dto.interfaceInfo;

import lombok.Data;

/**
 * 请求参数字段
 */
@Data
public class RequestParamsField {

    private String id;

    private String fieldName;

    private String type;

    private String desc;

    private String required;
}
