package com.lixnstudy.webcrawler.testCode;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;


/**
 * @author lixn
 * @ClassName ArticleSpider
 * @Description TODO 一个Demo，读取一个小说网站，然后将小说内容拉取下来，并拼装成文件，然后保存到本地
 * @create 2021/8/26 9:46 上午
 **/
public class NovelDemoFromInternet {
    /**
     * 保存地址
     */
    private String path;

    /**
     * 启动
     */
    public void start(String url) {
        try {
            Document document = Jsoup.connect(url).get();

            // 拿到列表第一个链接
            String listUrl = document.select("#list>dl>dd>a").attr("abs:href");
            System.out.println(listUrl);
            // 获取文本内容
            String fileName = document.select("h1").text();

            // 文件保存地址，这里根据书名作为文件夹
            path = "/Users/aihuishou/" + fileName;
            each(listUrl);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 便利获取每一章的数据
     */
    private void each(String url) {
        try {
            Document document = Jsoup.connect(url).get();
            // 拿到小说信息并且进行转换处理
            Element element = document.getElementById("content");
            // 符号处理
            String content = element.text().replaceAll("   ", "\n").replaceAll("。", "。\n");
            String title = document.getElementsByTag("h1").text();
            // 下一章地址
            String next = document.getElementsByClass("bottem1").get(0).child(3).attr("abs:href");
            // 创建文件
            File file = createFile(title);
            mergeBook(file, content);
            // 判断是否到最后一章
            if (next.indexOf("html") != -1) {
                // 做一个爬取限制
                Thread.sleep(5000);
                System.out.println("休息5秒继续爬");
                each(next);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 创建文件
     */
    public File createFile(String fileName) {
        // 创建空白文件夹：networkNovel
        File file = new File(path + "/" + fileName + ".txt");
        try {
            // 获取父目录
            File fileParent = file.getParentFile();
            if (!fileParent.exists()) {
                fileParent.mkdirs();
            }
            // 创建文件
            if (!file.exists()) {
                file.createNewFile();
            }
        } catch (Exception e) {
            file = null;
            System.out.println("新建文件的时候出错了");
            e.printStackTrace();
        }
        return file;
    }

    /**
     * 写入文本
     */
    public void mergeBook(File file, String content) {
        try {
            FileWriter resultFile = new FileWriter(file, true);// true，则追加写入
            PrintWriter myFile = new PrintWriter(resultFile);
            // 写入
            myFile.println(content);
            myFile.println("\n");

            myFile.close();
            resultFile.close();
        } catch (Exception e) {
            System.out.println("写入的时候出错了");
            e.printStackTrace();
        }
    }
}
