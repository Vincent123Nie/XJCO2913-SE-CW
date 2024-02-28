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
public class FriendShips implements Serializable {
    private Long userId1;

    private Long userId2;

    private Integer status;

    private Long actionUserId;

    private LocalDateTime createTime;
}
