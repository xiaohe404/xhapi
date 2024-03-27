package com.xiaohe.xhapicommon.service;

import com.xiaohe.xhapicommon.model.entity.User;

/**
 * 用户服务
 */
public interface InnerUserService {

    /**
     * 获取调用用户
     *
     * @param accessKey
     * @return
     */
    User getInvokeUser(String accessKey);

}
