package com.cwk.pojo.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FunsVo implements Serializable {
    private Long funs_id;
    private String fansName;
    private String avatar;
}
