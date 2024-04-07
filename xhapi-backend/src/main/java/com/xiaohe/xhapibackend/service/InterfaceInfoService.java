package com.xiaohe.xhapibackend.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xiaohe.xhapibackend.model.dto.interfaceInfo.InterfaceInfoQueryRequest;
import com.xiaohe.xhapicommon.model.entity.InterfaceInfo;
import com.xiaohe.xhapicommon.model.vo.UserVO;

/**
* @author Lenovo
* @description 针对表【interface_info(接口信息)】的数据库操作Service
* @createDate 2023-12-11 12:12:55
*/
public interface InterfaceInfoService extends IService<InterfaceInfo> {

    /**
     * 校验
     * @param interfaceInfo 接口信息
     * @param add 是否为创建校验
     */
    void validInterfaceInfo(InterfaceInfo interfaceInfo, boolean add);

    /**
     * 获取查询条件
     * @param interfaceInfoQueryRequest
     * @return
     */
    QueryWrapper<InterfaceInfo> getQueryWrapper(InterfaceInfoQueryRequest interfaceInfoQueryRequest, UserVO user);

    /**
     * 更新接口总调用次数
     * @param interfaceInfoId
     * @return
     */
    boolean updateTotalInvokes(long interfaceInfoId);
}
