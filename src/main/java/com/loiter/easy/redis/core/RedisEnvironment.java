package com.loiter.easy.redis.core;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
public class RedisEnvironment {

    public static final String DEFAULT_ADAPTER_KEY = "etcRedisAdapter";
    public static final String DEFAULT_PROCESSOR_SINGLE_KEY = "redisSingleProcessor";
    public static final String DEFAULT_PROCESSOR_CLUSTER_KEY = "redisClusterProcessor";
    public static final String DEFAULT_PROCESSOR_SENTINEL_KEY = "redisSentinelProcessor";

    private Boolean redisConnectionFactoryIsInit = Boolean.FALSE;

    private String adapterkey = DEFAULT_ADAPTER_KEY;

    private String processorSingleKey = DEFAULT_PROCESSOR_SINGLE_KEY;

    private String processorClusterkey = DEFAULT_PROCESSOR_CLUSTER_KEY;

    private String processorSentinelkey = DEFAULT_PROCESSOR_SENTINEL_KEY;

    public Boolean getRedisConnectionFactoryIsInit() {
        return redisConnectionFactoryIsInit;
    }

    public void setRedisConnectionFactoryIsInit(Boolean redisConnectionFactoryIsInit) {
        this.redisConnectionFactoryIsInit = redisConnectionFactoryIsInit;
    }

    public static String getDefaultAdapterKey() {
        return DEFAULT_ADAPTER_KEY;
    }

    public static String getDefaultProcessorSingleKey() {
        return DEFAULT_PROCESSOR_SINGLE_KEY;
    }

    public static String getDefaultProcessorClusterKey() {
        return DEFAULT_PROCESSOR_CLUSTER_KEY;
    }

    public static String getDefaultProcessorSentinelKey() {
        return DEFAULT_PROCESSOR_SENTINEL_KEY;
    }
}
