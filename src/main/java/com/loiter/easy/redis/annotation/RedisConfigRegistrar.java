package com.loiter.easy.redis.annotation;

import com.loiter.easy.redis.adapter.RedisAdapter;
import com.loiter.easy.redis.core.RedisApplicationContext;
import com.loiter.easy.redis.core.RedisEnvironment;
import com.loiter.easy.redis.handler.RedisClusteProcessor;
import com.loiter.easy.redis.handler.RedisSentinelProcessor;
import com.loiter.easy.redis.handler.RedisSingleProcessor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.type.AnnotationMetadata;

/**
 * Redis 配置初始化
 */
@Slf4j
public class RedisConfigRegistrar implements ImportBeanDefinitionRegistrar {
    @Override
    public void registerBeanDefinitions(AnnotationMetadata annotationMetadata, BeanDefinitionRegistry beanDefinitionRegistry) {
        AnnotationAttributes annotationAttributes = AnnotationAttributes.fromMap(annotationMetadata.getAnnotationAttributes(EnableRedis.class.getName()));

        String redisMode = annotationAttributes.getString("value");

        RedisAdapter redisAdapter = new RedisAdapter();
        redisAdapter.setRedisMode(redisMode);
        RedisSingleProcessor redisSingleProcessor = new RedisSingleProcessor();
        RedisClusteProcessor redisClusteProcessor = new RedisClusteProcessor();
        RedisSentinelProcessor redisSentinelProcessor = new RedisSentinelProcessor();

        RedisEnvironment redisEnvironment = new RedisEnvironment();
        redisEnvironment.setRedisConnectionFactoryIsInit(Boolean.TRUE);

        RedisApplicationContext.builder()
                .build().setBean(redisEnvironment.getClass().getName(), redisEnvironment)
                .setBean(redisAdapter.getClass().getName(), redisAdapter)
                .setBean(redisSingleProcessor.getClass().getName(), redisSingleProcessor)
                .setBean(redisClusteProcessor.getClass().getName(), redisClusteProcessor)
                .setBean(redisSentinelProcessor.getClass().getName(), redisSentinelProcessor);
    }
}
