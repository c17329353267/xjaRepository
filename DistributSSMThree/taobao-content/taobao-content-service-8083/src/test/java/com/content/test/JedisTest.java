package com.content.test;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import javax.sound.midi.Soundbank;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.fasterxml.jackson.databind.annotation.JsonAppend.Attr;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring/spring-redis.xml")
public class JedisTest {

	@Autowired
	private JedisPool jedisPool;
	
	//注入jedisCluser
	@Autowired
	private JedisCluster JedisCluster;
	
	@Test
	public void testRedis(){
		Jedis jedis = new Jedis("192.168.3.110",6379);
        System.out.println("服务真在运行："+jedis.ping());
        //设置 redis 字符串数据
        jedis.set("runoobkey", "中国");
        System.out.println(jedis.get("runoobkey"));
        jedis.close();
	}
	
	//redis连接池设置
	@Test
	public void testRedisPool(){
		 JedisPoolConfig config = new JedisPoolConfig(); 
		 config.setMaxIdle(300);//设置最小连接数
		 config.setMaxTotal(600 );//设置最大连接数
		 config.setMaxWaitMillis(1000);//设置等待时间
		 
		  JedisPool jedisPool = new JedisPool(config, "192.168.3.110",6379);
		 
		 Jedis jedis = jedisPool.getResource();
		 jedis.set("testRedispool", "one");
		 System.out.println(jedis.get("testRedispool"));
		 jedis.close();
		
	}
	//测试spring整合的springpool
	@Test
	public void testJedisPool(){
		Jedis jedis = jedisPool.getResource();
		System.out.println("spring==="+jedis.get("testRedispool"));
	}
	//redis集群测试
	@Test
	public void testJedisCluser() throws IOException{
		
		Set<HostAndPort> nodes = new HashSet<HostAndPort>();
		nodes.add(new HostAndPort("192.168.3.110", 7001));
		nodes.add(new HostAndPort("192.168.3.110", 7002));
		nodes.add(new HostAndPort("192.168.3.110", 7003));
		nodes.add(new HostAndPort("192.168.3.110", 7004));
		nodes.add(new HostAndPort("192.168.3.110", 7005));
		nodes.add(new HostAndPort("192.168.3.110", 7006));
		JedisCluster jedisCluster = new JedisCluster(nodes);
		jedisCluster.set("jedisCluser", "this is jedisCluser");
		String value = jedisCluster.get("jedisCluser");
		System.out.println("jedisCluser:"+value);
		jedisCluster.close();
	}
	
	//jediscluser整合spring配置文件进行测试
	@Test
	public void testJedisCluserSpring(){
		System.out.println(JedisCluster.hget("contentCategory","89"));
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
