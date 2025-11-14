package com.example.uberlocationservices.configurations;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfiguration {

    /**
     * to return the redis connection object
     */
    @Bean
   public RedisConnectionFactory redisConnectionFactory() {
       JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory();
       jedisConnectionFactory.setHostName("localhost");
       jedisConnectionFactory.setPort(6379);
       return jedisConnectionFactory;
   }

    /**
     * to make what kind of value is stored inside redis and
     * automatically use serialize and deserialize algorithm>>
     * use to implement the strategy that how our key and value is going to be serialized>>
     *
     */
   @Bean
    public RedisTemplate<String,String> redisTemplate() {
        RedisTemplate<String,String> redisTemplate = new RedisTemplate<>();

        redisTemplate.setConnectionFactory(redisConnectionFactory());
        redisTemplate.setKeySerializer(new StringRedisSerializer());
         redisTemplate.setValueSerializer(new StringRedisSerializer());

         return redisTemplate;
   }


}
