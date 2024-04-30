package com.cwk.gps.mapper;

import com.cwk.pojo.dto.RevenuePageQueryDTO;
import com.cwk.pojo.entity.Revenue;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.time.LocalDateTime;

@Mapper
public interface RevenueMapper {

    Page<Revenue> pageQuery(RevenuePageQueryDTO revenuePageQueryDTO);

    @Insert("insert into revenue (order_id, money, time) values (#{orderId}, #{money}, #{time})")
    void save(Revenue revenue);

    @Delete("delete from revenue where order_id = #{orderId}")
    void deleteByOrderId(Long orderId);

    @Select("select sum(money) from revenue")
    Double getSum();

    Double getSumMonthly(LocalDateTime beginTime, LocalDateTime endTime, Integer prize);
}
