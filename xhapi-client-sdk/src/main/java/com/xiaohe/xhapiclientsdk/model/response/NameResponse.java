package com.xiaohe.xhapiclientsdk.model.response;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class NameResponse extends BaseResponse {
    private static final long serialVersionUID = 3815888526334267152L;
    private String name;
}
