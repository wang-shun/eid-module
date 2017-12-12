package com.eid.anonymous;

import com.google.common.util.concurrent.AbstractIdleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

/**
 * 描述类的作用
 * Created by:ruben Date:2016/12/15 Time:下午5:43
 */
@Slf4j
@Component
public class Bootstrap extends AbstractIdleService {

    private ClassPathXmlApplicationContext context;

    public static void main(String[] args) {
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.startAsync();
        try {
            Object lock = new Object();
            synchronized (lock) {
                while (true) {
                    lock.wait();
                }
            }
        } catch (InterruptedException ex) {
            log.error("ignore interruption");
        }
    }

    @Override
    protected void startUp() throws Exception {
        context = new ClassPathXmlApplicationContext(new String[]{"classpath:dubbo-provider.xml"});
        context.start();
        context.registerShutdownHook();
        log.info("service started successfully");
    }

    @Override
    protected void shutDown() throws Exception {
        context.stop();
        log.info("service stopped successfully");
    }
}
