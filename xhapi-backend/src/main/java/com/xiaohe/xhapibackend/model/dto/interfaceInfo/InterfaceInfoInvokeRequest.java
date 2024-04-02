package com.xiaohe.xhapibackend.model.dto.interfaceInfo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 调试请求
 *
 */
@Data
public class InterfaceInfoInvokeRequest implements Serializable {

    /**
     * 主键
     */
    private Long id;

    /**
     * 请求参数
     */
    private List<Field> requestParams;

    @Data
    public static class Field {
        private String fieldName;
        private String value;
    }


    private static final long serialVersionUID = 1L;
}