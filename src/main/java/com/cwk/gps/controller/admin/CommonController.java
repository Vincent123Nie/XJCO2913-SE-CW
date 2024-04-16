package com.cwk.gps.controller.admin;

import com.cwk.gps.result.Result;
import com.cwk.gps.utils.AliOssUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@RestController("adminCommonController")
@RequestMapping("/admin/common")
@CrossOrigin("http://localhost:8081")
@Slf4j
public class CommonController {

    @Autowired
    private AliOssUtil aliOssUtil;
    /**
     * 文件上传
     * @param file
     * @return
     */
    @PostMapping("/upload")
    public Result<String> upload(MultipartFile file){
        log.info("文件上传： {}",file);

        try {
            //原始文件名
            String originalFilename = file.getOriginalFilename();
            //截取后缀
            String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
            //构造新文件的名称
            String objectName = UUID.randomUUID().toString() + extension;
            //文件的请求路径
            String filePath = aliOssUtil.upload(file.getBytes(),objectName);
            return Result.success(filePath);
        } catch (IOException e) {
            log.info("文件上传失败：{}",e);
        }

        return Result.error("文件上传失败");
    }
}
