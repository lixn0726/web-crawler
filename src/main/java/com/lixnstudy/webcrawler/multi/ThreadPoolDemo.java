package com.lixnstudy.webcrawler.multi;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.*;

/**
 * @author lixn
 * @ClassName ThreadPoolDemo
 * @Description TODO
 * @create 2021/8/30 11:49 上午
 **/
public class ThreadPoolDemo implements Callable<String> {
    public static final Long KEEP_ALIVE_TIME = 1L;

    public static final int CORE_POOL_SIZE = 5;

    public static final int MAX_QUEUE_CAPACITY = 100;

    public static final int MAXIMUM_POOL_SIZE = 10;

    @Override
    public String call() throws Exception {
        System.out.println("it's not executing, getting result ---");
        return Thread.currentThread().getName();
    }

    public static void main(String[] args) {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(
                CORE_POOL_SIZE,
                MAXIMUM_POOL_SIZE,
                KEEP_ALIVE_TIME,
                TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(MAX_QUEUE_CAPACITY),
                new ThreadPoolExecutor.CallerRunsPolicy());
        List<Future<String>> futureList = new ArrayList<>();
        Callable<String> callable = new ThreadPoolDemo();

        for (int i = 0; i < 10; i++) {
            // 提交任务
            Future<String> future = executor.submit(callable);
            // 返回值添加到List，通过 future可以获得执行 Callable得到的返回值
            futureList.add(future);
        }
        // 获取返回结果
        for (Future<String> fut : futureList) {
            try {
                System.out.println(new Date() + "::" + fut.get());
            } catch (InterruptedException | ExecutionException exception) {
                exception.printStackTrace();
            }
        }
        // 关闭线程池
        executor.shutdown();
    }
}
