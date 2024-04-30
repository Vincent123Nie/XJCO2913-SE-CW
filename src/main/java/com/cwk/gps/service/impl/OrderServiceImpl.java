package com.cwk.gps.service.impl;

import com.cwk.context.BaseContext;
import com.cwk.gps.mapper.MemberMapper;
import com.cwk.gps.mapper.OrderMapper;
import com.cwk.gps.mapper.RevenueMapper;
import com.cwk.gps.result.PageResult;
import com.cwk.gps.service.OrderService;
import com.cwk.pojo.dto.OrderPageQueryDTO;
import com.cwk.pojo.dto.OrdersSubmitDTO;
import com.cwk.pojo.entity.Member;
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
    @Autowired
    private MemberMapper memberMapper;


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
        Order order = orderMapper.getById(id);
        Long userId = order.getUserId();
        Integer type = order.getType();
        if (order.getStatus() == status) {
            return;
        } else if (status == Order.COMPLETE) {
            //完成订单，向收入表中插入数据，更新会员表数据
            orderMapper.updateStatusWithId(status, id);

            Revenue revenue = Revenue.builder()
                    .orderId(id)
                    .money(orderMapper.getById(id).getPrice())
                    .time(LocalDateTime.now())
                    .build();
            revenueMapper.save(revenue);

            Member member = memberMapper.getByUserId(userId);

            if (member == null) {
                //新增会员
                memberMapper.save(userId,type);
            } else {
                //增加会员时长
                memberMapper.increase(userId,type);
            }

        } else if (status == Order.CANCEL) {
            //取消订单
            orderMapper.updateStatusWithId(status, id);

            //如果订单原本是完成状态，从收入表中删除数据，更新会员表数据
            if (order.getStatus() == Order.COMPLETE) {
                revenueMapper.deleteByOrderId(id);
                //减少会员时长
                memberMapper.decrease(userId, type);
            }
        }

    }

    @Override
    public Long submitOrder(OrdersSubmitDTO ordersSubmitDTO) {
        Order order = new Order();
        order.setUserId(BaseContext.getCurrentId());
        order.setType(ordersSubmitDTO.getType());
        order.setPrice(ordersSubmitDTO.getPrice());
        order.setStatus(Order.WAITING);
        orderMapper.insert(order);
        return order.getId();
    }
}
