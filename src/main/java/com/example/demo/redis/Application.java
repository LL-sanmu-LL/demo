package com.example.demo.redis;

import javax.annotation.Resource;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@SpringBootApplication
public class Application implements CommandLineRunner {

  @Resource
  private RedisTemplate template;

  public static void main(String[] args) {
    System.out.println("start");
    SpringApplication.run(Application.class);
    System.out.println("end");
  }

  @Override
  public void run(String... args) throws Exception {
    ValueOperations operations = template.opsForValue();
    operations.set("n1", "abc");
  }

  @Bean
  public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory factory) {

    RedisTemplate<String, Object> template = new RedisTemplate<>();
    // 配置连接工厂
    template.setConnectionFactory(factory);
    template.setKeySerializer(new StringRedisSerializer());
    template.setHashKeySerializer(new StringRedisSerializer());
    template.afterPropertiesSet();
    return template;
  }
}
