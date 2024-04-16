package com.cwk.gps.controller.admin;

import com.cwk.gps.result.PageResult;
import com.cwk.gps.result.Result;
import com.cwk.gps.service.ActivityService;
import com.cwk.pojo.dto.ActivityPageQueryDTO;
import com.cwk.pojo.entity.Activity;
import com.cwk.pojo.entity.Employee;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/activity")
@CrossOrigin("http://localhost:8081")
@Slf4j
public class ActivityController {

    @Autowired
    private ActivityService activityService;

    @PostMapping
    public Result save(@RequestBody Activity activity){
        log.info("添加活动：{}",activity);
        activityService.save(activity);
        return Result.success();
    }

    @GetMapping("/page")
    public Result<PageResult> page(ActivityPageQueryDTO activityPageQueryDTO) {
        log.info("活动分页查询：{}", activityPageQueryDTO);
        PageResult pageResult = activityService.pageQuery(activityPageQueryDTO);
        return Result.success(pageResult);
    }

    @DeleteMapping
    public Result delete(Long id){
        log.info("删除活动：{}", id);
        activityService.delete(id);
        return Result.success();
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

    @PutMapping
    public Result update(@RequestBody Activity activity){
        log.info("编辑活动信息：{}", activity);
        activityService.update(activity);
        return Result.success();
    }
}
