package com.cwk.gps.service;

import com.cwk.gps.result.PageResult;
import com.cwk.pojo.dto.ActivityPageQueryDTO;
import com.cwk.pojo.entity.Activity;
import com.cwk.pojo.vo.AllActivityVo;

import java.util.List;

public interface ActivityService {

    void save(Activity activity);

    PageResult pageQuery(ActivityPageQueryDTO activityPageQueryDTO);

    void delete(Long id);

    Activity getById(Long id);

    void update(Activity activity);

    List<AllActivityVo> getActivities();

    void signUp(Long id);
}
