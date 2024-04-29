package com.cwk.gps.mapper;

import com.cwk.pojo.dto.PageQueryDTO;
import com.cwk.pojo.entity.AdminLog;
import com.cwk.pojo.entity.Post;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface PostMapper {

    @Select("select * from post")
    Page<Post> pageQuery(PageQueryDTO pageQueryDTO);

    @Delete("delete from post where post_id = #{id}")
    void delete(Long id);
}
