package com.cwk.gps.controller.user;

import com.cwk.gps.result.Result;
import com.cwk.gps.common.GpxReader;
import com.cwk.gps.common.Track;
import com.cwk.gps.common.TrackPoint;
import com.cwk.gps.common.TrackSegment;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/user/common")
@Slf4j
public class CommonController {

    /**
     * GPX文件上传
     * @param file
     * @return
     */
    @PostMapping("/upload")
    public Result upload(MultipartFile file) throws IOException {
        log.info("文件上传： {}",file);
        String uploadDir = "E://";
        File serverFile = new File(uploadDir + file.getOriginalFilename());
        file.transferTo(serverFile);
        String filePath = serverFile.getAbsolutePath();

        GpxReader gpxFileReader = new GpxReader(filePath);
        Track track = gpxFileReader.readData();
        List<TrackSegment> segments = track.getSegments();
        segments.forEach(x -> {
            List<TrackPoint> points = x.getPoints();
            points.forEach(y -> {
                System.out.println("======================" );
                double longitude = y.getLongitude();
                System.out.println("经度 = " + longitude);
                double latitude = y.getLatitude();
                System.out.println("纬度 = " + latitude);
                Double elevation = y.getElevation();
                System.out.println("海拔 = " + elevation);
                Date time = y.getTime();
                System.out.println("时间 = " + time);
            });
        });
        return Result.success(track);
    }
}
