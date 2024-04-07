package com.xiaohe.xhapibackend.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xiaohe.xhapibackend.common.BaseResponse;
import com.xiaohe.xhapibackend.common.ErrorCode;
import com.xiaohe.xhapibackend.common.ResultUtils;
import com.xiaohe.xhapibackend.exception.BusinessException;
import com.xiaohe.xhapibackend.exception.ThrowUtils;
import com.xiaohe.xhapibackend.model.entity.UserCheckIn;
import com.xiaohe.xhapibackend.service.UserCheckInService;
import com.xiaohe.xhapibackend.service.UserService;
import com.xiaohe.xhapicommon.model.vo.UserVO;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/userCheckIn")
public class UserCheckInController {

    @Resource
    private UserService userService;

    @Resource
    private UserCheckInService userCheckInService;

    @PostMapping("/daily")
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse<Boolean> dailyCheckIn(HttpServletRequest request) {
        UserVO loginUser = userService.getLoginUser(request);
        Long userId = loginUser.getId();
        QueryWrapper<UserCheckIn> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId);
        UserCheckIn userCheckIn = userCheckInService.getOne(queryWrapper);
        if (ObjectUtils.isNotEmpty(userCheckIn)) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR, "签到失败,今日已签到");
        }
        userCheckIn = new UserCheckIn();
        userCheckIn.setUserId(userId);
        userCheckIn.setAddPoints(10L);
        boolean dailyCheckInResult = userCheckInService.save(userCheckIn);
        boolean addPointsResult = userService.addPoints(userId, userCheckIn.getAddPoints());
        boolean result = dailyCheckInResult && addPointsResult;
        ThrowUtils.throwIf(!result, ErrorCode.OPERATION_ERROR, "调用失败");
        return ResultUtils.success(true);
    }
}
