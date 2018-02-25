import com.smupdates.model.GloblaIndexDetails;
import org.apache.http.HttpResponse;
import org.jsoup.Jsoup;
import org.jsoup.select.Elements;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

public class GlobalEquityInfoTest {
    private  static DateFormat formatter =  new SimpleDateFormat(" EEE MMM dd yyyy HH:mm");

    public GlobalEquityInfoTest() {
        formatter.setTimeZone(TimeZone.getTimeZone("EST"));
    }

    public static void main(String[] args) throws IOException {
        HttpResponse res = GetProcessor.execute("https://liveindex.org/Futures");
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

        Elements select = document.getElementsByClass("index_table").select("table").get(0).select("tr");
        System.out.println("");
        int rows =select.size();
        List<GloblaIndexDetails> detailsList = new ArrayList<>();
        for(int i=1;i<rows;i=i+2){
            Elements columnElement = select.get(i).select("td");
            int columns = columnElement.size();
            GloblaIndexDetails details = new GloblaIndexDetails();
            details.setSymbol(columnElement.get(0).text());
            details.setLtp(Double.parseDouble(columnElement.get(2).text().replace(",","").replace("%","")));
            details.setChg(Double.parseDouble(columnElement.get(3).text().replace(",","").replace("%","")));
            details.setPerChg(Double.parseDouble(columnElement.get(4).text().replace(",","").replace("%","")));
            details.setHigh(Double.parseDouble(columnElement.get(5).text().replace(",","").replace("%","")));
            details.setLow(Double.parseDouble(columnElement.get(6).text().replace(",","").replace("%","")));

            columnElement = select.get(i+1).select("td");
            details.setStatus(columnElement.get(0).select("td").get(0).text().split(" ")[0].toLowerCase());
            String[] timeWithCountry = columnElement.get(1).select("td").get(0).text().split(" Time :");
            try {
                formatter.setTimeZone(getTimezone(timeWithCountry[0]));
                Date istDate = formatter.parse(timeWithCountry[1]);
                details.setTime(istDate);
                System.out.println("time With country"+timeWithCountry[0]+timeWithCountry[1]);
                System.out.println("IST:"+istDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            detailsList.add(details);
        }
        System.out.println("Done");

    }

    private static TimeZone getTimezone(String county) {
        if(county.equalsIgnoreCase("us"))
            return TimeZone.getTimeZone("EST");
        if(county.equalsIgnoreCase("uk"))
            return TimeZone.getTimeZone("GMT");
        if(county.equalsIgnoreCase("france"))
            return TimeZone.getTimeZone("CET");
        if(county.equalsIgnoreCase("germany"))
            return TimeZone.getTimeZone("CET");
        if(county.equalsIgnoreCase("euro"))
            return TimeZone.getTimeZone("GMT");
        if(county.equalsIgnoreCase("hong kong"))
            return TimeZone.getTimeZone("Asia/Singapore");
        if(county.equalsIgnoreCase("japan"))
            return TimeZone.getTimeZone("JST");
        if(county.equalsIgnoreCase("Singapore"))
            return TimeZone.getTimeZone("Asia/Singapore");
        return TimeZone.getTimeZone("IST");
    }
}
