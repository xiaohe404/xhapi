package com.xiaohe.xhapibackend.model.vo;

import com.xiaohe.xhapicommon.model.entity.InterfaceInfo;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 接口信息封装视图
 *
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class InterfaceInfoVO extends InterfaceInfo {

    /**
     * 调用次数
     */
    private Long totalInvokes;

    private static final long serialVersionUID = 1L;
}

