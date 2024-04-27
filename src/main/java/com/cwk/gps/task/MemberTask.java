package com.cwk.gps.task;

import com.cwk.gps.mapper.ActivityMapper;
import com.cwk.gps.mapper.MemberMapper;
import com.cwk.pojo.entity.Activity;
import com.cwk.pojo.entity.Member;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 定时任务类，定时处理过期会员
 */
@Component
@Slf4j
public class MemberTask {

    @Autowired
    private MemberMapper memberMapper;

    /**
     * 每天检查是否有过期的会员
     */
    @Scheduled(cron = "0 0 0 * * ?")
    public void updateMemberTask() {

        List<Member> members = memberMapper.getAll();

        //判断会员是否过期
        if (members != null && !members.isEmpty()) {
            for (Member member: members) {
                if (member.getExpirationDate().isBefore(LocalDateTime.now())) {
                    log.info("定时处理过期会员：{}", member);
                    memberMapper.cancel(member.getId());
                }
            }
        }
    }
}

