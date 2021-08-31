package com.lixnstudy.webcrawler.util;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * @author lixn
 * @ClassName ParseHTMLUtil
 * @Description TODO
 * @create 2021/8/30 7:38 下午
 **/
public class ParseHTMLUtil {
    public static void parseUrlInHtml(String html) {
        Document document = Jsoup.parse(html);
        Element content = document.getElementById("content");
        Elements items = content.getElementsByTag("li");
        for (Element element : items) {
            System.out.println(element.getElementsByAttribute("item"));
        }
    }
}
