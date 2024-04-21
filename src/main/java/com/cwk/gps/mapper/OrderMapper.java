package com.cwk.gps.mapper;

import com.cwk.pojo.dto.OrderPageQueryDTO;
import com.cwk.pojo.entity.Order;
import com.cwk.pojo.vo.OrderVO;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface OrderMapper {


    Page<OrderVO> pageQuery(OrderPageQueryDTO orderPageQueryDTO);

    @Update("update `order` set status = #{status} where id = #{id}")
    void updateStatusWithId(Integer status, Long id);

    @Select("select * from `order` where id = #{id}")
    Order getById(Long id);
}
