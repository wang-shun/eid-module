package com.eid.dispatch.process;

import com.eid.common.enums.FeeType;
import com.eid.dispatch.annotations.FeeTypeImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 描述类的作用
 * Created by:ruben Date:2017/3/13 Time:下午6:14
 */
@Slf4j
@Component
public class AnnotationFactory implements ApplicationContextAware {

    /**
     * 注入Spring ApplicationContext
     */
    private static ApplicationContext applicationContext;

    private static Map<Class<? extends Annotation>, Map<String, Object>> implMap = new HashMap<Class<? extends Annotation>, Map<String, Object>>();

    /**
     * 获取使用注解的类
     *
     * @param c 传入类类型
     * @return Object       返回实类
     */
    public Object getBeans(Class c) {
        return applicationContext.getBean(c);
    }

    /**
     * 获取使用注解的类
     *
     * @param annotation 注解类
     * @return List<Object> 使用注解的类的集合
     */
    private List<Object> getImplBeans(Class<? extends Annotation> annotation) {
        List<Object> classes = new ArrayList<Object>();
        try {

            Map<String, Object> beans = implMap.get(annotation);

            if (beans == null || beans.isEmpty()) {
                beans = applicationContext.getBeansWithAnnotation(annotation);
                implMap.put(annotation, beans);
            }

            Object object;
            for (String key : beans.keySet()) {
                object = beans.get(key);
                classes.add(object);
            }
        } catch (Exception e) {
            log.error("获取使用注解使用类异常：{},{}", e.getMessage(), e);
        }
        return classes;
    }

    /**
     * 获取计费实现类
     *
     * @param T       实现类泛型
     * @param feeType 协议类型
     * @return List<Object>         使用注解的类的集合
     */
    public <T> T getFeeImpl(Class<T> T, FeeType feeType) {
        try {
            List<Object> classes = getImplBeans(FeeTypeImpl.class);
            for (Object object : classes) {

                FeeTypeImpl impl = object.getClass().getAnnotation(FeeTypeImpl.class);
                FeeType[] feeTypes = impl.value();
                for (FeeType at : feeTypes) {
                    if (at.equals(feeType)) {
                        return (T) object;
                    }
                }
            }

        } catch (Exception e) {
            log.error("获取使用注解使用类异常：{},{}", e.getMessage(), e);
        }
        return null;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

}
