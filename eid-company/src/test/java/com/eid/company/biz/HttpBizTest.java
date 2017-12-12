package com.eid.company.biz;

import com.github.kevinsawicki.http.HttpRequest;
import org.junit.Test;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 描述类的作用
 * Created by:ruben Date:2017/3/22 Time:上午11:19
 */
public class HttpBizTest {

    @Test
    public void getTest() {
        Long start = System.currentTimeMillis();
        try {
            HttpRequest httpRequest = HttpRequest.get("https://github.com").connectTimeout(5000).readTimeout(5000);
            int resCode = httpRequest.code();
            String response = httpRequest.body();

            System.out.println("Response code was:" + resCode);
            System.out.println("Response was:" + response);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        Long end = System.currentTimeMillis();
        System.out.println(end - start);

    }

    @Test
    public void sendTest() {
        HttpRequest request = HttpRequest.post("http://192.168.0.60:8100/eid/feedback");
        request.part("mobile", "1");
        request.part("title", "2");
        request.part("desc", "3");
        request.part("logo", new File("/Users/ruben/Downloads/1.jpg"));
        if (request.ok())
            System.out.println("Status was updated");


    }

    @Test
    public void domainTest() {
        String regEx = "(http|https):\\/\\/[\\w\\-_]+(\\.[\\w\\-_]+)+([\\w\\-\\.,@?^=%&amp;:/~\\+#]*[\\w\\-\\@?^=%&amp;/~\\+#])?";
        Pattern pattern = Pattern.compile(regEx);
        Matcher matcher = pattern.matcher("https://chneid.com");
        System.out.println(matcher.matches());
    }

}
