package com.xiaohe.xhapibackend.common;

import lombok.Data;

import java.io.Serializable;

/**
 * 通用请求
 */
@Data
public class IdRequest implements Serializable {

    /**
     * id
     */
    private Long id;

    private static final long serialVersionUID = 1L;
}