package com.cwk.gps.service;

import com.cwk.gps.result.PageResult;
import com.cwk.pojo.dto.CoursePageQueryDTO;
import com.cwk.pojo.entity.Course;

public interface CourseService {

    void save(Course course);

    PageResult pageQuery(CoursePageQueryDTO coursePageQueryDTO);

    void delete(Long id);

    Course getById(Long id);

    void update(Course course);
}
