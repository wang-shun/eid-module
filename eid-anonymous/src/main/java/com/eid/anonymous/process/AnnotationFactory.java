package com.eid.anonymous.process;

import com.eid.anonymous.annotations.BizImpl;
import com.eid.common.enums.BizType;
import com.google.common.base.Throwables;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import java.util.*;

/**
 * 扫描工厂
 * Created by:ruben Date:2017/2/8 Time:下午3:33
 */
@Slf4j
@Component
public class AnnotationFactory implements ApplicationContextAware {

    private ApplicationContext applicationContext;

    private static Map<Class<? extends Annotation>, Map<String, Object>> implMap = new HashMap<>();

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    /**
     * 获取Bean
     *
     * @param c
     * @return
     */
    public Object getBean(Class c) {
        return this.applicationContext.getBean(c);
    }

    /**
     * 获取注解类
     *
     * @param annotaion
     * @return
     */
    private List<Object> getImplBeans(Class<? extends Annotation> annotaion) {
        List<Object> classes = new ArrayList<>();

        try {
            Map<String, Object> beans = implMap.get(annotaion);
            if (Objects.equals(beans, null) || beans.isEmpty()) {
                beans = applicationContext.getBeansWithAnnotation(annotaion);
                implMap.put(annotaion, beans);
            }

            Object object;
            for (String key : beans.keySet()) {
                object = beans.get(key);
                classes.add(object);
            }
        } catch (Exception e) {
            log.error("get implBeans exception:{};", Throwables.getStackTraceAsString(e));
        }

        log.info("get implBeans request:{};response:{};", annotaion, classes);
        return classes;
    }

    /**
     * 获取认证实现类
     *
     * @param T
     * @param bizType
     * @param <T>
     * @return
     */
    public <T> T getBizImpl(Class<T> T, BizType bizType) {
        try {
            List<Object> classes = getImplBeans(BizImpl.class);
            for (Object object : classes) {

                BizImpl impl = object.getClass().getAnnotation(BizImpl.class);
                BizType[] bizTypes = impl.value();
                for (BizType bt : bizTypes) {
                    if (bt.equals(bizType)) {
                        return (T) object;
                    }
                }
            }

        } catch (Exception e) {
            log.error("获取使用注解使用类异常：{},{}", e.getMessage(), e);
        }
        return null;
    }
}
