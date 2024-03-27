package com.xiaohe.xhapibackend.service.impl.inner;

import com.xiaohe.xhapibackend.service.UserInterfaceInfoService;
import com.xiaohe.xhapicommon.service.InnerUserInterfaceInfoService;
import org.apache.dubbo.config.annotation.DubboService;

import javax.annotation.Resource;

/**
 * 内部用户调用接口服务实现类
 */
@DubboService
public class InnerUserInterfaceInfoServiceImpl implements InnerUserInterfaceInfoService {

    @Resource
    private UserInterfaceInfoService userInterfaceInfoService;

    /**
     * 更新接口调用次数
     * @param interfaceInfoId
     * @param userId
     * @return
     */
    @Override
    public boolean invokeCount(long interfaceInfoId, long userId, long consumePoints) {
        return userInterfaceInfoService.invokeCount(interfaceInfoId, userId, consumePoints);
    }
}
