package com.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.apache.taglibs.standard.lang.jstl.test.beans.PublicInterface2;
import org.csource.common.MyException;
import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.StorageClient;
import org.csource.fastdfs.StorageServer;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring/spring-mvc.xml")
public class UploadTest {

	@Test
	public void testImageUpload(){
		System.out.println("这是测试方法");
		try {
			ClientGlobal.init("D:/javaEclipsePROM/java-workspace/taobao-manager-web-8081/src/main/resources/config/config.conf");
			TrackerClient trackerClient = new TrackerClient();
			TrackerServer trackerServer = trackerClient.getConnection();
			StorageServer storageServer = null;
			StorageClient storageClient = new StorageClient(trackerServer,storageServer);
			String[] array = storageClient.upload_file("C:/Users/DELL/Desktop/1.jpg","jpg", null);
			for(String str:array){
				System.out.println(str);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
	//底层读取properties文件
	@Test
	public void testGetProperties(){
		
		try {
			FileInputStream fileInputStream = new FileInputStream(new File("D:\\javaEclipsePROM\\java-workspace\\taobao-manager-web-8081\\src\\main\\resources\\config\\constant.properties"));
			Properties properties = new Properties();
			//加载properties
			properties.load(fileInputStream);
			Object object = properties.get("IMAGE_HOST");
			System.out.println(object.toString());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
