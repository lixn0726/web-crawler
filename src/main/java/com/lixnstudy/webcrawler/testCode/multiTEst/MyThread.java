package com.lixnstudy.webcrawler.testCode.multiTEst;

import org.apache.http.impl.client.CloseableHttpClient;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * @author lixn
 * @ClassName MyThread
 * @Description TODO
 * @create 2021/8/31 2:36 下午
 **/
public class MyThread implements Callable<String> {
    @Override
    public String call() throws Exception {

        return null;
    }

    public static void main(String[] args) {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(3, 10, 60, TimeUnit.SECONDS, new ArrayBlockingQueue<>(20));
        List<Future<String>> futureList = new ArrayList<>();
        executor.execute( () -> {
            CloseableHttpClient client = getHttpClient();

        });
    }
    private static CloseableHttpClient getHttpClient() {
        return null;
    }
}
