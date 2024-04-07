package com.xiaohe.xhapibackend.job;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xiaohe.xhapibackend.model.entity.UserCheckIn;
import com.xiaohe.xhapibackend.service.UserCheckInService;
import com.xiaohe.xhapibackend.utils.RedissonLockUtils;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 用户签到任务
 */
@Component
public class UserCheckInJob {

    @Resource
    private UserCheckInService userCheckInService;

    @Resource
    private RedissonLockUtils redissonLockUtils;

    /**
     * 每日签到（每天晚上12点批量清除签到列表）
     */
    @Scheduled(cron = "0 0 0 * * *")
    public void clearDailyCheckInList() {
        redissonLockUtils.redissonDistributedLocks("clearDailyCheckInList", () -> {
            // 每批删除的数据量
            int batchSize = 1000;
            // 是否还有数据需要删除
            boolean hasMoreData = true;

            while (hasMoreData) {
                // 分批查询数据
                QueryWrapper<UserCheckIn> queryWrapper = new QueryWrapper<>();
                queryWrapper.last("LIMIT " + batchSize);
                List<UserCheckIn> dataList = userCheckInService.list(queryWrapper);

                if (dataList.isEmpty()) {
                    // 没有数据了，退出循环
                    hasMoreData = false;
                } else {
                    // 批量删除数据
                    userCheckInService.removeByIds(dataList.stream().map(UserCheckIn::getId).collect(Collectors.toList()));
                }
            }
        });
    }
}
