package com.lixnstudy.webcrawler.testCode.multiTEst;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author lixn
 * @ClassName MyThread
 * @Description TODO
 * @create 2021/8/31 2:36 下午
 **/
public class MyThread {

    private static final String USER_AGENT = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/92.0.4515.159 Safari/537.36";
    private static final String COOKIE = "";
    // 代理IP 端口都是9999
    private static String[] Ips = new String[] {"58.252.195.99",  "58.255.5.27",  "58.255.5.122", "58.255.6.155",  "58.255.199:244"};

    public static void main(String[] args) throws Exception{
        // 连接池对象
        PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager();
        connectionManager.setMaxTotal(200);
        connectionManager.setDefaultMaxPerRoute(20);
        CloseableHttpClient httpClient = HttpClients.custom().setConnectionManager(connectionManager).build();

        // URIs to doGet
        List<String> urisToGet = getURIsFromPage("https://movie.douban.com/top250");
        // 为每一个URI创建一个线程
        GetThread[] threads = new GetThread[urisToGet.size()];
        for (int i = 0; i < threads.length; i++) {
            HttpGet httpGet = new HttpGet(urisToGet.get(i));
            threads[i] = new GetThread(httpClient, httpGet);
        }
        // 启动线程
        Long startTime = System.currentTimeMillis();
        for (int j = 0; j < threads.length; j++) {
            threads[j].start();
        }
        // join线程
        for (int k = 0; k < threads.length; k++) {
            try {
                threads[k].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        Long endTime = System.currentTimeMillis();
        System.out.println("进程需要" + (endTime - startTime) / 1000);
    }


    public static class GetThread extends Thread {
        private final CloseableHttpClient httpClient;
        private final HttpContext context;
        private final HttpGet httpGet;

        public GetThread(CloseableHttpClient httpClient, HttpGet httpGet) {
            this.httpClient = httpClient;
            this.httpGet = httpGet;
            this.context = HttpClientContext.create();
        }

        @Override
        public void run() {
            try {
                httpGet.setHeader("User-Agent", USER_AGENT);
                CloseableHttpResponse response = httpClient.execute(httpGet, context);
                try {
                    System.out.println(Thread.currentThread().getName() + " result is " + response.getStatusLine().getStatusCode());
                    Elements simpleDescription = Jsoup.parse(EntityUtils.toString(response.getEntity(), "UTF-8")).select("span.top250-no");
                    for (Element element : simpleDescription) {
                        System.out.println(element.text());
                    }
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    // TODO 处理异常
                    System.out.println("Thread sleep error");
                } finally {
                    response.close();
                }
            } catch (ClientProtocolException e) {
                // TODO 处理客户端协议异常
                System.out.println("Client protocol error");
            } catch (IOException e) {
                // TODO 处理客户端IO异常
                System.out.println("Client IO error");
            }
        }
    }

    private static int getRandomIndex() {
        Random random = new Random();
        return random.nextInt(5);
    }

    private static List<String> getURIsFromPage(String url) throws IOException {
        List<String> URIList = new ArrayList<>();
        CloseableHttpClient client = HttpClientBuilder.create().build();
        HttpGet get = new HttpGet(url);
        get.setHeader("User-Agent", USER_AGENT);
        CloseableHttpResponse response = client.execute(get);
        Elements URIs = Jsoup.parse(EntityUtils.toString(response.getEntity(), "UTF-8")).select("div.pic>a");
        for (Element uri : URIs) {
            URIList.add(uri.attr("href"));
            System.out.println("获取到的电影URI是 ： " + uri.attr("href"));
        }
        return URIList;
    }
}
