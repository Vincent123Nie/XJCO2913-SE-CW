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
public class FriendShip implements Serializable {
    private Long id;

    private Long userId;

    private Long friendId;

    private LocalDateTime createTime;
}
