package com.cwk.gps.task;

import com.cwk.gps.mapper.OrderMapper;
import com.cwk.pojo.entity.Order;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 定时任务类，定时处理订单状态
 */
@Component
@Slf4j
public class OrderTask {

    @Autowired
    private OrderMapper orderMapper;

    /**
     * 每分钟检查一次超时未支付订单
     */
    @Scheduled(cron = "0 * * * * ?")
    public void updateTimeoutOrder() {
        List<Order> orders = orderMapper.getWaitingOrders(Order.WAITING);
        log.info("定时处理订单状态：{}", orders);

        if (orders != null && !orders.isEmpty()) {
            for (Order order: orders) {
                if (order.getCreateTime().plusMinutes(15).isBefore(LocalDateTime.now())) {
                    orderMapper.updateStatusWithId(Order.CANCEL,order.getId());
                }
            }
        }
    }
}
