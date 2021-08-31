package com.lixnstudy.webcrawler.multi;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * @author lixn
 * @ClassName MultiThread
 * @Description TODO
 * @create 2021/8/30 11:30 上午
 **/
public class MultiThread {
    public static void main(String[] args) {
        /**
         * ThreadPoolExecutor
         * (corePoolSize, 核心线程数
         *  maximumPoolSize,  最大线程数量
         *  keepAliveTime,  非核心线程空闲时保持存活的时间
         *  Timeunit,  时间单位
         *  BlockingQueue<Runnable Task>,  阻塞队列，存储来不及执行的任务的队列
         *  ThreadFactory,  创建线程的工厂
         *  RejectedExecutionHandler  拒绝策略Policy)
         */
        ThreadPoolExecutor executor = new ThreadPoolExecutor(3, 10, 60, TimeUnit.SECONDS, new ArrayBlockingQueue<>(20));
        List<Future<String>> futureList = new ArrayList<>();
        executor.execute( () -> {
            // TODO do something
        });
        // 关闭线程池，会阻止新任务的提交，但不影响已经提交的任务
        executor.shutdown();
        // 关闭线程池，会阻止新任务的提交，并且中断当前正在执运行的线程
        executor.shutdownNow();
    }


}
