package com.loiter.easy.redis.boot;

import com.loiter.easy.redis.properties.RedisProperties;
import com.loiter.easy.redis.template.EasyRedisTemplate;
import com.loiter.easy.redis.util.RedisConnectionFactoryUitl;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;

import javax.annotation.Resource;

@Configuration
@EnableConfigurationProperties(RedisProperties.class)
public class RedisInitialization {

    @Resource
    private RedisProperties redisProperties;

    @Bean
    @ConditionalOnMissingBean(EasyRedisTemplate.class)
    public EasyRedisTemplate easyRedisTemplate() {
        return new EasyRedisTemplate();
    }

    @Bean
    @ConditionalOnMissingBean(JedisConnectionFactory.class)
    public JedisConnectionFactory jedisConnectionFactory() {
        return RedisConnectionFactoryUitl.init(redisProperties);
    }

}
