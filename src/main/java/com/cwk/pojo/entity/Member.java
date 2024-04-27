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
public class Member implements Serializable {

    public static final Integer STATUS_EXPIRED = 0;
    public static final Integer STATUS_UNEXPIRED = 1;


    private Long id;

    private Long userId;

    private LocalDateTime registrationDate;

    private LocalDateTime expirationDate;
}
