package com.xiaohe.xhapibackend.service.impl.inner;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.xiaohe.xhapibackend.common.ErrorCode;
import com.xiaohe.xhapibackend.exception.BusinessException;
import com.xiaohe.xhapibackend.mapper.UserMapper;
import com.xiaohe.xhapicommon.model.entity.User;
import com.xiaohe.xhapicommon.service.InnerUserService;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.DubboService;

import javax.annotation.Resource;

/**
 * 内部用户服务实现类
 */
@DubboService
public class InnerUserServiceImpl implements InnerUserService {

    @Resource
    private UserMapper userMapper;

    /**
     * 获取调用用户
     * @param accessKey
     * @return
     */
    @Override
    public User getInvokeUser(String accessKey) {
        if (StringUtils.isBlank(accessKey)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(User::getAccessKey, accessKey);
        return userMapper.selectOne(lambdaQueryWrapper);
    }
}
