import com.smupdates.model.OptionData;
import com.smupdates.model.OptionDetails;
import com.smupdates.model.StockOptionDerivativeData;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicHeader;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;

public class OptionDataListTest {
    private static SimpleDateFormat format = new SimpleDateFormat("MMM dd, yyyy hh:mm:ss Z");

    public static void main(String[] args) throws IOException {
        format.setTimeZone(TimeZone.getTimeZone("IST"));
        RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(30 * 1000).setSocketTimeout(30 * 1000).build();
        HttpClient client = HttpClientBuilder.create().setDefaultRequestConfig(requestConfig).build();
        String url = "https://www.nseindia.com/live_market/dynaContent/live_watch/option_chain/optionKeys.jsp?symbol=sbin";
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

        ///srchpnl() opttbldata(data) wrapper_btm(first table to get time stamp)


        //stock with value and  time stamp
        String instrumentWithValue = document.getElementById("wrapper_btm").select("table").get(0).select("tr").get(0).select("td").get(1).select("span").get(0).select("b").text();
        String timeStamp = document.getElementById("wrapper_btm").select("table").get(0).select("tr").get(0).select("td").get(1).select("span").get(1).text().replace("As on ","");
        String symbol = instrumentWithValue.split(" ")[0];
        Double value = Double.parseDouble(instrumentWithValue.split(" ")[1].replace(",",""));

        StockOptionDerivativeData optionDerivativeData=null;
        try {
             optionDerivativeData = new StockOptionDerivativeData(symbol,value,format.parse(timeStamp));
        } catch (Exception e) {
            e.printStackTrace();
        }

        // get Expiry Date
        String expiryDate = document.getElementsByClass("srchpnl").select("select").get(1).select("option").get(1).text();
        optionDerivativeData.setExpiry(expiryDate);

        // get Data
        Elements rowElement = document.getElementsByClass("opttbldata").select("table").select("tr");

        System.out.println("stock ltp"+value);
        double lowRange = value-(value*0.08);
        double higherRange = value+value*.08;
        List<OptionData> options = new ArrayList<>();
        int rows = rowElement.size();
        for(int i=2;i<rows-1;i++){
            Double strikePrice = Double.parseDouble(rowElement.get(i).select("td").get(11).text());
            if(strikePrice <=higherRange && lowRange <= strikePrice ){
                System.out.println(strikePrice+" "+lowRange+" "+higherRange);

                OptionData optionData = populateOptionData(rowElement.get(i));
                optionData.setStrikePrice(strikePrice);
                options.add(optionData);
            }else {
                continue;
            }
        }

        optionDerivativeData.setOptionDataList(options);
        System.out.println(options.toString());
    }

    private static OptionData populateOptionData(Element row) {
        OptionData optionData = new OptionData();

        Elements columnEle = row.select("td");
        OptionDetails call = new OptionDetails();
        OptionDetails put = new OptionDetails();

        call.setOi(getFilterValue(columnEle.get(1).text()));
        call.setChnginOI(getFilterValue(columnEle.get(2).text()));
        call.setVolume(getFilterValue(columnEle.get(3).text()));
        call.setImpliedVoltality(getFilterValue(columnEle.get(4).text()));
        call.setLtp(getFilterValue(columnEle.get(5).text()));
        call.setNetChng(getFilterValue(columnEle.get(6).text()));
        call.setBidQty(getFilterValue(columnEle.get(7).text()));
        call.setBidPrice(getFilterValue(columnEle.get(8).text()));
        call.setAskPrice(getFilterValue(columnEle.get(9).text()));
        call.setAskQty(getFilterValue(columnEle.get(10).text()));
        put.setOi(getFilterValue(columnEle.get(21).text()));
        put.setChnginOI(getFilterValue(columnEle.get(20).text()));
        put.setVolume(getFilterValue(columnEle.get(19).text()));
        put.setImpliedVoltality(getFilterValue(columnEle.get(18).text()));
        put.setLtp(getFilterValue(columnEle.get(17).text()));
        put.setNetChng(getFilterValue(columnEle.get(16).text()));
        put.setBidQty(getFilterValue(columnEle.get(12).text()));
        put.setBidPrice(getFilterValue(columnEle.get(13).text()));
        put.setAskPrice(getFilterValue(columnEle.get(14).text()));
        put.setAskQty(getFilterValue(columnEle.get(15).text()));

        optionData.setCall(call);
        optionData.setPut(put);
        return optionData;
    }

    private static Double getFilterValue(String text) {
        if(text=="") return 0.0;
        if(text.equals("-")) return 0.0;
        return Double.parseDouble(text.replace(",",""));
    }
}
