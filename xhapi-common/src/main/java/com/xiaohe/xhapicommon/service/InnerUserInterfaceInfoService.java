package com.xiaohe.xhapicommon.service;

/**
* 用户调用接口服务
*/
public interface InnerUserInterfaceInfoService {

    /**
     * 更新接口调用次数
     * @param interfaceInfoId
     * @param userId
     * @return
     */
    boolean invokeCount(long interfaceInfoId, long userId, long consumePoints);

}
