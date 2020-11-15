package com.loiter.easy.redis.annotation;

import com.loiter.easy.redis.constants.RedisModeConstants;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * 启动redis注解， 默认模式为单 点
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Import(RedisConfigRegistrar.class)
public @interface EnableRedis {
    String value() default RedisModeConstants.REDIS_SINGLE;
}
