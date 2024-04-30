package com.cwk.gps.mapper;

import com.cwk.enumeration.OperationType;
import com.cwk.gps.annotation.AutoFill;
import com.cwk.pojo.dto.PageQueryDTO;
import com.cwk.pojo.entity.AdminLog;
import com.cwk.pojo.entity.Comment;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface CommentMapper {

    @Select("select * from comment")
    Page<Comment> pageQuery(PageQueryDTO pageQueryDTO);

    @Delete("delete from comment where comment_id = #{id}")
    void delete(Long id);

    @Delete("delete from comment where post_id = #{postId}")
    void deleteByPostId(Long postId);

    @Select("select * from gps.comment where post_id = #{postId}")
    List<Comment> queryByPostID(Long postId);


    @AutoFill(value = OperationType.INSERT_USER)
    @Insert("insert into gps.comment(comment_id, post_id, user_id, content) " +
            "values " +
            "(#{commentId},#{postId},#{userId},#{content})")
    void insert(Comment comment);

    @Select("select user_id from gps.comment where comment_id = #{commentId}")
    Long queryByCommentId(Long commentId);

}
