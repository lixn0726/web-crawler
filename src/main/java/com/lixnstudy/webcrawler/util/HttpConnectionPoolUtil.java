package com.lixnstudy.webcrawler.util;

import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ScheduledExecutorService;


/**
 * @author lixn
 * @ClassName HttpConnectionPoolUtil
 * @Description TODO
 * @create 2021/8/31 11:20 上午
 **/
public class HttpConnectionPoolUtil {
//    private static Logger logger = LoggerFactory.getLogger(HttpConnectionPoolUtil.class);
//
////    private static final int CONNECT_TIMEOUT = Config.getHttpConnectTimeout();
//    private static final int CONNECT_TIMEOUT = 10000;// 设置链接建立的超时时间为10s
//    private static final int SOCKET_TIMEOUT = 10000;
//    private static final int MAX_CONN = 100;// 最大连接数
//    private static final int MAX_PRE_ROUTE = 10000;//
//    private static final int MAX_ROUTE =  1000;// TODO 这些属性暂时定为这么大，了解后再进行设置
//    private static CloseableHttpClient httpClient; // 发送请求的单例客户端
//    private static PoolingHttpClientConnectionManager manager; // 连接池管理类
//    private static ScheduledExecutorService monitorExecutor;
//
//    private final static Object syncLock = new Object();// 相当于线程锁，用于线程安全
//
//    /**
//     * 对http请求进行基本设置
//     */
//    private static void setRequestConfig(HttpRequestBase httpRequestBase) {
//        RequestConfig requestConfig = RequestConfig.custom().setConnectionRequestTimeout(CONNECT_TIMEOUT)
//                .setConnectTimeout(CONNECT_TIMEOUT)
//                .setSocketTimeout(SOCKET_TIMEOUT)
//                .build();
//        httpRequestBase.setConfig(requestConfig);
//    }
//
//    public static CloseableHttpClient getHttpClient(String url) {
//        String hostName = url.split("/")[2];
//        System.out.println(hostName);
//        int port = 80;
//        if (hostName.contains(":")) {
//            String[] args = hostName.split(":");
//            hostName = args[0];
//            port = Integer.parseInt(args[1]);
//        }
//        return null;
//    }

    private static final String cookieValue = "ll=\"118281\"; bid=xXOFVbD9elY; dbcl2=\"245370804:7ULN5r+hQKc\"; ck=M8Af";

    private static Logger logger = LoggerFactory.getLogger(HttpConnectionPoolUtil.class);
    private static final String DEFAULT_ENCODING = "UTF-8";
    private static int connectPoolTimeout = 2000;// 设定从连接池获取可用连接的时间
    private static int connectTimeout = 5000;// 建立连接超时时间
    private static int socketTimeout = 5000;// 设置等待数据超时时间5秒钟，根据业务调整
    private static int maxTotal = 100;// 连接池最大连接数
    private static int maxPerRoute = 10;// 每个主机的并发
    private static int maxRoute = 50;// 目标主机的最大连接数
    private static CloseableHttpClient httpClient = null;
    private final static Object syncLock = new Object();// 相当于线程锁，用于线程安全
    private static PoolingHttpClientConnectionManager cm = null;
    private static ScheduledExecutorService monitorExecutor;
    private static boolean isShowUsePoolLog = true;

    /**
     * 获取HttpClient对象
     */
    public static CloseableHttpClient getHttpClient() {
        RequestConfig config = RequestConfig.custom().build();
        if (HttpConnectionPoolUtil.httpClient == null) {
            return HttpClients.custom().setConnectionManager(cm).build();
        }
        return httpClient;
    }


    static {
        cm = new PoolingHttpClientConnectionManager();
        cm.setMaxTotal(maxTotal);
        cm.setDefaultMaxPerRoute(maxPerRoute);
    }


}
