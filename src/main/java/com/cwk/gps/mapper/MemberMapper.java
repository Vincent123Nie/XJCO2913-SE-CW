package com.cwk.gps.mapper;

import com.cwk.pojo.dto.PageQueryDTO;
import com.cwk.pojo.entity.Member;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface MemberMapper {


    void save(Long userId,Integer type);

    @Select("select * from member where user_id = #{userId}")
    Member getByUserId(Long userId);

    void increase(Long userId, Integer type);

    void decrease(Long userId, Integer type);

    Page<Member> pageQuery(PageQueryDTO pageQueryDTO);

    @Delete("delete from member where id = #{id}")
    void cancel(Long id);

    @Select("select * from member")
    List<Member> getAll();

    @Select("select count(id) from member")
    Integer getCount();
}
