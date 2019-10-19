package com.taobao.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.oracle.util.FastDFSClient;

@RestController
@RequestMapping("/pic/") 
public class FileUploadController {
	@Value("${IMAGE_HOST}")
	private String IMAGE;
	@RequestMapping("upload")
	public Map<String, Object> uploadImages(@RequestParam("uploadFile")MultipartFile uploadFile){
		System.out.println("in FileUploadController uploadImages");
		//获取文件后缀
		String suffix = uploadFile.getOriginalFilename().substring(uploadFile.getOriginalFilename().lastIndexOf(".")+1);
		Map<String, Object> map = new HashMap<>();
		try {
			FastDFSClient fastClient = new FastDFSClient("classpath:config/config.conf");
			String imgurl = fastClient.uploadFile(uploadFile.getBytes(), suffix);
			imgurl = IMAGE+imgurl;
			map.put("url",imgurl);
			map.put("error",0);//1正常  0 失败
			System.out.println(imgurl);
		} catch (Exception e) {
			map.put("message","上传失败");
			map.put("error",1);//1正常  0 失败
			e.printStackTrace();
		}
				
		
		return map;
	}
}
