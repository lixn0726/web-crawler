package com.lixnstudy.webcrawler.myCode;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.HttpClientBuilder;

/**
 * @author lixn
 * @ClassName HttpClientInstance
 * @Description TODO 单例模式操作HttpClient 但是cookie怎么办呢？？
 * @create 2021/8/27 11:14 上午
 **/
public class HttpClientInstance {
    private static HttpClient instance;
    private static BasicCookieStore cookieStore;

    private HttpClientInstance() {

    }

    public static void main(String[] args) throws Exception{
        HttpClient client = HttpClientBuilder.create().build();
        HttpGet get = new HttpGet("https://movie.douban.com/top250");
        get.setHeader("User-Agent",
                "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/92.0.4515.159 Safari/537.36 Edg/92.0.902.78");
        HttpResponse response = client.execute(get);
        System.out.println(response.getStatusLine().getStatusCode());
    }


    public static HttpClient getInstance() {
        if (instance == null) {
            if (instance == null) {
                instance = HttpClientBuilder.create()
                        .setDefaultCookieStore(cookieStore)
                        .build();

            }
        }
        return instance;
    }
}
