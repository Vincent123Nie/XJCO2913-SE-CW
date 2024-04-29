package com.cwk.gps.aspect;

import com.cwk.context.BaseContext;
import com.cwk.gps.annotation.Log;
import com.cwk.gps.constant.AutoFillConstant;
import com.cwk.gps.mapper.LogMapper;
import com.cwk.pojo.entity.AdminLog;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.util.Arrays;

@Slf4j
@Component
@Aspect
public class LogAspect {

    private static final String GET_ID = "getId";
    private static final String GET_POST_ID = "getPostId";
    private static final String GET_COMMENT_ID = "getCommentId";

    @Autowired
    private LogMapper logMapper;

    /**
     * 切入点
     */
    @Pointcut("execution(* com.cwk.gps.controller.admin.*.*(..)) && @annotation(com.cwk.gps.annotation.Log)")
    public void logPointCut(){}

    @After("logPointCut()")
    public void adminLog(JoinPoint joinPoint) {
        log.info("记录管理员日志：{}", BaseContext.getCurrentId());

        //获取当前被拦截的方法上的操作名称
        MethodSignature signature = (MethodSignature)joinPoint.getSignature();//方法签名对象
        Log log = signature.getMethod().getAnnotation(Log.class);//获得方法上的注解对象
        String method = log.method();//操作名称

        //获取操作参数
        Object[] arg = joinPoint.getArgs();
        String parameter = Arrays.toString(arg);

        Long objectId = null;
        for (Object object: arg) {
            if (object instanceof Long) {
                objectId = (Long) object;
            }
        }
        if (objectId == null) {
            Object entity = arg[0];
            try {
                Method getId;
                if (method.equals("Delete post")) {
                    getId = entity.getClass().getDeclaredMethod(GET_POST_ID);
                } else if (method.equals("Delete comment")) {
                    getId = entity.getClass().getDeclaredMethod(GET_COMMENT_ID);
                } else {
                    getId = entity.getClass().getDeclaredMethod(GET_ID);
                }
                objectId = (Long) getId.invoke(entity);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        AdminLog adminLog = AdminLog.builder()
                .userId(BaseContext.getCurrentId())
                .method(method)
                .objectId(objectId)
                .parameter(parameter)
                .build();

        logMapper.save(adminLog);
    }
}
