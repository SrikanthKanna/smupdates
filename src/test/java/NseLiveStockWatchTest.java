import com.fasterxml.jackson.databind.ObjectMapper;
import com.smupdates.dto.EquityDataDto;
import com.smupdates.dto.IndexDataDto;
import com.smupdates.dto.LiveEquityFeedDto;
import com.smupdates.model.EquityData;
import com.smupdates.model.IndexData;
import com.smupdates.model.LiveEquityFeed;
import org.apache.http.HttpResponse;

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

public class NseLiveStockWatchTest {

    //time format Feb 23, 2018 15:59:59
    private  static DateFormat formatter =  new SimpleDateFormat("MMM dd, yyyy HH:mm:ss");



    public static void main(String[] args) throws IOException {
        InputStream resourceAsStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("allIndicesTypes.csv");
        BufferedReader br1 = new BufferedReader(new InputStreamReader(resourceAsStream));
        String line = null;
        List<String> indexNames = new ArrayList<>();
        while ((line=br1.readLine())!=null){
            indexNames.add(line);
        }
        br1.close();
        resourceAsStream.close();
        for(String indexUrl : indexNames){

            process("https://www.nseindia.com/live_market/dynaContent/live_watch/stock_watch/"+indexUrl+"StockWatch.json");
        }
    }

    private static void process(String url) throws IOException {
        System.out.println("***"+url+"****");
        String line = null;
        HttpResponse res = GetProcessor.execute(url);
        InputStream inputStream = res.getEntity().getContent();
        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
        StringBuffer buffer = new StringBuffer();
        line = null;
        while ((line = br.readLine()) != null) {
            buffer.append(line);
        }
        String s = buffer.toString();
        br.close();
        inputStream.close();
        ObjectMapper mapper = new ObjectMapper();
        LiveEquityFeed liveEquityFeed = mapper.readValue(s, LiveEquityFeed.class);
        System.out.println(liveEquityFeed);
        LiveEquityFeedDto dto = populateLiveFeed(liveEquityFeed);
        System.out.println(dto.toString());
    }

    private static LiveEquityFeedDto populateLiveFeed(LiveEquityFeed lef) {
        LiveEquityFeedDto dto = new LiveEquityFeedDto();
        dto.setAdvances(lef.getAdvances());
        dto.setDeclines(lef.getDeclines());
        dto.setUnchanged(lef.getUnchanged());
        try {
            dto.setTime(formatter.parse(lef.getTime()));
        } catch (ParseException e) {
            dto.setTime(new Date(System.currentTimeMillis()));
        }
        dto.setTrdValueSum(Double.parseDouble(lef.getTrdValueSum().replace(",","")));
        dto.setTrdValueSumMil(Double.parseDouble(lef.getTrdValueSumMil().replace(",","")));
        dto.setTrdVolumesum(Double.parseDouble(lef.getTrdValueSum().replace(",","")));
        dto.setTrdVolumesumMil(Double.parseDouble(lef.getTrdValueSumMil().replace(",","")));

        // populate index data
        List<IndexData> indexDataList = lef.getLatestData();
        List<IndexDataDto> indexDataDtoList = new ArrayList<>();

        for(IndexData indexData :indexDataList){
            IndexDataDto indexDataDto = new IndexDataDto();
            indexDataDto.setIndexName(indexData.getIndexName());
            indexDataDto.setLtp(Double.parseDouble(indexData.getLtp().replace(",","")));
            indexDataDto.setOpen(Double.parseDouble(indexData.getOpen().replace(",","")));
            indexDataDto.setLow(Double.parseDouble(indexData.getLow().replace(",","")));
            indexDataDto.setHigh(Double.parseDouble(indexData.getHigh().replace(",","")));
            indexDataDto.setCh(Double.parseDouble(indexData.getCh().replace(",","")));
            indexDataDto.setPer(Double.parseDouble(indexData.getPer().replace(",","")));
            indexDataDto.setmCls(Double.parseDouble(indexData.getmCls().replace(",","")));
            indexDataDto.setyCls(Double.parseDouble(indexData.getyCls().replace(",","")));
            indexDataDto.setyHigh(Double.parseDouble(indexData.getyHigh().replace(",","")));
            indexDataDto.setyLow(Double.parseDouble(indexData.getyLow().replace(",","")));
            indexDataDtoList.add(indexDataDto);
        }
        dto.setLatestData(indexDataDtoList);
        // stock Data feed
        List<EquityData> equityDataList = lef.getData();
        List<EquityDataDto> equityDataDtos = new ArrayList<>();
        for(EquityData equityData : equityDataList){
            EquityDataDto equityDataDto = new EquityDataDto();
            equityDataDto.setSymbol(equityData.getSymbol());
            equityDataDto.setcAct(equityData.getcAct());
            equityDataDto.setLtP(Double.parseDouble(equityData.getLtP().replace(",","")));
            equityDataDto.setOpen(Double.parseDouble(equityData.getOpen().replace(",","")));
            equityDataDto.setLow(Double.parseDouble(equityData.getLow().replace(",","")));
            equityDataDto.setHigh(Double.parseDouble(equityData.getHigh().replace(",","")));
            equityDataDto.setDayEndClose(Double.parseDouble(equityData.getDayEndClose().replace(",","")));
            equityDataDto.setPreviousClose(Double.parseDouble(equityData.getPreviousClose().replace(",","")));
            equityDataDto.setPtsC(Double.parseDouble(equityData.getPtsC().replace(",","")));
            equityDataDto.setPer(Double.parseDouble(equityData.getPer().replace(",","")));
            equityDataDto.setTrdVol(Double.parseDouble(equityData.getTrdVol().replace(",","")));
            equityDataDto.setNtP(Double.parseDouble(equityData.getNtP().replace(",","")));
            equityDataDto.setWkhi(Double.parseDouble(equityData.getWkhi().replace(",","")));
            equityDataDto.setWklo(Double.parseDouble(equityData.getWklo().replace(",","")));
            equityDataDto.setIislPtsChange(Double.parseDouble(equityData.getIislPtsChange().replace(",","")));
            equityDataDto.setIislPercChange(Double.parseDouble(equityData.getIislPercChange().replace(",","")));
            equityDataDto.setmPC(Double.parseDouble(getFilterData(equityData.getmPC())));
            equityDataDto.setyPC(Double.parseDouble(getFilterData(equityData.getyPC())));
            equityDataDtos.add(equityDataDto);
        }
        dto.setData(equityDataDtos);
        return dto;
    }

    private static String getFilterData(String s) {
       s= s.replace(",","");
        if(s.equalsIgnoreCase("-")) return "0";
        return s;
    }

}
