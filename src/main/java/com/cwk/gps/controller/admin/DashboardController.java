package com.cwk.gps.controller.admin;

import com.cwk.gps.result.Result;
import com.cwk.gps.service.DashboardService;
import com.cwk.pojo.vo.DashboardVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/dashboard")
@CrossOrigin("http://localhost:8081")
@Slf4j
public class DashboardController {

    @Autowired
    private DashboardService dashboardService;

    /**
     * 获得所有首页需要的数据
     * @return
     */
    @GetMapping
    public Result<DashboardVO> getDashboard() {
        DashboardVO dashboardVO = dashboardService.getDashboard();
        return Result.success(dashboardVO);
    }

}
