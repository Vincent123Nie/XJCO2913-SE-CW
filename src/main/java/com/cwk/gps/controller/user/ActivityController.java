package com.cwk.gps.controller.user;

import com.cwk.gps.result.Result;
import com.cwk.gps.service.ActivityService;
import com.cwk.pojo.entity.Activity;
import com.cwk.pojo.vo.AllActivityVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("userActivityController")
@RequestMapping("/user/activity")
@CrossOrigin("http://localhost:8081")
@Slf4j
public class ActivityController {

    @Autowired
    private ActivityService activityService;

    /**
     * 查询全部活动
     * @return
     */
    @GetMapping("/activities")
    public Result<List<AllActivityVo>> queryActivities() {
        List<AllActivityVo> list = activityService.getActivities();
        return Result.success(list);
    }

    /**
     * 根据Id查询活动信息
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public Result<Activity> getById(@PathVariable Long id){
        Activity activity = activityService.getById(id);
        return Result.success(activity);
    }

    /**
     * 报名活动
     * @param id
     * @return
     */
    @PostMapping("/sign/{id}")
    public Result signUp(@PathVariable Long id){
        activityService.signUp(id);
        return Result.success("报名成功");
    }
}
