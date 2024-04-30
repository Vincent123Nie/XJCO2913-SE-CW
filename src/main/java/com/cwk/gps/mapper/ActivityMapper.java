package com.cwk.gps.mapper;

import com.cwk.enumeration.OperationType;
import com.cwk.gps.annotation.AutoFill;
import com.cwk.pojo.dto.ActivityPageQueryDTO;
import com.cwk.pojo.entity.Activity;
import com.cwk.pojo.entity.Employee;
import com.cwk.pojo.vo.AllActivityVo;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ActivityMapper {

    @AutoFill(OperationType.INSERT_ADMIN)
    void insert(Activity activity);

    /**
     * 获取未完成的活动
     */
    @Select("select * from activity where status = 0 or status = 1")
    List<Activity> getUnfinishedActivities();

    @AutoFill(OperationType.UPDATE_ADMIN)
    void update(Activity activity);

    Page<Activity> pageQuery(ActivityPageQueryDTO activityPageQueryDTO);

    @Delete("delete from activity where id = #{id}")
    void deleteById(Long id);

    @Select("select * from activity where id = #{id}")
    Activity getById(Long id);

    @Select("select * from activity where status = 0 or status = 1")
    List<AllActivityVo> getActivities();

}
