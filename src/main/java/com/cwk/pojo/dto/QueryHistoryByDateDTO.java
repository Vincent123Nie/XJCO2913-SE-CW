package com.cwk.pojo.dto;

import lombok.Data;

@Data
public class QueryHistoryByDateDTO {
    private Long userid;
    private Long startDate;
    private Long endDate;
}
