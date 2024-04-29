package com.cwk.pojo.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AdminLog implements Serializable {

    private Long id;

    private Long userId;

    private String method;

    private Long objectId;

    private String parameter;

    private LocalDateTime time;
}
