package org.example.controller;

import org.example.pojo.Result;
import org.example.utils.AliOssUtil;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@RestController
public class FileUploadController {
    @PostMapping("/upload")
    public Result<String> upload(MultipartFile file) throws Exception {
        //把文件内容储存到本地上
        String originalFilename = file.getOriginalFilename();
        //保证文件名唯一，防止文件覆盖
        String filename = UUID.randomUUID().toString() + originalFilename.substring(originalFilename.lastIndexOf("."));
//        本地储存
//        file.transferTo(new File("F:\\Web Restudy\\HM\\SpringBoot + Vue\\files\\" + originalFilename));
//        阿里云储存
        String url = AliOssUtil.uploadFile(filename,file.getInputStream());
        return  Result.success(url);
    }
}
