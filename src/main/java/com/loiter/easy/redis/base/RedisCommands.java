package com.loiter.easy.redis.base;

import redis.clients.jedis.ScanResult;

import java.util.List;
import java.util.Map;

/**
 * redis 操作函数顶级接口
 */
public interface RedisCommands {
    /**
     * 判断redis#key键是否存在
     * @param key
     * @return
     */
    boolean exists(String key);

    /**
     * 通过redis#key判断value长度
     * @param key
     * @return
     */
    Long length(String key);

    void set(String key, String value);

    // 添加key value
    void set(String key, Object obj);

    /**
     * 添加key并设置过期时间
     * @param key
     * @param value
     * @param seconds 过期时间单位为秒
     */
    void setex(String key, String value, int seconds);

    /**
     * 添加并设置过期时间
     * @param key
     * @param obj
     * @param seconds 过期时间单位为秒
     */
    void setex(String key, Object obj, int seconds);

    String get(String key);

    List<String> get(String ... key);

    ScanResult<String> scan(int index, String regx);

    ScanResult<String> scan(String regx);

    <T> T get(String key, Class<T> clazz);

    Long getValid(String key);

    Long removeValid(String key);

    Long getTimeOut(String key);

    Long delete(String key);

    Long incr(String key);

    void hset(String key, String field, String value);

    String hget(String key, String field);

    Long hdel(String key, String field);

    void hmset(String key, Map<String, String> map);

    List<String> hmget(String key, String...fields);

    Long hlen(String key);

    Map<String, String> hgetAll(String key);

    void lpush(String key, String ... strings);

    void rpush(String key, String...strings);

    Long llen(String key);

    String lindex(String key, long index);

    List<String> lrangeAll(String key);

    List<String> lrange(String key, long start, long end);

    String lpop(String key);

    String rpop(String key);

    void setTimeOut(String key, int seconds);

    /**
     * redis 分布式锁  锁定
     * @param key
     * @param value
     * @param expire
     * @return
     */
    boolean tryLock(String key, String value, long expire);

    /**
     * redis 分布式锁  解锁
     * @param key
     * @param value
     * @return
     */
    boolean unLock(String key, String value);

    /**
     * redis 分布式锁  锁定
     * @param key
     * @param expireSecond
     * @param waitSecond
     * @param flag
     * @return
     */
    boolean tryLockWait(String key, long expireSecond, int waitSecond, String flag);

    /**
     * redis 分布式锁  解锁
     * @param key
     * @param flag
     * @return
     */
    boolean unlockWait(String key, String flag);

    /**
     * 获取线程标
     * @return
     */
    String getThreadFlag();
}
