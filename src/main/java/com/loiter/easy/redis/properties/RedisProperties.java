package com.loiter.easy.redis.properties;

import com.loiter.easy.redis.annotation.Describe;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.commons.pool2.impl.BaseObjectPoolConfig;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Redis 配置信息类
 */
@Component
@ConfigurationProperties(prefix = "easy.redis")
public class RedisProperties {

    @Describe(value = "redis 单点地址")
    private String host = "localhost";

    @Describe(value = "redis 集群地址 ")
    private String cluster;

    @Describe(value = "redis 哨兵主")
    private String master;

    @Describe(value = "redis 哨兵从节点")
    private String nodes;

    @Describe(value = "redis 端口")
    private Integer port = 6379;

    @Describe(value = "redis 数据库")
    private Integer database = 0;

    @Describe(value = "redis 密码")
    private String password;

    @Describe(value = "redis 读取超时时间")
    private Long timeOut = 5000L;

    @Describe(value = "redis 最大连接数")
    private Integer maxActive = 200;

    @Describe(value = "redis 获取连接时最大的等待毫秒数")
    private Long maxWait = BaseObjectPoolConfig.DEFAULT_MAX_WAIT_MILLIS;

    @Describe(value = "redis 最大的空闲连接数")
    private Integer maxIde = GenericObjectPoolConfig.DEFAULT_MAX_IDLE;

    @Describe(value = "redis 最小的空闲连接数")
    private Integer minIdle = GenericObjectPoolConfig.DEFAULT_MIN_IDLE;

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getCluster() {
        return cluster;
    }

    public void setCluster(String cluster) {
        this.cluster = cluster;
    }

    public String getMaster() {
        return master;
    }

    public void setMaster(String master) {
        this.master = master;
    }

    public String getNodes() {
        return nodes;
    }

    public void setNodes(String nodes) {
        this.nodes = nodes;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public Integer getDatabase() {
        return database;
    }

    public void setDatabase(Integer database) {
        this.database = database;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Long getTimeOut() {
        return timeOut;
    }

    public void setTimeOut(Long timeOut) {
        this.timeOut = timeOut;
    }

    public Integer getMaxActive() {
        return maxActive;
    }

    public void setMaxActive(Integer maxActive) {
        this.maxActive = maxActive;
    }

    public Long getMaxWait() {
        return maxWait;
    }

    public void setMaxWait(Long maxWait) {
        this.maxWait = maxWait;
    }

    public Integer getMaxIde() {
        return maxIde;
    }

    public void setMaxIde(Integer maxIde) {
        this.maxIde = maxIde;
    }

    public Integer getMinIdle() {
        return minIdle;
    }

    public void setMinIdle(Integer minIdle) {
        this.minIdle = minIdle;
    }
}
