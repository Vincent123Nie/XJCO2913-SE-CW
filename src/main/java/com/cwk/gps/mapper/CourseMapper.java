package com.cwk.gps.mapper;

import com.cwk.enumeration.OperationType;
import com.cwk.gps.annotation.AutoFill;
import com.cwk.pojo.dto.CoursePageQueryDTO;
import com.cwk.pojo.entity.Course;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface CourseMapper {

    @AutoFill(OperationType.INSERT_ADMIN)
    void save(Course course);

    Page<Course> pageQuery(CoursePageQueryDTO coursePageQueryDTO);

    @Delete("delete from course where id = #{id}")
    void deleteById(Long id);

    @Select("select * from course where id = #{id}")
    Course getById(Long id);

    @AutoFill(OperationType.UPDATE_ADMIN)
    void update(Course course);

    @Select("select * from course where type = #{str}")
    List<Course> getByCategory(String str);
}
