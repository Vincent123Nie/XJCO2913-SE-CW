package com.cwk.gps.task;

import com.cwk.gps.mapper.ActivityMapper;
import com.cwk.pojo.entity.Activity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 定时任务类，定时处理活动状态
 */
@Component
@Slf4j
public class ActivityTask {

    @Autowired
    private ActivityMapper activityMapper;

    /**
     * 每小时更新活动状态
     */
    @Scheduled(cron = "0 0 * * * ?")
    public void updateActivityStatusTask() {

        //查询未开始和进行中的活动
        List<Activity> activities = activityMapper.getUnfinishedActivities();
        log.info("定时处理活动状态：{}", activities);

        //判断活动是否到达开始时间或者结束时间
        if (activities != null && !activities.isEmpty()) {
            for (Activity activity: activities) {
                if (activity.getStatus() == 0 && activity.getStartTime().isBefore(LocalDateTime.now())) {
                    //更改活动状态为进行中
                    activity.setStatus(Activity.ONGOING);
                    activityMapper.update(activity);
                }else if (activity.getStatus() == 1 && activity.getEndTime().isBefore(LocalDateTime.now())) {
                    //更改活动状态为已结束
                    activity.setStatus(Activity.FINISHED);
                    activityMapper.update(activity);
                }
            }
        }

    }
}

