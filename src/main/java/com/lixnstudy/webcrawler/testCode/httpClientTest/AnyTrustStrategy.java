package com.lixnstudy.webcrawler.testCode.httpClientTest;

import org.apache.http.ssl.TrustStrategy;

import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

/**
 * @author lixn
 * @ClassName AnyTrustStrategy
 * @Description TODO
 * @create 2021/8/30 6:24 下午
 **/
public class AnyTrustStrategy implements TrustStrategy {

    // 建立一个信任任何密钥的策略
    @Override
    public boolean isTrusted(X509Certificate[] chain, String authType) throws CertificateException {
        return true;
    }

}
