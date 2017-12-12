package com.eid.dispatch;

import com.eid.dal.entity.BizCmdEntity;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 任务分发器
 * Created by:ruben Date:2017/3/8 Time:上午9:47
 */
@Slf4j
@Setter
public class Dispatcher implements Runnable {

    private Lock producerLock = new ReentrantLock();

    /**
     * 业务类型名称
     */
    private String name;

    /**
     * 线程池队列长度
     */
    private int queueSize = 5;

    /**
     * 初始线程处理数
     */
    private int coreSize = 0;

    /**
     * 最大线程数
     */
    private int maxSize = 5;

    /**
     * 一天秒数
     */
    private static final long ONE_DAY_SEC = 24 * 60 * 60;

    /**
     * 空闲线程最大闲置时间
     */
    private long keepAliveTime = ONE_DAY_SEC;

    /**
     * 无命令处理时休息时常(秒)
     */
    private long noTaskSleepSeconds = 5;

    /**
     * 线程池
     */
    private WorkerPool pool;

    /**
     * 队列新增任务阀值
     */
    private int hungrySize;

    /**
     * 任务处理列
     */
    private BlockingQueue<Runnable> queue;

    /**
     * 命令管理器，查询待处理命令
     */
    private CmdManager cmdManager;

    /**
     * 命令处理Handler
     */
    private CmdHandler cmdHandler;

    public String getName() {
        return name;
    }

    @Override
    public void run() {
        log.info("分发器[" + getName() + "]启动ing...");
        queue = new ArrayBlockingQueue<>(queueSize);
        pool = new WorkerPool(coreSize, maxSize, keepAliveTime, TimeUnit.SECONDS, queue);

        while (true) {
            producerLock.lock();
            try {
                if (queue.size() >= hungrySize) {
                    continue;
                }
                List<BizCmdEntity> commands = cmdManager.lockAndListCommands(name, queueSize - queue.size());
                // 队列没有任务则进行休眠
                if (commands == null || commands.isEmpty()) {
                    log.debug("任务类型：{}，记录数为空", name);
                    try {
                        TimeUnit.SECONDS.sleep(noTaskSleepSeconds);
                    } catch (InterruptedException e) {
                        log.error("分发器[" + getName() + "]休眠被打断!", e);
                    }
                    continue;
                }
                log.info("任务类型：{}，记录数：{}", name, commands.size());
                for (BizCmdEntity command : commands) {
                    pool.execute(new HandlerWrapper(command, cmdHandler));
                }
            } catch (Throwable t) {
                log.error("分发器[" + getName() + "]执行失败：", t);
                break;
            } finally {
                producerLock.unlock();
            }
        }
    }

    public void destroy() {
        log.warn("收到分发器[" + getName() + "]停止通知!!");
        if (null != pool)
            pool.shutdown();
        Thread.interrupted();
    }

    /**
     * 任务处理线程池
     */
    private class WorkerPool extends ThreadPoolExecutor {
        public WorkerPool(int coreSize, int maxSize, long keepAlive, TimeUnit timeUnit, BlockingQueue<Runnable> queue) {
            super(coreSize, maxSize, keepAlive, timeUnit, queue);
        }

        @Override
        protected void afterExecute(Runnable runnable, Throwable throwable) {
            super.afterExecute(runnable, throwable);
        }
    }
}