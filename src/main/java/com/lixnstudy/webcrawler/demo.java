package com.lixnstudy.webcrawler;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.jsoup.select.Selector;

import java.io.File;


/**
 * @author lixn
 * @ClassName demo
 * @Description TODO JSOUP用法
 * @create 2021/8/25 5:29 下午
 **/
public class demo {
    public static void main(String[] args) throws Exception {
        Document document = Jsoup.connect("http://www.baidu.com/").get();
        /// Jsoup.connect()返回一个 org.jsoup.Connection对象
        /// 在Connection对象中，可以执行get和post来执行请求。也可以使用Connection对象来设置一些请求信息
        /// 比如：头信息，cookie，请求等待时间，代理等等，模拟浏览器行为
        Document document1 = Jsoup.connect("http://www.baidu.com/")
                .data("wd", "我")
                .userAgent("Mozilla")
                .cookie("auth", "token")
                .timeout(3000)
                .post();
        /// 从文件中获取html来解析
        Document path = Jsoup.parse(new File("path"), "utf-8");
        /// 直接从字符串中获得html来解析
        Document text = Jsoup.parse("${html uri}");
        /// 通过Document来获取指定节点 Element对象
        /// 通过方法来查找指定的节点Element
        // ******************************************** 查找指定节点Element ********************************************** //
        // 元素Id
        Element element = document.getElementById("element id");
        // 标签名
        Elements elements = document.getElementsByTag("tagName");
        // 类名
        Elements elements1 = document.getElementsByClass("className");
        // 属性名
        Elements elements2 = document.getElementsByAttribute("key");
        // 指定属性名称和属性值
        Elements elements3 = document.getElementsByAttributeValue("key", "value");
        // 获取所有节点元素
        Elements elements4 = document.getAllElements();
        // ************************************************************************************************************* //

    }

    public class select {
        /// 通过css或jQuery的选择器来查找元素
        public Elements select(String cssQuery) {
            // 选择好一个Element来选择元素？
            Element element = null;
            return Selector.select(cssQuery, element);
        }

        File input = new File("/tmp/input.html");
    }
}
