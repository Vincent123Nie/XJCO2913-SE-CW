package com.cwk.pojo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserretriveDTO {
    //邮箱
    private String email;
    private String code;
    private String password;
    private String confirmPassword;
}
