package com.cwk.gps.mapper;

import com.cwk.dto.EmployeePageQueryDTO;
import com.cwk.entity.Employee;
import com.cwk.enumeration.OperationType;
import com.cwk.gps.annotation.AutoFill;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface EmployeeMapper {

    @Select("select * from gps.employee where username = #{username};")
    Employee getByUsername(String username);

    @Insert("insert into gps.employee (name, username, email, password, phone, create_time, update_time, status)" +
            "values" +
            "(#{name},#{username},#{email},#{password},#{phone},#{createTime},#{updateTime},#{status})")
    void insert(Employee employee);

    /**
     *  分页查询
     * @param employeePageQueryDTO
     * @return
     */
    Page<Employee> pageQuery(EmployeePageQueryDTO employeePageQueryDTO);

    @AutoFill(value = OperationType.UPDATE)
    void update(Employee employee);

    @Select("select * from gps.employee where id = #{id}")
    Employee getById(Long id);
}
