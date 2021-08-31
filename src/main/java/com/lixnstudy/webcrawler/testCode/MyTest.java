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
    public static void main(String[] args) throws Exception{
        String url = "https://movie.douban.com/top250";
        String anotherUrl = "https://movie.douban.com/subject/1291546/";
        String cookieValue = "ll=\"118281\"; bid=xXOFVbD9elY; dbcl2=\"245370804:7ULN5r+hQKc\"; ck=M8Af";
        HttpClient client = HttpClientBuilder.create()
                .build();
        HttpGet get = new HttpGet(anotherUrl);
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
//            HttpGet httpGet = new HttpGet(picLink);
//            System.out.println(count + " time ready to execute get request" );
//            httpGet.setHeader("Cookie",cookieValue);
//            HttpResponse response1 = client.execute(httpGet);
//            httpGet.setHeader("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/92.0.4515.159 Safari/537.36");
//            System.out.println(count + ": result is " + response1.getStatusLine().getStatusCode());
////            String htmlCode = EntityUtils.toString(client.execute(httpGet).getEntity(), "UTF-8");
////            Document doc = Jsoup.parse(htmlCode);
////            Elements elements = doc.select("span.pl");
////            for (Element element : elements) {
////                String director = element.text();
////                System.out.println("director is " + director);
////            }
//            count++;
//        }


        System.out.println(response.getStatusLine().getStatusCode());
//        System.out.println(response.getStatusLine().getStatusCode());
//        System.out.println(EntityUtils.toString(response.getEntity(), "UTF-8"));

    }


}
