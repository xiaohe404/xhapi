package com.xiaohe.xhapiclientsdk.model.params;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 随机情话请求参数
 */
@Data
@Accessors(chain = true)
public class LoveWordsParams implements Serializable {
    private static final long serialVersionUID = 3815188540434269370L;
}
