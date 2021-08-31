package com.lixnstudy.webcrawler.testCode;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;



/**
 * @author lixn
 * @ClassName HttpClientDemo
 * @Description TODO 一个Demo，主要做流程的测试
 * @create 2021/8/26 11:37 上午
 **/
public class HttpClientDemo {
// 简化请求URL的创建和修改
//        URI uri = new URIBuilder()
//                .setScheme("http")
//                .setHost("www.baidu.com")
//                .setPath("")
////                .setParameter()
//                .build();
// Cookie管理工具
//    public class SpiderHttpUtils {
//        private static CloseableHttpClient httpClient;
//
//        private static BasicCookieStore cookieStore;
//
//        static {
//            cookieStore = new BasicCookieStore();
//            httpClient = HttpClients.custom()
//                    .setDefaultCookieStore(cookieStore)
//                    .build();
//        }
//    }
    public static void main(String[] args) {
        String html = getHtmlByUrl("http://douban.com");
        if (html != null && !"".equals(html)) {
            Document document = Jsoup.parse(html);
//            Elements linksElements = document.select("section#main>div#content>article#post>header.entry-header>h3.entry-title>a");
            /* 以上cssQuery语句的意思是：
            找id为“main”的section里面
            id为“content”的div里面
            id为“post”的article里面
            class为“entry-header”的header里面
            class为“entry-title”的h3里面a标签
             */
            Elements linksElements = document.getElementsByTag("a");
            for (Element element : linksElements) {
                String href = element.attr("href");
                String title = element.text();
                System.out.println(title + ":" + href);

            }
        }
    }

    /**
     * 使用HttpClient，获取网页的源代码
     * @param url
     * @return
     */
    public static String getHtmlByUrl(String url) {
        String html = null;
        // 创建Cookie管理工具
        BasicCookieStore cookieStore = new BasicCookieStore();
        // 伪造UA，防止被某些网站拦截IP，这里用的是本机的Chrome的UA
        String fakeUA = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/92.0.4515.159 Safari/537.36";
//        String fakeUA = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/92.0.4515.159 Safari/537.36 Edg/92.0.902.78";
//        String fakeUA = "";
        // 创建httpClient对象
        HttpClient httpClient = HttpClientBuilder.create()
                .setUserAgent(fakeUA)
                .build();
        // get方法请求URL
        HttpGet httpGet = new HttpGet(url);
        try {
            // 得到response
            HttpResponse response = httpClient.execute(httpGet);
            int resStatus = response.getStatusLine().getStatusCode();
            // 返回码，200正常，其他就不对
            if (resStatus == HttpStatus.SC_OK) {
                // 获得相应的实体
                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    // 获得html源码
                    html = EntityUtils.toString(entity, "UTF-8");
//                    System.out.println(html);
                }
            }
        } catch (Exception e) {
            System.out.println("访问[" + url + "]出错");
            e.printStackTrace();
        } finally {
            httpClient.getConnectionManager().shutdown();
        }
        return html;
    }

    /**
     * 保存cookie
     */

}
