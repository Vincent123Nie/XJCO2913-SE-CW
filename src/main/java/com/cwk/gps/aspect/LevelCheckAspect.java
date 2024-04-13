package com.cwk.gps.aspect;

import com.cwk.context.BaseContext;
import com.cwk.exception.PermissionException;
import com.cwk.gps.constant.MessageConstant;
import com.cwk.gps.mapper.EmployeeMapper;
import com.cwk.pojo.entity.Employee;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 自定义切面，实现员工权限检查逻辑
 */
@Slf4j
@Component
@Aspect
public class LevelCheckAspect {

    @Autowired
    private EmployeeMapper employeeMapper;

    @Pointcut("execution(* com.cwk.gps.service.impl.EmployeeServiceImpl.*(..)) && @annotation(com.cwk.gps.annotation.LevelCheck)")
    public void levelCheckPointCut(){}

    @Before("levelCheckPointCut()")
    public void levelCheck(JoinPoint joinPoint) {
        log.info("开始检查员工权限...");
        //获取登录员工的等级
        Integer level1 = employeeMapper.getById(BaseContext.getCurrentId()).getLevel();
        Integer level2 = 0;

        //获取操作对象的等级
        Object[] args = joinPoint.getArgs();
        if (args.length == 1) {
            Employee employee = (Employee) args[0];
            level2 = employee.getLevel();
        }else if (args.length == 2) {
            Employee employee = employeeMapper.getById((Long) args[1]);
            level2 = employee.getLevel();
        }

        //判断登录员工的等级是否高于操作对象
        if (level1 >= level2) {
            throw new PermissionException(MessageConstant.PERMISSION_ERROR);
        }
    }
}
