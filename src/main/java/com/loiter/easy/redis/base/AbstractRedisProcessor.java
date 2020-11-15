package com.loiter.easy.redis.base;

import io.lettuce.core.protocol.RedisCommand;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;

import java.io.*;

/**
 * redis 抽象处理类各种处理类必须继承此类
 */
@Slf4j
public abstract class AbstractRedisProcessor implements RedisCommands {

    /**
     * 默认的线程标识
     */
    protected static ThreadLocal<String> threadFlag = new ThreadLocal<>();

    /**
     * 线程标识
     */
    protected static String THREAD_FLAG_NUM = "thread:flag:num";

    /**
     * 阻塞队列key前缀
     */
    protected static String BLOCKING_KEY_PREFIX = "blocking";

    /**
     * 获取redis 连接工厂
     * @return
     */
    public abstract JedisConnectionFactory getConnectionFactory();

    /**
     * 关才连接
     */
    public abstract void close();

    /**
     *  序列化处理
     * @param object
     * @return
     */
    protected static byte[] serialize(Object object) {
        ObjectOutputStream objectOutputStream = null;
        ByteArrayOutputStream byteArrayOutputStream = null;
        try{
            byteArrayOutputStream = new ByteArrayOutputStream();
            objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
            objectOutputStream.writeObject(object);
            return byteArrayOutputStream.toByteArray();
        } catch (Exception exception) {
            log.error("etc Redis serialize Object error: ", exception);
        } finally {
            try{
                if (objectOutputStream != null) {
                    objectOutputStream.close();
                }
                if (byteArrayOutputStream != null) {
                    byteArrayOutputStream.close();
                }
            } catch (IOException e) {
                log.error("etc Redis output Stream close error: ", e);
            }
        }
        return null;
    }

    /**
     * 反序列化处理
     * @param bytes
     * @return
     */
    protected static Object unSerialize(byte[] bytes) {
        ObjectInputStream objectInputStream = null;
        ByteArrayInputStream byteArrayInputStream = null;
        try{
            byteArrayInputStream = new ByteArrayInputStream(bytes);
            objectInputStream = new ObjectInputStream(byteArrayInputStream);
            return objectInputStream.readObject();
        } catch (Exception e) {
            log.error("etcRedis unSerialize Object error: ", e);
        } finally {
            try {
                if (objectInputStream != null) {
                    objectInputStream.close();
                }
                if (byteArrayInputStream != null) {
                    byteArrayInputStream.close();
                }
            } catch (IOException ioe) {
                log.error("etcRedis inputStream close error:", ioe);
            }
        }
        return null;
    }
}
