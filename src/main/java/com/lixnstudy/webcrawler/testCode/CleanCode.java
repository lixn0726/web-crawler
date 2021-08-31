package com.lixnstudy.webcrawler.testCode;


import org.apache.http.*;
import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author lixn
 * @ClassName CleanCode
 * @Description TODO
 * @create 2021/8/27 7:24 下午
 **/
public class CleanCode {
    private static BasicCookieStore cookieStore;
    private static String DEFAULT_ENCODING = "UTF-8";
    private static Integer DEFAULT_STR_LENGTH = 10000;
    private static Map<String, String> cookieValue;
    private static String testURI = "";

        // TODO 首先第一次登陆，获取cookie，然后递归发送请求直至到达网页终点

    public static void main(String[] args) throws Exception{
        CookieStore cookieStore = new BasicCookieStore();
        String cookieValue = "ll=\"118281\"; bid=A8NSPB2yiw4; __utmc=30149280; __gads=ID=5004543af5d6ab04-223a32382dcb000d:T=1630292803:RT=1630292803:S=ALNI_MbKSeedDYd0wC3toZSNpIHRx4rQxg; __utmz=30149280.1630292911.2.2.utmcsr=baidu|utmccn=(organic)|utmcmd=organic; _ga_RXNMP372GL=GS1.1.1630293556.2.0.1630293556.0; dbcl2=\"134088041:RqF+IaIq6jE\"; ck=XjVj; push_noty_num=0; push_doumail_num=0; __utmv=30149280.13408; ap_v=0,6.0; __utma=30149280.44422392.1630292729.1630292911.1630302642.3; _pk_ref.100001.8cb4=%5B%22%22%2C%22%22%2C1630304305%2C%22https%3A%2F%2Fmovie.douban.com%2Fsubject%2F1292052%2F%22%5D; _pk_ses.100001.8cb4=*; __utmt=1; __utmb=30149280.2.10.1630302642; _pk_id.100001.8cb4=647b271ba2acb819.1630052384.5.1630304489.1630293898.";
//        String cookieValue = "ll=\"118281\"; bid=A8NSPB2yiw4";
        BasicClientCookie cookie = new BasicClientCookie("Cookie", cookieValue);
        cookie.setPath("/");
        cookie.setDomain(".douban.com");
        cookieStore.addCookie(cookie);
        HttpClient client = HttpClientBuilder.create()
                .setDefaultCookieStore(cookieStore)
                .build();
        HttpGet get = new HttpGet("https://www.douban.com/people/134088041/");
        get.setHeader("User-Agent",
                "'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.87 Safari/537.36'");
        System.out.println(Arrays.toString(get.getHeaders("Cookie")));
        HttpResponse response = client.execute(get);
        System.out.println(response.getStatusLine().getStatusCode());
        HttpEntity entity = response.getEntity();
        String html = EntityUtils.toString(entity, "UTF-8");
        Document document = Jsoup.parse(html);
        Elements elements = document.getElementsByTag("a");
        for (Element element : elements) {
            String href = element.attr("href");
            String title = element.text();
            System.out.println(title + ":" + href);
        }
    }

    static {
        cookieStore = new BasicCookieStore();
        cookieValue = new HashMap<>();
    }

    public void setCookieInClient() {

    }

    private static HttpClient getCookieAfterLogin(String url, String username, String password) throws Exception{
        // 第一次的Client不会带有Cookie
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        HttpPost post = new HttpPost(url);
        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("username", username));
        params.add(new BasicNameValuePair("password", password));
        post.setHeader(
                "User-Agent",
                "'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.87 Safari/537.36'");
        post.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
        HttpResponse response = httpClient.execute(post);

        Integer statusCode = response.getStatusLine().getStatusCode();
        System.out.println("after login , the result is " + statusCode);
        if (statusCode == HttpStatus.SC_OK) { // 成功
            HttpEntity entity = response.getEntity();
            String htmlCode = EntityUtils.toString(entity, DEFAULT_ENCODING);
            // TODO 用流来处理过长的HTML源码
            Document document = Jsoup.parse(htmlCode);
            Elements elements = document.getElementsByTag("a");// 获取所有a标签的Element
//            printfElement(elements);
        }

        Header[] AllHeaders = response.getHeaders("Set-Cookie");
        for (Header header : AllHeaders) {
            String headerValue = header.getValue();
            cookieStore.addCookie(generateCookies(headerValue, parseHeaderValue(headerValue)));
        }
        return HttpClientBuilder.create()
                .setDefaultCookieStore(cookieStore)
                .build();
    }

    /**
     * 展示所有元素
     */
    private static void printfElement(Elements elements) {
        for (Element element : elements) {
            String href = element.attr("href");
            String title = element.text();
            System.out.println(title + ":" + href);
        }
    }

    /**
     * 处理cookie
     */
    private static String parseHeaderValue(String headerValue) {
        return headerValue.substring(0, headerValue.indexOf(";")).split("=")[0];
    }



    /**
     * TODO 将Expiration 从String 转为Date
     */
    private static Date getDateFromString(String timeStr) throws Exception{
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEE, dd-MMM-yyyy HH:mm:ss 'GMT'", Locale.US);
        Date date = simpleDateFormat.parse(timeStr);
        return date;
    }

    /**
     * 从服务器获取ll 和 bid后生成所有需要的cookie
     * type就是 ll / bid
     */
    private static BasicClientCookie generateCookies(String headerValue, String type) throws Exception {
        String[] values = headerValue.split(";");
        BasicClientCookie cookie = null;
        Map<String, String> temp = new HashMap<>();
        for (String pairs : values) {
            pairs = pairs.trim();
            String[] pair = pairs.split("=");
            String key = pair[0].toLowerCase();
            String value = pair[1];
            temp.put(key, value);
            System.out.println("key is " + key + " : value is " + value);
        }
        cookie = new BasicClientCookie(type, temp.get(type));
        cookie.setDomain(temp.get("domain"));
        cookie.setPath(temp.get("path"));
        cookie.setExpiryDate(getDateFromString(temp.get("expires")));


        System.out.println(cookie.toString());
        return cookie;
    }

}


