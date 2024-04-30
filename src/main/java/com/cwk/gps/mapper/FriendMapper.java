package com.cwk.gps.mapper;

import com.cwk.pojo.entity.FriendShip;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Service;

import java.util.List;

@Mapper
public interface FriendMapper {
    void insert(FriendShip friendShip);

    @Delete("delete from gps.friendship where friend_id = #{friendId} and user_id = #{currentId}")
    void delete(Long friendId, Long currentId);

    @Select("select gps.friendship.user_id from friendship where friend_id = #{currentId}")
    List<Long> queryfuns(Long currentId);

    @Select("select gps.friendship.friend_id from friendship where user_id = #{currentId}")
    List<Long> following(Long currentId);

    @Select("select count(*) from friendship where user_id = #{id}")
    int queryfollowingcount(Long id);

    @Select("select count(*) from friendship where friend_id = #{id}")
    int queryfunscount(Long id);
}
