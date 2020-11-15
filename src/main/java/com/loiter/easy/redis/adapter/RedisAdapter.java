package com.loiter.easy.redis.adapter;

import com.loiter.easy.redis.base.AbstractRedisProcessor;
import com.loiter.easy.redis.constants.RedisModeConstants;
import com.loiter.easy.redis.core.RedisApplicationContext;
import com.loiter.easy.redis.handler.RedisClusteProcessor;
import com.loiter.easy.redis.handler.RedisSentinelProcessor;
import com.loiter.easy.redis.handler.RedisSingleProcessor;
import lombok.extern.slf4j.Slf4j;

/**
 * redis 适配器， 适配redis的各种模式处理类
 */
@Slf4j
public class RedisAdapter {
    private String redisMode;

    public RedisAdapter(){}

    public String getRedisMode(){
        return redisMode;
    }
    public void setRedisMode(String redisMode) {
        this.redisMode = redisMode;
    }

    /**
     * 获取redis处理器
     * @return
     */
    public AbstractRedisProcessor getRedisProcessor() {
        switch (redisMode) {
            case RedisModeConstants
                    .REDIS_SINGLE:
                return RedisApplicationContext.builder()
                        .build()
                        .getBean(RedisSingleProcessor.class);
            case RedisModeConstants.REDIS_CLUSTER:
                return RedisApplicationContext.builder()
                        .build()
                        .getBean(RedisClusteProcessor.class);
            case RedisModeConstants.REDIS_SENTINEL:
                return RedisApplicationContext.builder()
                        .build()
                        .getBean(RedisSentinelProcessor.class);
            default:
                log.error("Failed to obtain redis schema instance params {}", redisMode);
                throw new RuntimeException("Failed to obtain redis schema instance");
        }
    }
}
