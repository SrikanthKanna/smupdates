import org.apache.http.Header;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicHeader;
import org.jsoup.Jsoup;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class ClientUpdates {
    public static void main(String[] args) throws IOException {
        RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(30 * 1000).setSocketTimeout(30 * 1000).build();
        HttpClient client = HttpClientBuilder.create().setDefaultRequestConfig(requestConfig).build();
        String url = "https://www.nseindia.com/live_market/dynaContent/live_watch/option_chain/optionKeys.jsp?symbol=HDFC";
        HttpGet get = new HttpGet(url);
        Header header = new BasicHeader("content-type", "text/html");
        HttpResponse res = client.execute(get);
        System.out.println(res);

        InputStream inputStream = res.getEntity().getContent();
        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
        StringBuffer buffer = new StringBuffer();
        String line = null;
        while ((line = br.readLine()) != null) {
            buffer.append(line);
        }
        String s = buffer.toString();
        org.jsoup.nodes.Document document = Jsoup.parse(s);
        System.out.println(s);

        String StockQuoteWithTime=document.select("table").get(0).select("tr").select("td").get(1).text();
        String[] s1 = StockQuoteWithTime.replace("Underlying Stock: ", "").replace("As on","").split(" ");

        int rows=document.select("table").get(2).select("tr").size();

        String[] headers = document.select("table").get(2).select("tr").get(1).text().split(" ");

        int rowsize = document.select("table").get(2).select("tr").size();
        int rowIterator_count = 2;

        while (rowIterator_count<rowsize-2){
           double strikePrice = Double.parseDouble(document.select("table").get(2).select("tr").get(rowIterator_count).select("td").get(11).text());
        }



        System.out.println(headers);

    }
}
