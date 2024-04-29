package com.cwk.gps.mapper;

import com.cwk.pojo.dto.EmployeePageQueryDTO;
import com.cwk.pojo.entity.Employee;
import com.cwk.enumeration.OperationType;
import com.cwk.gps.annotation.AutoFill;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface EmployeeMapper {

    @Select("select * from employee where username = #{username}")
    Employee getByUsername(String username);

    @AutoFill(value = OperationType.INSERT_ADMIN)
    void insert(Employee employee);

    /**
     *  分页查询
     * @param employeePageQueryDTO
     * @return
     */
    Page<Employee> pageQuery(EmployeePageQueryDTO employeePageQueryDTO);

    @AutoFill(value = OperationType.UPDATE_ADMIN)
    void update(Employee employee);

    @Select("select * from employee where id = #{id}")
    Employee getById(Long id);
}
