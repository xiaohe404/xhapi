package com.xiaohe.xhapicommon.service;

import com.xiaohe.xhapicommon.model.entity.InterfaceInfo;

/**
* 接口服务
*/
public interface InnerInterfaceInfoService {

    /**
     * 从数据库中查询模拟接口是否存在（请求路径、请求方法、请求参数）
     * @param url
     * @param method
     * @return
     */
    InterfaceInfo getInterfaceInfo(String url, String method);

}
