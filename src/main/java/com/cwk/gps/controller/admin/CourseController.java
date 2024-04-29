package com.cwk.gps.controller.admin;

import com.cwk.gps.annotation.Log;
import com.cwk.gps.result.PageResult;
import com.cwk.gps.result.Result;
import com.cwk.gps.service.CourseService;
import com.cwk.pojo.dto.CoursePageQueryDTO;
import com.cwk.pojo.entity.Course;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/course")
@CrossOrigin("http://localhost:8081")
@Slf4j
public class CourseController {

    @Autowired
    private CourseService courseService;

    @PostMapping
    @Log(method = "Add course")
    public Result save(@RequestBody Course course) {
        log.info("新增课程：{}",course);
        courseService.save(course);
        return Result.success();
    }

    @GetMapping("/page")
    public Result<PageResult> page(CoursePageQueryDTO coursePageQueryDTO) {
        log.info("课程分页查询：{}", coursePageQueryDTO);
        PageResult pageResult = courseService.pageQuery(coursePageQueryDTO);
        return Result.success(pageResult);
    }

    @DeleteMapping
    @Log(method = "Delete course")
    public Result delete(Long id){
        log.info("删除课程：{}", id);
        courseService.delete(id);
        return Result.success();
    }

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

    @PutMapping
    @Log(method = "Modify course")
    public Result update(@RequestBody Course course){
        log.info("编辑课程信息：{}", course);
        courseService.update(course);
        return Result.success();
    }
}
