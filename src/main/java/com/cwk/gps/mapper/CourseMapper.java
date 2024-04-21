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

@Mapper
public interface CourseMapper {

    @Insert("insert into course (name, picture, resource, type, duration, calorie, description, create_time, update_time, create_user, update_user)" +
            "values" +
            "(#{name}, #{picture}, #{resource}, #{type}, #{duration}, #{calorie}, #{description}, #{createTime}, #{updateTime}, #{createUser}, #{updateUser})")
    @AutoFill(OperationType.INSERT_ADMIN)
    void save(Course course);

    Page<Course> pageQuery(CoursePageQueryDTO coursePageQueryDTO);

    @Delete("delete from course where id = #{id}")
    void deleteById(Long id);

    @Select("select * from course where id = #{id}")
    Course getById(Long id);

    @AutoFill(OperationType.UPDATE_ADMIN)
    void update(Course course);
}
