package com.cwk.gps.service.impl;

import com.cwk.gps.mapper.ActivityMapper;
import com.cwk.gps.mapper.ActivityUserMapper;
import com.cwk.gps.result.PageResult;
import com.cwk.gps.service.ActivityService;
import com.cwk.pojo.dto.ActivityPageQueryDTO;
import com.cwk.pojo.entity.Activity;
import com.cwk.pojo.entity.Employee;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ActivityServiceImpl implements ActivityService {

    @Autowired
    private ActivityMapper activityMapper;

    @Autowired
    private ActivityUserMapper activityUserMapper;

    @Override
    public void save(Activity activity) {
        //默认活动未开始
        activity.setStatus(0);

        activityMapper.insert(activity);
    }

    @Override
    public PageResult pageQuery(ActivityPageQueryDTO activityPageQueryDTO) {
        //开始分页查询
        PageHelper.startPage(activityPageQueryDTO.getPage(),activityPageQueryDTO.getPageSize());

        Page<Activity> page = activityMapper.pageQuery(activityPageQueryDTO);
        long total = page.getTotal();
        List<Activity> records = page.getResult();
        return new PageResult(total, records);
    }

    @Override
    public void delete(Long id) {
        //删除用户活动相关的数据
        activityUserMapper.deleteByActivityId(id);

        activityMapper.deleteById(id);
    }

    @Override
    public Activity getById(Long id) {
        return activityMapper.getById(id);
    }

    @Override
    public void update(Activity activity) {
        activityMapper.update(activity);
    }

}
