package com.xiaohe.xhapiclientsdk.model.response;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class PoisonousChickenSoupResponse extends BaseResponse {
    private static final long serialVersionUID = -6467312483425078539L;
    private String text;
}
