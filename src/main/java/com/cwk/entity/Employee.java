package com.cwk.entity;

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

    //姓名
    private String username;

    private String name;

    //邮箱
    private String email;

    //密码
    private String password;

    //手机号
    private String phone;

    private Integer status;

    //注册时间
    private LocalDateTime createTime;

    //更新时间
    private LocalDateTime updateTime;

}
