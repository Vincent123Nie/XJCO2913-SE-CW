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
public class subscriptions implements Serializable {
    private Long id;

    private Long userId;

    //0-weekly 1-monthly 2-yearly
    private Integer subscriptionType;

    private LocalDateTime endTime;

    private LocalDateTime createTime;
}
