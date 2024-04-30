package com.cwk.gps.mapper;

import com.cwk.enumeration.OperationType;
import com.cwk.gps.annotation.AutoFill;
import com.cwk.pojo.dto.PageQueryDTO;
import com.cwk.pojo.entity.AdminLog;
import com.cwk.pojo.entity.Post;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface PostMapper {
    @AutoFill(value = OperationType.INSERT_USER)
    void insert(Post post);

    @Select("select * from gps.post")
    List<Post> query();

    @Select("select * from gps.post where post_id = #{postId}")
    Post queryByPostId(Long postId);

    @Select("select * from post")
    Page<Post> pageQuery(PageQueryDTO pageQueryDTO);

    @Delete("delete from post where post_id = #{id}")
    void delete(Long id);

    @Select("select * from gps.post where userId = #{id}")
    List<Post> queryByUserId(Long id);

    @Select("select count(*) from post where userId = #{id}")
    int queryPostsCount(Long id);
}
