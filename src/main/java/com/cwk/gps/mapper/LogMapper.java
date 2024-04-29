package com.cwk.gps.mapper;

import com.cwk.enumeration.OperationType;
import com.cwk.gps.annotation.AutoFill;
import com.cwk.pojo.dto.CoursePageQueryDTO;
import com.cwk.pojo.dto.PageQueryDTO;
import com.cwk.pojo.entity.AdminLog;
import com.cwk.pojo.entity.Course;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface LogMapper {

    @Insert("insert into admin_log (user_id, method, object_id, parameter) VALUES (#{userId}, #{method}, #{objectId}, #{parameter})")
    void save(AdminLog adminLog);

    @Select("select * from admin_log")
    Page<AdminLog> pageQuery(PageQueryDTO pageQueryDTO);
}
