//package com.lixnstudy.webcrawler.testCode;
//
//import org.apache.http.*;
//import org.apache.http.client.CookieStore;
//import org.apache.http.client.entity.UrlEncodedFormEntity;
//import org.apache.http.client.methods.HttpGet;
//import org.apache.http.client.methods.HttpPost;
//import org.apache.http.impl.client.BasicCookieStore;
//import org.apache.http.impl.client.CloseableHttpClient;
//import org.apache.http.impl.client.HttpClientBuilder;
//import org.apache.http.message.BasicNameValuePair;
//import org.apache.http.util.EntityUtils;
//
//import java.io.IOException;
//import java.util.*;
//
///**
// * @author lixn
// * @ClassName test
// * @Description TODO
// * @create 2021/8/27 11:28 上午
// **/
//public class test {
//    static CookieStore cookieStore = null;
//    public void testLogin() {
//        String loginUrl = "https://www.douban.com/";
//        String testUrl = "https://www.douban.com/explore/";
//        HttpClientBuilder builder = HttpClientBuilder.create();
//        CloseableHttpClient client = builder.build();
//        //--------------------------------------------------------------------------------
//        HttpPost post = new HttpPost(loginUrl);
//        Map<String, String> parameterMap = new HashMap();
//        parameterMap.put("username", "username");
//        parameterMap.put("password", "lx990726");
//        //--------------------------------------------------------------------------------
//        UrlEncodedFormEntity postEntity = new UrlEncodedFormEntity(getParam(parameterMap), "UTF-8");
//        post.setEntity(postEntity);
//        try {
//            // --执行post请求
//            HttpResponse response = client.execute(post);
//            String location = response.getFirstHeader("Location").getValue();
//            printResponse(response);
//            // --执行get请求
//            HttpGet get = new HttpGet(testUrl);
//            HttpResponse httpResponse = client.execute(get);
//            printResponse(httpResponse);
//            // cookie store
//            setCookieStore(response);
//            // context
//            setContext();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    public static List<NameValuePair> getParam(Map<String, String> parameterMap) {
//        List<NameValuePair> param = new ArrayList<>();
//        for (Map.Entry<String, String> entry : parameterMap.entrySet()) {
//            param.add(new BasicNameValuePair(entry.getKey(), entry.getValue());
//        }
//        return param;
//    }
//
//    public static void printResponse(HttpResponse response) throws ParseException, IOException {
//        HttpEntity entity = response.getEntity();
//        System.out.println("status : " + response.getStatusLine());
//        System.out.println("headers :");
//        HeaderIterator iterator = response.headerIterator();
//        while (iterator.hasNext()) {
//            System.out.println("\t" + iterator.next());
//        }
//        if (entity != null) {
//            String html = EntityUtils.toString(entity, "UTF-8");
//            System.out.println("response length :" + html.length());
//            System.out.println("response content : " + html.replace("\r\n", ""));
//        }
//    }
//
//    public void testCookieStore() throws Exception {
//
//    }
//
//    public static void setCookieStore(HttpResponse response) {
//        cookieStore = new BasicCookieStore();
//
//    }
//}
