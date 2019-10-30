package com.taobao.freemarker.test;

import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import freemarker.template.Configuration;
import freemarker.template.Template;
/*
 * freeMarker的基本使用
 */
public class FreeMarkerTest {

	@Test
	public void testFreeMarker(){
		try {
			//System.out.println("freemarker");
			//第一步创建一个Configuration对象，直接new一个对象，构造方法的参数就是freemarker对于斑斑的号
			Configuration configuration = new Configuration(Configuration.getVersion());
			//设置版本文件所在的路径
			configuration.setDirectoryForTemplateLoading(new File("D:\\javaEclipsePROM\\java-workspace\\taobao-item-web-8087\\src\\main\\webapp\\WEB-INF\\ftl"));
			//设置模板文件使用的字符集 utf-8
			configuration.setDefaultEncoding("utf-8");
			//加载一个模板，创建一个模板对象
		
			Template template = configuration.getTemplate("hellow01test.ftl");//ftl:freemarker thymeleaf language
			//创建一个模板使用的数据集，可以是pojo也可以是map,一般使用map
			Map map = new HashMap<>();
			//向数据集中添加数据
			map.put("hello", "this is 我的祖国");
			//创建一个Writer对象，一般创建一个FilterWriter对象，指定文件生成的文件名。
			Writer writer = new FileWriter(new File("D:/freemarkertestFile/htmltest/hello.html"));
			//调用模板对象的process方法输出文件
			template.process(map, writer);
			//关闭流
			writer.close();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
}
