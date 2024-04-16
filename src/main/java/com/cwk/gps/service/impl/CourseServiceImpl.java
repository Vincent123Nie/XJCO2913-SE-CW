package com.cwk.gps.service.impl;

import com.cwk.gps.mapper.CourseMapper;
import com.cwk.gps.result.PageResult;
import com.cwk.gps.service.CourseService;
import com.cwk.pojo.dto.CoursePageQueryDTO;
import com.cwk.pojo.entity.Course;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseServiceImpl implements CourseService {

    @Autowired
    private CourseMapper courseMapper;

    @Override
    public void save(Course course) {
        courseMapper.save(course);
    }

    @Override
    public PageResult pageQuery(CoursePageQueryDTO coursePageQueryDTO) {
        //开始分页查询
        PageHelper.startPage(coursePageQueryDTO.getPage(),coursePageQueryDTO.getPageSize());

        Page<Course> page = courseMapper.pageQuery(coursePageQueryDTO);
        long total = page.getTotal();
        List<Course> records = page.getResult();
        return new PageResult(total, records);
    }

    @Override
    public void delete(Long id) {
        courseMapper.deleteById(id);
    }

    @Override
    public Course getById(Long id) {
        return courseMapper.getById(id);
    }

    @Override
    public void update(Course course) {
        courseMapper.update(course);
    }
}
