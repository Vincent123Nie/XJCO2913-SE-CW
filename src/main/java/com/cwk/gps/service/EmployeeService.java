package com.cwk.gps.service;

import com.cwk.pojo.dto.EmployeePageQueryDTO;
import com.cwk.pojo.entity.Employee;
import com.cwk.gps.result.PageResult;

public interface EmployeeService {
    Employee login(Employee employee);

    void save(Employee employee);

    /**
     *  分页查询
     * @param employeePageQueryDTO
     * @return
     */
    PageResult pageQuery(EmployeePageQueryDTO employeePageQueryDTO);

    /**
     * 启用禁用员工账号
     * @param status
     * @param id
     */
    void startOrStop(Integer status, Long id);

    /**
     * 根据Id查询员工信息
     * @param id
     * @return
     */
    Employee getById(Long id);

    /**
     * 编辑员工信息
     * @param employeeDTO
     * @return
     */
    void update(Employee employeeDTO);
}
