package com.loiter.easy.redis.template;

import com.loiter.easy.redis.adapter.RedisAdapter;
import com.loiter.easy.redis.core.RedisApplicationContext;
import com.loiter.easy.redis.core.RedisEnvironment;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class EasyRedisTemplate {

    public boolean exists(String key) {
        return getRedisAdapter().getRedisProcessor().exists(key);
    }

    private RedisAdapter getRedisAdapter() {
        RedisEnvironment env = RedisApplicationContext.builder()
                .build()
                .getEnv();
        if (!env.getRedisConnectionFactoryIsInit()) {
            log.error("Please open the redis intergration annotaion" +
                    "@enableEtcRedis and select redis mode {single, cluster, sentinel}");
            throw new RuntimeException("Please open the redis intergration annotaion" +
                    "@enableEtcRedis and select redis mode {single, cluster, sentinel} ");
        }
        return RedisApplicationContext.builder().build().getBean(RedisAdapter.class);
    }
}
