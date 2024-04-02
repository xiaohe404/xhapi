package com.xiaohe.xhapibackend.model.dto.interfaceInfo;

import com.xiaohe.xhapibackend.common.PageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.List;

/**
 * 查询请求
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class InterfaceInfoQueryRequest extends PageRequest implements Serializable {

    private Long id;

    /**
     * 接口名称
     */
    private String name;

    /**
     * 接口描述
     */
    private String description;

    /**
     * 接口地址
     */
    private String url;

    /**
     * 请求类型
     */
    private String method;

    /**
     * 接口请求参数
     */
    private List<RequestParamsField> requestParams;

    /**
     * 接口响应参数
     */
    private List<ResponseParamsField> responseParams;

    /**
     * 返回格式(JSON等等)
     */
    private String resultFormat;

    /**
     * 请求示例
     */
    private String requestExample;

    /**
     * 状态(0 关闭 1 开启)
     */
    private Integer status;

    /**
     * 消耗积分
     */
    private Long consumePoints;

    /**
     * 创建人
     */
    private Long userId;

    private static final long serialVersionUID = 1L;
}