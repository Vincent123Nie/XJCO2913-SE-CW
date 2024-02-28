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
public class Trips implements Serializable {

    private Long id;

    private Long userId;

    private String name;

    private String description;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    private LocalDateTime createTime;
}
