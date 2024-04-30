package com.cwk.pojo.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AllActivityVo {

    private Long id;

    private String name;

    private String picture;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

}
