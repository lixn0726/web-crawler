package com.lixnstudy.webcrawler.testCode;


import org.apache.http.*;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;

import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.*;

/**
 * @author lixn
 * @ClassName LoginWithHttpClient
 * @Description TODO 通过HttpClient中的 BasicCookieStore，保存登陆后的cookie，在以后的每次请求都带上这个cookie来进行身份验证
 * @create 2021/8/26 3:38 下午
 **/
public class LoginWithHttpClient {
    static BasicCookieStore cookieStore = null;
    static String HOOK_URL = "http://www.douban.com";
    static String HOOK_FW = "/";
    /**
     * 组装登陆参数
     */
    public static List<NameValuePair> getLoginNameValuePairList() {
        List<NameValuePair> params = new ArrayList<>();
        /// TODO 填写参数
        params.add(new BasicNameValuePair("sso_callback_uri", "/xxx/forward?locale-zh_CN"));
        params.add(new BasicNameValuePair("appName", "xxx"));
        params.add(new BasicNameValuePair("username", "xxx"));
        params.add(new BasicNameValuePair("password", "xxx"));
        return params;
    }

    /**
     * 组装操作参数
     */
    public static List<NameValuePair> getQueryNameValuePairList() {
        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("regionNo", "xxx"));
        params.add(new BasicNameValuePair("pageNo", "xxx"));
        params.add(new BasicNameValuePair("pageSize", "xxx"));
        return params;
    }

    /**
     * 将cookie保存到静态变量中供后续使用
     */
    public static void setCookieStore(HttpResponse httpResponse) {
        System.out.println("-------- setting cookie --------");
        cookieStore = new BasicCookieStore();
        // JSESSIONID
        String setCookie = httpResponse.getFirstHeader("Set-Cookie").getValue();
        String JSESSIONID = setCookie.substring("JSESSIONID=".length(), setCookie.indexOf("."));
        System.out.println("JSESSIONID : " + JSESSIONID);
        // 新建一个Cookie
        BasicClientCookie cookie = new BasicClientCookie("JSESSIONID", JSESSIONID);
        cookie.setVersion(0);
        cookie.setDomain("domain");
        cookie.setPath("/xxx");
        cookieStore.addCookie(cookie);
    }

    // 那第一次要给他加入这个参数才行

    /**
     * 执行Post请求
     */
    public static String doPost(String postUrl, List<NameValuePair> parameterList) {
        String retStr = "";
        // 创建HttpClientBuilder
        HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
        // HttpClient
        CloseableHttpClient client = null;
        if (cookieStore != null) {
            client = httpClientBuilder.setDefaultCookieStore(cookieStore).build();
        } else {
            client = httpClientBuilder.build();
        }
        HttpPost httpPost = new HttpPost(postUrl);
        // 设置请求和传输超时时间
        RequestConfig requestConfig = RequestConfig.custom()
                .setSocketTimeout(30000)
                .setConnectTimeout(30000)
                .build();
        httpPost.setConfig(requestConfig);
        try {
            UrlEncodedFormEntity entity = new UrlEncodedFormEntity(parameterList, "UTF-8");
            httpPost.setEntity(entity);
            CloseableHttpResponse response = client.execute(httpPost);
            // 保存Cookie
            setCookieStore(response);

            HttpEntity httpEntity = response.getEntity();
            // 获取源码
            retStr = EntityUtils.toString(httpEntity, "UTF-8");
            System.out.println(retStr);
            // 释放资源
            client.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return retStr;
    }

//    public static void main(String[] args) {
//        String loginUrl = "http://domain/xxx/sso_login";
//        String queryRegionUrl = "http://domain/xxx/system/getRegionList.do";
//        // 第一次登陆会保存cookie
//        doPost(loginUrl, getLoginNameValuePairList());
//        // 第二次登陆会使用已经存在的cookie
//        doPost(queryRegionUrl, getQueryNameValuePairList());
//
//    }



    // ***************************************************************************************************************** //


    public static void main(String[] args) throws Exception{
        String username = "username";
        String password = "lx990726";
        BasicClientCookie cookie = loginModel(username, password);
        BasicCookieStore store = new BasicCookieStore();
        store.addCookie(cookie);
        HttpClient withCookieClient = HttpClientBuilder.create()
                .setDefaultCookieStore(cookieStore)
                .build();
        String url = "https://www.douban.com";
        if (cookie != null) {
            System.out.println("after login, cookie is : " + cookie.toString());
        }


        HttpGet post = new HttpGet(url);
        HttpResponse response = withCookieClient.execute(post);// 得到response

        System.out.println(response.getStatusLine().getStatusCode());// 返回状态码

        if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
            HttpEntity entity = response.getEntity();
            String html = EntityUtils.toString(entity, "UTF-8");
            Document document = Jsoup.parse(html);
            Elements elements = document.getElementsByTag("a");
            for (Element element : elements) {
                String href = element.attr("href");
                String title = element.text();
                System.out.println(title + ": " + href);
            }
        }
        System.out.println("finish connecting");
    }

    /**
     * 登陆获取cookie
     */
    private static BasicClientCookie loginModel(String username, String password) {
        String url = "https://www.douban.com/";
        String bid = null;
        HttpPost httpPost = new HttpPost(url);
        BasicClientCookie cookie = null;
        try {
            List<NameValuePair> para = new ArrayList<>();
            para.add(new BasicNameValuePair("password", password));
            para.add(new BasicNameValuePair("username", username));// 构建表单
            httpPost.setHeader(
                    "User-Agent",
                    "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/92.0.4515.159 Safari/537.36"
            );
            httpPost.setEntity(new UrlEncodedFormEntity(para, "UTF-8"));// 设置请求体
            BasicCookieStore cookieStore = new BasicCookieStore();// 建立一个CookieStore
            CloseableHttpClient httpClient = HttpClients.custom()
                    .setDefaultCookieStore(cookieStore)
                    .build();
            HttpResponse response = httpClient.execute(httpPost);// 执行请求
            int statusCodes = response.getStatusLine().getStatusCode();// 发送请求，发送成功后，cookie将存在cookieStore中
            if (statusCodes == HttpStatus.SC_OK) {// 请求成功，解析并保存cookie值
                System.out.println("成功发送请求到" + url);
                Header neededHeader = null;
                Header[] setCookie = response.getHeaders("Set-Cookie");
                //
                for (Header need : setCookie) {
//                    if (need.getValue().contains("bid")) {
//                        neededHeader = need;
//                        System.out.println("neededHeader name is : " + neededHeader.getName());
//                        break;
//                    }
                    String headerValue = need.getValue();

                }
                Map<String, String> valueMap = new HashMap<>();
                String cookieValue = neededHeader.getValue();
                cookieValue = cookieValue.substring(cookieValue.indexOf("Set-Cookie") + 1);
                String[] values = cookieValue.split(";");
                for (String value : values) { // 获取Set-Cookie里的所有属性
                    value = value.trim();// 去除空格
                    valueMap.put(value.substring(0, value.indexOf("=")), value.substring(value.indexOf("=") + 1));
                }
                bid = valueMap.get("bid");
                String domain = valueMap.get("Domain");
                String path = valueMap.get("Path");
                String expiry = valueMap.get("Expires");
                cookie = new BasicClientCookie("bid", bid);
                cookie.setSecure(false);
                cookie.setPath(path);
                cookie.setDomain(domain);
                cookie.setVersion(0);
                cookie.setAttribute("expiry", expiry);
//                cookie.setAttribute("domain", domain);
//                cookie.setAttribute("path", path);
                cookieStore.addCookie(cookie);
            } else {// 请求失败
                // TODO NOTHING
                System.out.println("请求失败");
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            httpPost.releaseConnection();// 释放资源
        }
        return cookie;
    }

    /**
     * 创建带有cookie的HttpClient
     */
    private static HttpClient getHttpClient(String username, String password) {
        BasicCookieStore cookieStore = new BasicCookieStore();
        HttpClientBuilder builder = HttpClientBuilder.create();
        BasicClientCookie cookie = loginModel(username, password);
        cookie.setVersion(0);
        // cookie.domain 指定了哪些主机可以访问该cookie的域名
        /*
        例如：
            如果设置了domain = ".google.com"，则所有以".google.com"结尾的域名都可以访问该cookie
        注意第一个字符必须为"."
         */
        // cookie.path 表示cookie的使用路径，path标识指定了主机下的哪些路径可以接受cookie，该URL路径必须存在于请求URL中。
        /*
        例如：
            如果设置为"/sessionWeb/"，则只有contentPath为 "/sessionWeb"的程序可以访问该cookie；
            如果设置为"/"，那么本域名下的contextPath都可以访问该cookie；
        注意最后一个字符必须为"/"
         */
        cookieStore.addCookie(cookie);
        // 带有cookie的HttpClient
        return builder.setDefaultCookieStore(cookieStore).build();
    }

//    private Map<String, String> parseHeaderValue(String headerValue) {
//        Map<String, String> result = new HashMap<>();
//        // 去除前缀Set-Cookie=
//        headerValue = headerValue.substring(0, headerValue.indexOf("="));
//        String[] values = headerValue.split(";");
//        for (String value : values) {
//            value = value.trim();
//            result.put(value.substring(0, value.indexOf("=")), value.substring(value.indexOf("=") + 1));
//        }
//        return result;
//    }

}
