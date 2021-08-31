package com.lixnstudy.webcrawler.util;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.LayeredConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.SSLContext;
import java.rmi.registry.Registry;
import java.security.NoSuchAlgorithmException;

/**
 * @author lixn
 * @ClassName HttpClientUtil
 * @Description TODO
 * @create 2021/8/30 7:06 下午
 **/
public class HttpClientUtil {
    private static Logger logger = LoggerFactory.getLogger(HttpClientUtil.class);

/// ============================================== 单例模式 ============================================ ///
///    private CloseableHttpClient httpClient = null;
///    private RequestConfig requestConfig = null;
///   private static final HttpClientUtil HTTP_CLIENT_UTIL = new HttpClientUtil();
    /**
     * 私有构造函数，禁止在外部new一个实例对象
     * 初始化httpClient实例和requestConfig配置信息
     */
///    private HttpClientUtil() {
//        httpClient = HttpClients.custom().disableAutomaticRetries().build();
//        requestConfig = RequestConfig.custom().setConnectionRequestTimeout(10000)
//                .setSocketTimeout(10000).setConnectTimeout(10000).build();// 设置超时时间
//    }

    /**
     * 对外暴露获取实例的接口
     */
//     public synchronized  static HttpClientUtil getInstance() {
//        return HTTP_CLIENT_UTIL;
//    }
/// ================================================================================================ ///
}
