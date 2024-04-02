package com.xiaohe.xhapibackend.model.dto.interfaceInfo;

import lombok.Data;

/**
 * 响应参数字段
 */
@Data
public class ResponseParamsField {

    private String id;

    private String fieldName;

    private String type;

    private String desc;
}
