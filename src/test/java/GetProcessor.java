import com.sun.xml.internal.ws.client.sei.ResponseBuilder;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicHeader;

import java.io.IOException;

public class  GetProcessor {

    public  static HttpResponse execute(String url) throws IOException {

            RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(30 * 1000).setSocketTimeout(30 * 1000).build();
            HttpClient client = HttpClientBuilder.create().setDefaultRequestConfig(requestConfig).build();
            HttpGet get = new HttpGet(url);
            Header header = new BasicHeader("content-type", "text/html");
            HttpResponse res = client.execute(get);
           return res;
    }

}
