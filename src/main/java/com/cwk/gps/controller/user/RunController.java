package com.cwk.gps.controller.user;

import com.cwk.context.BaseContext;
import com.cwk.gps.result.Result;
import com.cwk.gps.service.BaiduService;
import com.cwk.gps.service.UserLocationService;
import com.cwk.pojo.vo.RunParamVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/user/run")
@RestController
@Slf4j
public class RunController {
    @Autowired
    private BaiduService baiduService;

    @Autowired
    private UserLocationService userLocationService;

    /**
     * 上报位置 app中15秒会上报一次
     *
     * @param routeId    路线id
     * @param runParamVo 请求参数，其中包含了经纬度和速度
     * @return
     */
    @PostMapping("{routeId}")
    public Result run(@PathVariable("routeId") Long routeId, @RequestBody RunParamVo runParamVo) {
        //上报数据到鹰眼服务
        Boolean result = this.baiduService.uploadLocation(routeId, runParamVo);
        log.info("上传成功： {}", result);
        if (result) {
            //异步更新自己的位置数据
            Long userId = BaseContext.getCurrentId();
            log.info("用户id{}", userId);
            Double longitude = runParamVo.getLongitude();
            Double latitude = runParamVo.getLatitude();
            userLocationService.uploadLocation(userId, longitude, latitude);
            return Result.success("上传地理位置成功");
        }
        return Result.error("上报地理位置失败！");
    }
}
