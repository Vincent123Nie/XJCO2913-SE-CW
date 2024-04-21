package com.cwk.gps.service.impl;

import com.cwk.gps.mapper.OrderMapper;
import com.cwk.gps.mapper.RevenueMapper;
import com.cwk.gps.result.PageResult;
import com.cwk.gps.service.OrderService;
import com.cwk.pojo.dto.OrderPageQueryDTO;
import com.cwk.pojo.entity.Order;
import com.cwk.pojo.entity.Revenue;
import com.cwk.pojo.vo.OrderVO;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private RevenueMapper revenueMapper;


    @Override
    public PageResult pageQuery(OrderPageQueryDTO orderPageQueryDTO) {
        //开始分页查询
        PageHelper.startPage(orderPageQueryDTO.getPage(),orderPageQueryDTO.getPageSize());

        Page<OrderVO> page = orderMapper.pageQuery(orderPageQueryDTO);
        long total = page.getTotal();
        List<OrderVO> records = page.getResult();
        return new PageResult(total, records);
    }

    @Override
    @Transactional
    public void completeOrCancel(Integer status ,Long id) {
        //完成订单，向收入表中插入数据，更新会员表数据
        if (status == Order.COMPLETE) {
            orderMapper.updateStatusWithId(status, id);

            Revenue revenue = Revenue.builder()
                    .orderId(id)
                    .money(orderMapper.getById(id).getPrice())
                    .time(LocalDateTime.now())
                    .build();
            revenueMapper.save(revenue);


        }

        //取消订单，从收入表中删除数据，更新会员表数据
        if (status == Order.CANCEL) {
            orderMapper.updateStatusWithId(status, id);

            revenueMapper.deleteByOrderId(id);


        }

    }
}
