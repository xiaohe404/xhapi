package com.xiaohe.xhapibackend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaohe.xhapibackend.common.ErrorCode;
import com.xiaohe.xhapibackend.exception.BusinessException;
import com.xiaohe.xhapibackend.exception.ThrowUtils;
import com.xiaohe.xhapibackend.mapper.UserInterfaceInfoMapper;
import com.xiaohe.xhapibackend.service.InterfaceInfoService;
import com.xiaohe.xhapibackend.service.UserInterfaceInfoService;
import com.xiaohe.xhapibackend.service.UserService;
import com.xiaohe.xhapicommon.model.entity.UserInterfaceInfo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
* @author Lenovo
* @description 针对表【user_userInterface_info(用户调用接口)】的数据库操作Service实现
* @createDate 2023-12-14 14:56:55
*/
@Service
public class UserInterfaceInfoServiceImpl extends ServiceImpl<UserInterfaceInfoMapper, UserInterfaceInfo>
    implements UserInterfaceInfoService {

    @Resource
    private InterfaceInfoService interfaceInfoService;

    @Resource
    private UserService userService;

    @Override
    public void validUserInterfaceInfo(UserInterfaceInfo userInterfaceInfo, boolean add) {
        if (userInterfaceInfo == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        // 创建时，参数不能为空
        if (add) {
            if (userInterfaceInfo.getUserId() <= 0 || userInterfaceInfo.getInterfaceInfoId() <= 0) {
                throw new BusinessException(ErrorCode.PARAMS_ERROR, "用户或接口不存在");
            }
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean invokeCount(long interfaceInfoId, long userId, long consumePoints) {
        // 查询是否存在记录
        QueryWrapper<UserInterfaceInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("interface_info_id", interfaceInfoId);
        queryWrapper.eq("user_id", userId);
        UserInterfaceInfo userInterfaceInfo = this.getOne(queryWrapper);
        boolean result;
        // 不存在就创建一条记录
        if (userInterfaceInfo == null) {
            userInterfaceInfo = new UserInterfaceInfo();
            userInterfaceInfo.setInterfaceInfoId(interfaceInfoId);
            userInterfaceInfo.setUserId(userId);
            userInterfaceInfo.setTotalInvokes(1L);
            result = this.save(userInterfaceInfo);
        } else {
            UpdateWrapper<UserInterfaceInfo> updateWrapper = new UpdateWrapper<>();
            updateWrapper.eq("interface_info_id", interfaceInfoId);
            updateWrapper.eq("user_id", userId);
            updateWrapper.setSql("total_invokes = total_invokes + 1");
            result = this.update(updateWrapper);
        }
        // 更新接口总调用次数
        boolean updateInterfaceInfo = interfaceInfoService.updateTotalInvokes(interfaceInfoId);
        // 更新用户积分
        boolean updateUser = userService.updatePoints(userId, consumePoints);
        result = result && updateInterfaceInfo && updateUser;
        ThrowUtils.throwIf(!result, ErrorCode.OPERATION_ERROR, "调用失败");
        return true;
    }
}




