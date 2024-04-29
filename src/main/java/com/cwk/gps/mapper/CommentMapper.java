package com.cwk.gps.mapper;

import com.cwk.pojo.dto.PageQueryDTO;
import com.cwk.pojo.entity.AdminLog;
import com.cwk.pojo.entity.Comment;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface CommentMapper {

    @Select("select * from comment")
    Page<Comment> pageQuery(PageQueryDTO pageQueryDTO);

    @Delete("delete from comment where comment_id = #{id}")
    void delete(Long id);

    @Delete("delete from comment where post_id = #{postId}")
    void deleteByPostId(Long postId);
}
