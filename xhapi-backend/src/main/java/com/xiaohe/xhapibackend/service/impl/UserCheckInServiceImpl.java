package com.xiaohe.xhapibackend.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xiaohe.xhapibackend.model.entity.UserCheckIn;
import com.xiaohe.xhapibackend.service.UserCheckInService;
import com.xiaohe.xhapibackend.mapper.UserCheckInMapper;
import org.springframework.stereotype.Service;

/**
* @author Lenovo
* @description 针对表【user_check_in(用户签到)】的数据库操作Service实现
* @createDate 2024-03-26 15:59:49
*/
@Service
public class UserCheckInServiceImpl extends ServiceImpl<UserCheckInMapper, UserCheckIn>
    implements UserCheckInService{

}




