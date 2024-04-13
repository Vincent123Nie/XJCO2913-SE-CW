package com.cwk.pojo.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Employee implements Serializable {
    private Long id;

    //用户名
    private String username;

    //名字
    private String name;

    //密码
    private String password;

    //手机号
    private String phone;

    //员工权限
    private Integer level;

    //状态
    private Integer status;

    //注册时间
    private LocalDateTime createTime;

    //更新时间
    private LocalDateTime updateTime;

    //创建者
    private Long createUser;

    //更新者
    private Long updateUser;

}
