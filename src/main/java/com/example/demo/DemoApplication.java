package com.example.demo;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;

@SpringBootApplication
public class DemoApplication {

	@Value("${spring.redis.cluster.nodes}")
	private String[] redisClusterNodes;

	@Bean
	public RedisConnectionFactory connectionFactory() {
		System.out.println(Arrays.toString(redisClusterNodes));
		RedisConnectionFactory redisConnectionFactory =  new JedisConnectionFactory(new RedisClusterConfiguration(Arrays.asList(redisClusterNodes)));
		return redisConnectionFactory;
	}

	@Bean
	@Autowired
	public StringRedisTemplate redisTemplate(RedisConnectionFactory redisConnectionFactory) {
		System.out.println("step1");
		StringRedisTemplate stringRedisTemplate = new StringRedisTemplate(redisConnectionFactory);
		System.out.println("step2");
		stringRedisTemplate.setHashValueSerializer(new Jackson2JsonRedisSerializer<>(Object.class));
		System.out.println("step3");
		ValueOperations<String, String> values = stringRedisTemplate.opsForValue();
		System.out.println("step4");
		values.set("01", "a");
		System.out.println("step5");
		values.set("02", "b");
		System.out.println("step6");
		return stringRedisTemplate;
	}

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

}
