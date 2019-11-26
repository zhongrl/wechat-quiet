package com.quiet.live.hall.bi.rest;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.quiet.live.hall.utils.web.WebVo;

import io.swagger.annotations.Api;
@Api(value="文件上传接口",tags={"文件上传接口"})
@Controller
@RequestMapping("/file")
@CrossOrigin(origins = "*",maxAge = 36000)
public class FileUploadController {

	@Value("${file-path}")
	private String filePath;
	
	@Value("${http-path}")
	private String httpPath;
	
	
	@PostMapping(value = "/fileUpload")
	@ResponseBody
    public WebVo<String> fileUpload(@RequestParam(value = "file") MultipartFile file, HttpServletRequest request) {
        if (file.isEmpty()) {
            System.out.println("文件为空空");
        }
        String fileName = file.getOriginalFilename();  // 文件名
        String suffixName = fileName.substring(fileName.lastIndexOf("."));  // 后缀名
        fileName = UUID.randomUUID() + suffixName; // 新文件名
        File dest = new File(filePath + fileName);
        if (!dest.getParentFile().exists()) {
            dest.getParentFile().mkdirs();
        }
        try {
            file.transferTo(dest);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return WebVo.success(httpPath + fileName);
    }

}
