import org.apache.http.Header;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicHttpRequest;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Test {
    public static void main(String[] args) throws ParseException, IOException {
////        Date myDate = new Date();
////        System.out.println(myDate);
////        System.out.println(new SimpleDateFormat("MMM dd,").format(new Date()).toString());
////        System.out.println(new SimpleDateFormat("yyyy-MM-dd").format(myDate));
////        System.out.println(myDate);
////        System.out.println(new SimpleDateFormat("HH:mm").format(new Date()).toString());
//        Date parseDate = new SimpleDateFormat("yyyy-MM-dd").parse(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
//        System.out.println(parseDate.getTime());
//        long startTime = parseDate.getTime()+30600000;
//        System.out.println(new Date(startTime));
//        long endTime = parseDate.getTime()+55800000;
//        System.out.println(new Date(endTime));



        RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(30 * 1000).setSocketTimeout(30 * 1000).build();
        HttpClient client = HttpClientBuilder.create().setDefaultRequestConfig(requestConfig).build();
        String url = "http://10.70.82.42:8080/service/shipping/reprintPackslipAndInvoice";
        HttpPost get = new HttpPost(url);
        HttpRequest request = new BasicHttpRequest("content-type", "application/json");
        request.addHeader("orderCode","SLP1549542384");
        request.addHeader("sellerCode","5af6d4");
        request.addHeader("responseProtocol","PROTOCOL_JSON");
        request.addHeader("requestProtocol","PROTOCOL_JSON");
        get.setHeaders(request.getAllHeaders());
        HttpResponse res = client.execute(get);
        System.out.println(res);

    }
}
