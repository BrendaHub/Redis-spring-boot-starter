package com.loiter.easy.redis.boot;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
@Setter
@Getter
public class RedisBeanContext implements ApplicationContextAware {

    private static ApplicationContext applicationContext;


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        RedisBeanContext.applicationContext = applicationContext;
    }

    public static <T> T getBean(String beanName) {
        return (T)applicationContext.getBean(beanName);
    }

    public static <T> T getBean(Class<T> tClazz) {
        return applicationContext.getBean(tClazz);
    }


}
