package com.loiter.easy.redis.util;

import com.loiter.easy.redis.adapter.RedisAdapter;
import com.loiter.easy.redis.constants.RedisModeConstants;
import com.loiter.easy.redis.core.RedisApplicationContext;
import com.loiter.easy.redis.core.RedisEnvironment;
import com.loiter.easy.redis.properties.RedisProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.BeanInitializationException;
import org.springframework.data.redis.connection.*;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.util.StringUtils;
import redis.clients.jedis.JedisPoolConfig;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

/**
 * Redis 连接工具类
 */
@Slf4j
public class RedisConnectionFactoryUitl {
    private static final String LOCAL_HOST = "localhost";
    private static final String LOCAL_IP = "127.0.0.1";

    /**
     * 初始化redis连接工厂
     * @param redisProperties  redis配置信息
     * @return JedisConnectionFactory
     * {@see JedisConnectionFactory}
     */
    public static JedisConnectionFactory init(RedisProperties redisProperties) {
        RedisEnvironment env = RedisApplicationContext.builder()
                .build()
                .getEnv();
        if (!env.getRedisConnectionFactoryIsInit()) {
            return new JedisConnectionFactory();
        }
        String redisMode = RedisApplicationContext.builder().build()
                .getBean(RedisAdapter.class)
                .getRedisMode();

        JedisPoolConfig poolConfig = new JedisPoolConfig();
        poolConfig.setMaxTotal(redisProperties.getMaxActive());
        poolConfig.setMaxIdle(redisProperties.getMaxIde());
        poolConfig.setMaxWaitMillis(redisProperties.getMaxWait());
        poolConfig.setMinIdle(redisProperties.getMinIdle());
        // 在获取连接的时候检查有效性， 默认为false
        poolConfig.setTestOnBorrow(true);
        // 向池归还连接时是否做连接有效性检测（ping) 检测到无效连接将会被移除， 默认为false
        poolConfig.setTestOnReturn(false);
        // 在空闲时检查有效性， 默认false
        poolConfig.setTestWhileIdle(true);
        log.info("redis poolConfig Object config params {}", poolConfig.toString());

        JedisClientConfiguration clientConfig = JedisClientConfiguration.builder()
                .usePooling()
                .poolConfig(poolConfig)
                .and()
                .readTimeout(Duration.ofMillis(redisProperties.getTimeOut()))
                .build();

        // redis连接工厂
        JedisConnectionFactory jedisConnectionFactory;

        switch (redisMode) {
            case RedisModeConstants
                    .REDIS_SINGLE:
                RedisStandaloneConfiguration singleRedisConfig = RedisConnectionFactoryUitl
                        .getRedisSingleConfiguration(redisProperties);
                jedisConnectionFactory = new JedisConnectionFactory(singleRedisConfig, clientConfig);
                break;

            case RedisModeConstants.REDIS_CLUSTER:
                RedisClusterConfiguration clusterConfiguration = RedisConnectionFactoryUitl.getRedisClusterConfiguration(redisProperties);
                jedisConnectionFactory = new JedisConnectionFactory(clusterConfiguration, clientConfig);
                break;
            case RedisModeConstants.REDIS_SENTINEL:
                RedisSentinelConfiguration sentinelConfiguration = RedisConnectionFactoryUitl.getRedisSentinelConfiguration(redisProperties);
                jedisConnectionFactory = new JedisConnectionFactory(sentinelConfiguration, clientConfig);
                break;
            default:
                log.error("Initialization of redis connection factory failed: " +
                        "Please specify that redis mode of operation is optional (single, cluster, sentinel");
                throw new BeanInitializationException("Initialization of redis connection factory failed");
        }
        log.info("Initialization of redis connection factory success");
        return jedisConnectionFactory;

    }

    /**
     * 获取 redis 单点配置
     * @param redisProperties
     * @return
     */
    private static RedisStandaloneConfiguration getRedisSingleConfiguration(RedisProperties redisProperties) {
        if (LOCAL_HOST.equals(redisProperties.getHost()) || LOCAL_IP.equals(redisProperties.getHost())) {
            log.warn("Note that redis connection address is localhost. Change address if production environment");
        }

        RedisStandaloneConfiguration redisConfig = new RedisStandaloneConfiguration();
        redisConfig.setHostName(redisProperties.getHost());
        if (!StringUtils.isEmpty(redisProperties.getPassword())) {
            redisConfig.setPassword(RedisPassword.of(redisProperties.getPassword()));
        }
        redisConfig.setPort(redisProperties.getPort());
        redisConfig.setDatabase(redisProperties.getDatabase());
        return redisConfig;
    }

    /**
     * 获取cluster 集群的配置
     * @param redisProperties
     * @return
     */
    private static RedisClusterConfiguration getRedisClusterConfiguration(RedisProperties redisProperties) {
        if (StringUtils.isEmpty(redisProperties.getCluster())) {
            log.error("For redis cluster mode, please make cluster address Configuration name {}", "etc.redis.cluster");
            throw new BeanInitializationException("For redis cluster mode, please make cluster address");
        }
        RedisClusterConfiguration redisConfig = new RedisClusterConfiguration();
        List<RedisNode> nodeList = new ArrayList<>();
        // 获取节点
        String[] cNodes = redisProperties.getCluster().split(",");
        // 分割出集群节点
        splitNode(nodeList, cNodes);
        redisConfig.setClusterNodes(nodeList);
        if (!StringUtils.isEmpty(redisProperties.getPassword())) {
            redisConfig.setPassword(RedisPassword.of(redisProperties.getPassword()));
        }
        return redisConfig;
    }


    private static RedisSentinelConfiguration getRedisSentinelConfiguration(RedisProperties redisProperties) {
        if (StringUtils.isEmpty(redisProperties.getMaster()) || StringUtils.isEmpty(redisProperties.getNodes())) {
            log.error("For redis sentinel mode, please make sentinel address Configuration name {}, {}", "etc.redis.master", "etc.redis.nodes");
            throw new BeanInitializationException("For redis sentinel mode, please make sentinel address");
        }

        RedisSentinelConfiguration redisConfig = new RedisSentinelConfiguration();
        redisConfig.setMaster(redisProperties.getMaster());
        List<RedisNode> nodeList = new ArrayList<>();
        // 获取节点
        String[] cNodes = redisProperties.getNodes().split(",");
        // 分割出集群节点
        splitNode(nodeList, cNodes);
        redisConfig.setSentinels(nodeList);
        if (!StringUtils.isEmpty(redisProperties.getPassword())) {
            redisConfig.setPassword(RedisPassword.of(redisProperties.getPassword()));
        }
        return redisConfig;
    }

    /**
     * 切割集群节点
     */
    private static void splitNode(List<RedisNode> nodeList, String[] cNodes) {
        for (String node: cNodes) {
            String[] hp = node.split(":");
            nodeList.add(new RedisNode(hp[0], Integer.parseInt(hp[1])));
        }
    }
}
