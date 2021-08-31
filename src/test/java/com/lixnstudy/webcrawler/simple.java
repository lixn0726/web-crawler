package com.lixnstudy.webcrawler;

import com.lixnstudy.webcrawler.testCode.CleanCode;
import org.junit.jupiter.api.Test;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

/**
 * @author lixn
 * @ClassName simple
 * @Description TODO
 * @create 2021/8/27 9:37 上午
 **/
public class simple {
    @Test
    public void test() {
        Random random = new Random();
        System.out.println(random.nextInt(5));

        // parse string to date ===============================================
//        String timeStr =                                                  "Tue, 30-Aug-2022 01:38:35 GMT";
//        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEE, dd-MMM-yyyy HH:mm:ss 'GMT'", Locale.US);
//        try {
//            Date d = simpleDateFormat.parse(timeStr);System.out.println(simpleDateFormat.format(d));
//        } catch (Exception e) {
//            System.out.println("格式错误");
//            e.printStackTrace();
//        }
//        Date date = new Date();
//        System.out.println(simpleDateFormat.format(date));




//        String[] s = sr.split(";");
//        for (String s1 : s) {
//            s1 = s1.trim();
//            String[] pair = s1.split("=");
//            String key = pair[0];
//            String value = pair[1];
//            System.out.println("str is " + s1 + ", key is " + key + ", value is " + value);
//        }
//        Date date = new Date("30-Aug-2022 01:38:35 GMT");
//        System.out.println(date);
    }
}
