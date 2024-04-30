package com.cwk.gps.service.impl;

import com.cwk.gps.mapper.EmployeeMapper;
import com.cwk.gps.mapper.MemberMapper;
import com.cwk.gps.mapper.RevenueMapper;
import com.cwk.gps.mapper.UserMapper;
import com.cwk.gps.service.DashboardService;
import com.cwk.pojo.entity.Order;
import com.cwk.pojo.vo.DashboardVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class DashboardServiceImpl implements DashboardService {

    @Autowired
    private RevenueMapper revenueMapper;
    @Autowired
    private EmployeeMapper employeeMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private MemberMapper memberMapper;

    @Override
    public DashboardVO getDashboard() {

        double revenue = revenueMapper.getSum() == null ? 0.0 : revenueMapper.getSum();
        int employee = employeeMapper.getCount() == null ? 0 : employeeMapper.getCount();
        int user = userMapper.getCount() == null ? 0 : userMapper.getCount();
        int membership = memberMapper.getCount() == null ? 0 : memberMapper.getCount();
        List<List<Double>> monthlyRevenue = new ArrayList<>();

        LocalDateTime begin = LocalDateTime.of(2024,1,1,0,0,0);
        LocalDateTime end = LocalDateTime.of(2024,1,31,23,59,59);
        //获得每个月三种会员类型的收入和总收入
        int[] prize = {Order.PRIZE_MONTH, Order.PRIZE_SEASON, Order.PRIZE_YEAR};
        for (int type = 0; type < 4; type++) {
            List<Double> list = new ArrayList<>();
            for (int month = 0; month <= Period.between(begin.toLocalDate(), LocalDate.now()).getMonths(); month++) {

                LocalDateTime beginTime = begin.plusMonths(month);
                LocalDateTime endTime = end.plusMonths(month);
                Double sumMonthly;

                if (type == 3) {
                    sumMonthly = revenueMapper.getSumMonthly(beginTime,endTime,null);
                } else {
                    sumMonthly = revenueMapper.getSumMonthly(beginTime,endTime,prize[type]);
                }
                sumMonthly = sumMonthly == null ? 0.0 : sumMonthly;
                list.add(sumMonthly);
            }
            monthlyRevenue.add(list);
        }


        DashboardVO dashboardVO = DashboardVO.builder()
                .revenue(revenue)
                .employee(employee)
                .user(user)
                .membership(membership)
                .monthlyRevenue(monthlyRevenue)
                .build();

        return dashboardVO;
    }
}
