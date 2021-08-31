package com.lixnstudy.webcrawler.testCode;

import org.apache.http.NameValuePair;
import org.apache.http.client.CookieStore;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.util.LinkedList;
import java.util.List;

/**
 * @author lixn
 * @ClassName ZhihuLogin
 * @Description TODO
 * @create 2021/8/30 2:26 下午
 **/
public class ZhihuLogin {
    public static void main(String[] args) {
//        String username = "15820726910";
//        String password = "Lx990726";

        String username = "15989013743";
        String password = "lx990726";


        RequestConfig globalConfig = RequestConfig.custom().setCookieSpec(CookieSpecs.STANDARD).build();
        CookieStore cookieStore = new BasicCookieStore();
        HttpClientContext clientContext = HttpClientContext.create();
        clientContext.setCookieStore(cookieStore);

        CloseableHttpClient client = HttpClients.custom().setDefaultRequestConfig(globalConfig)
                .setDefaultCookieStore(cookieStore).build();

        CloseableHttpResponse response = null;

        try {
            try {
                HttpGet get = new HttpGet("http://www.douban.com");
                response = client.execute(get);
                System.out.println("访问豆瓣首页后的获取的常规Cookie:===============");
                for (Cookie cookie : cookieStore.getCookies()) {
                    System.out.println(cookie.getName() + ":" + cookie.getValue());
                }
                response.close();

                List<NameValuePair> valuePairs = new LinkedList<NameValuePair>();
                valuePairs.add(new BasicNameValuePair("password", password));
                valuePairs.add(new BasicNameValuePair("username", username));
//                valuePairs.add(new BasicNameValuePair("remember_me", "true"));
                UrlEncodedFormEntity entity = new UrlEncodedFormEntity(valuePairs, "UTF-8");

//                HttpPost post = new HttpPost("https://www.zhihu.com/login/email");
                HttpPost post = new HttpPost("https://www.douban.com/");
                post.setEntity(entity);

                response = client.execute(post, clientContext);

                System.out.println("打印相应信息=============");
                response.close();

                System.out.println("登陆成功后，新的Cookie：==================");
                for (Cookie cookie : clientContext.getCookieStore().getCookies()) {
                    System.out.println(cookie.getName() + ":" + cookie.getValue());
                }

//                HttpGet httpGet = new HttpGet("https://www.zhihu.com/people/li-xin-83-89");
                HttpGet httpGet = new HttpGet("https://www.douban.com/people/245370804/");
                response = client.execute(httpGet, clientContext);
                String content = EntityUtils.toString(response.getEntity(), "UTF-8");
                System.out.println("登陆成功后访问的页面====================");
                System.out.println(content);
//                if (content.contains("真的放弃荔枝电台")) {
//                    System.out.println("成功登陆咯");
//                } else {
//                    System.out.println("登陆还是出问题咯");
//                }
                response.close();
            } finally {
                client.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
