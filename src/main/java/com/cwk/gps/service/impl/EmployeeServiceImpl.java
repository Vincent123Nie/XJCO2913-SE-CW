package com.cwk.gps.service.impl;

import com.cwk.gps.annotation.LevelCheck;
import com.cwk.pojo.dto.EmployeePageQueryDTO;
import com.cwk.pojo.entity.Employee;
import com.cwk.exception.AccountLockedException;
import com.cwk.exception.AccountNotFoundException;
import com.cwk.exception.PasswordErrorException;
import com.cwk.gps.constant.MessageConstant;
import com.cwk.gps.constant.StatusConstant;
import com.cwk.gps.mapper.EmployeeMapper;
import com.cwk.gps.result.PageResult;
import com.cwk.gps.service.EmployeeService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeMapper employeeMapper;

    @Override
    public Employee login(Employee employee) {
        String username = employee.getUsername();
        String password = employee.getPassword();

        //1、根据用户名查询数据库中的数据
        Employee employee1 = employeeMapper.getByUsername(username);

        //2、处理各种异常情况（用户名不存在、密码不对、账号被锁定）
        if (employee1 == null) {
            //账号不存在
            throw new AccountNotFoundException(MessageConstant.ACCOUNT_NOT_FOUND);
        }

        //密码比对
        password = DigestUtils.md5DigestAsHex(password.getBytes());
        if (!password.equals(employee1.getPassword())) {
            //密码错误
            throw new PasswordErrorException(MessageConstant.PASSWORD_ERROR);
        }

        if (employee1.getStatus() == StatusConstant.DISABLE) {
            //账号被锁定
            throw new AccountLockedException(MessageConstant.ACCOUNT_LOCKED);
        }

        //3、返回实体对象
        return employee1;
    }

    @LevelCheck
    public void save(Employee employee) {

        //设置账号的状态，0表示锁定，1表示启用
        employee.setStatus(StatusConstant.ENABLE);
        //设置密码md5
        employee.setPassword(DigestUtils.md5DigestAsHex(employee.getPassword().getBytes()));

        employeeMapper.insert(employee);
    }

    /**
     *  分页查询
     * @param employeePageQueryDTO
     * @return
     */
    public PageResult pageQuery(EmployeePageQueryDTO employeePageQueryDTO) {
        //开始分页查询
        PageHelper.startPage(employeePageQueryDTO.getPage(),employeePageQueryDTO.getPageSize());

        Page<Employee> page = employeeMapper.pageQuery(employeePageQueryDTO);
        long total = page.getTotal();
        List<Employee> records = page.getResult();
        return new PageResult(total, records);
    }

    /**
     * 启用禁用员工账号
     * @param status
     * @param id
     */
    @LevelCheck
    public void startOrStop(Integer status, Long id) {
        Employee employee = Employee.builder()
                .status(status)
                .id(id)
                .build();
        employeeMapper.update(employee);
    }

    /**
     * 根据Id查询员工信息
     * @param id
     * @return
     */
    @Override
    public Employee getById(Long id) {
        Employee employee = employeeMapper.getById(id);
        employee.setPassword("****");
        return employee;
    }

    /**
     * 编辑员工信息
     * @param employeeDTO
     */
    @LevelCheck
    public void update(Employee employeeDTO) {
        employeeMapper.update(employeeDTO);
    }
}
