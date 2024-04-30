package com.cwk.gps.controller.user;

import com.cwk.gps.annotation.Log;
import com.cwk.gps.result.PageResult;
import com.cwk.gps.result.Result;
import com.cwk.gps.service.CourseService;
import com.cwk.pojo.dto.CoursePageQueryDTO;
import com.cwk.pojo.entity.Course;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("userCourseController")
@RequestMapping("/user/course")
@Slf4j
public class CourseController {

    @Autowired
    private CourseService courseService;



    /**
     * 根据Id查询活课程信息
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public Result<Course> getById(@PathVariable Long id){
             Course course = courseService.getById(id);
        return Result.success(course);
    }

    /**
     * 根据分类返回课程列表
     * @param str
     * @return
     */
    @GetMapping("/category")
    public Result getByCategory(@RequestParam String str){
        log.info("{}",str);
        List<Course> list = courseService.getByCategory(str);
        return Result.success(list);
    }

}
