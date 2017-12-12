package com.eid.dispatch;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.task.SimpleAsyncTaskExecutor;

import java.util.Map;

/**
 * 描述类的作用
 * Created by:ruben Date:2017/3/8 Time:上午9:44
 */
@Slf4j
public class DispatcherStart {

    /**
     * 分发器线程名称
     */
    private String name;
    /**
     * 分发器列表，Key：分发器线程名称前缀；Value:分发器实例
     */
    private Map<String, Dispatcher> dispatcherMap;

    public void setDispatcherMap(Map<String, Dispatcher> dispatcherMap) {
        this.dispatcherMap = dispatcherMap;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void init() {
        log.info("------- 【{}】正在启动...... -------", getName());
        SimpleAsyncTaskExecutor cmdDispatchExecutor = new SimpleAsyncTaskExecutor();
        cmdDispatchExecutor.setDaemon(true);
        cmdDispatchExecutor.setConcurrencyLimit(dispatcherMap.size());
        for (String dispatcherName : dispatcherMap.keySet()) {
            cmdDispatchExecutor.setThreadNamePrefix(dispatcherName);
            cmdDispatchExecutor.execute(dispatcherMap.get(dispatcherName));
        }
        log.info("------- 【{}】启动完毕! -------", getName());
    }
}
