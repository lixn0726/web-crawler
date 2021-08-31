package com.lixnstudy.webcrawler.testCode;


import com.lixnstudy.webcrawler.util.ParseHTMLUtil;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


/**
 * @author lixn
 * @ClassName MyTest
 * @Description TODO
 * @create 2021/8/30 3:08 下午
 **/
public class MyTest {
    // TODO 保持登陆所需的Cookie
    private static final String cookieValue = "ll=\"118281\"; bid=xXOFVbD9elY; dbcl2=\"245370804:7ULN5r+hQKc\"; ck=M8Af";

    public static void main(String[] args) throws Exception{
        String top250Url = "https://movie.douban.com/top250";
        String detailsUrl = "https://movie.douban.com/subject/1291546/";
        HttpClient client = HttpClientBuilder.create()
                .build();
        HttpGet get = new HttpGet(detailsUrl);
        get.setHeader("Cookie",cookieValue);
        get.setHeader("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/92.0.4515.159 Safari/537.36");
        HttpResponse response = client.execute(get);
        String html = EntityUtils.toString(response.getEntity(), "UTF-8");
        Document document = Jsoup.parse(html);
        Elements pics = document.select("div.pic>a");// 图片链接
        Elements picDescription = document.select("div.pic>a>img");// 图片描述，其中的alt属性就是片名
        System.out.println(pics.size());
        Elements infos = document.select("div.hd");
        int count = 1;
//        for (Element pic : pics) {
//            if (count == 5) {
//                break;
//            }
//            String picLink = pic.attr("href");
//            System.out.println(picLink);
//        }


        System.out.println(response.getStatusLine().getStatusCode());
//        System.out.println(response.getStatusLine().getStatusCode());
//        System.out.println(EntityUtils.toString(response.getEntity(), "UTF-8")); 这一句会导致流没有关闭，意思就是这个方法是通过流来处理Entity的

    }

    /**
     * 创建Get请求进入详情页
     */
    private static HttpGet generateGetRequest(String url) {
        HttpGet get = new HttpGet(url);
        get.setHeader("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/92.0.4515.159 Safari/537.36");
        get.setHeader("Cookie", "");
        return get;
    }


}



