package com.smupdates;

import com.smupdates.model.AnnonuncementPage;
import com.smupdates.model.response.Annonuncements;
import com.smupdates.model.response.AnnonuncementsResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by srikanth on 16/10/17.
 */
public class AppStart {
    private static final Logger LOG = LoggerFactory.getLogger(AppStart.class);
    public static final int LIMIT = 20;
    public NSEService announcementService = new NSEService();
    public  List<String> stocks = new ArrayList<>();
    public List<String> alreadySentAnnouncements = new ArrayList<>();

    public void execute() throws ParseException {
        populateStockNamesAndAlreadyrReadedAnnouncements();
        LinkedHashSet<Annonuncements> announcementStatusMap = new LinkedHashSet<>();
        String prevDate=null;
        FileWriter fw =null;
        boolean readWholeNseAnnouncemtsOnce = false;
        while (true && !stocks.isEmpty()) {
            String currentDateString = new SimpleDateFormat("MMM dd,").format(new Date()).toString();
            if(prevDate==null){
                prevDate=currentDateString;
                try {
                    Date parseDate = new SimpleDateFormat("yyyy-MM-dd").parse(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
                    announcementService.setDate(parseDate.getTime());
                    createTodayDirectoryAndDelprevDayDir(currentDateString,null);
                    fw = new FileWriter("./readedAnnouncements",true);
                } catch (Exception e) {
                    LOG.error("failed to write already readed announcements to file");
                }
            }else{
                if(!currentDateString.equals(prevDate)){
                    Date parseDate = new SimpleDateFormat("yyyy-MM-dd").parse(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
                    announcementService.setDate(parseDate.getTime());
                    announcementStatusMap.clear();
                    try {
                        createTodayDirectoryAndDelprevDayDir(currentDateString,prevDate);
                        fw.close();
                        fw = new FileWriter("./readedAnnouncements",false);
                    } catch (IOException e) {
                       LOG.info("Exception:{}",e);
                    }

                }
            }

            int offset = 0;
            List<String> announcementFilters = new ArrayList<>(stocks);

            AnnonuncementsResult latestAnnouncements = announcementService.getLatestAnnouncements(offset, LIMIT,2,readWholeNseAnnouncemtsOnce);
            int totalResults = latestAnnouncements!=null ?latestAnnouncements.getResults():0;
            LOG.info("offset:{}Getting Total Announcements:{}",offset,totalResults);
            int pageSize = totalResults/LIMIT;
            while (pageSize>=0) {
                if(readWholeNseAnnouncemtsOnce){
                    pageSize=0;
                }
                AnnonuncementsResult annonuncementsResult = announcementService.getLatestAnnouncements(pageSize*LIMIT, LIMIT,2,readWholeNseAnnouncemtsOnce);
                LOG.info("offset:{}",pageSize*LIMIT);
                pageSize--;
                Stack<Annonuncements> rs = new Stack<>();
                if(annonuncementsResult!=null && annonuncementsResult.getRows()!=null){
                    rs.addAll(annonuncementsResult.getRows());
                }
                Annonuncements annonuncement = null;
                while (!rs.empty()&&(annonuncement=rs.pop())!=null) {
                    try {
                        if (announcementFilters.contains(annonuncement.getSymbol())&& annonuncement.getDate().contains(currentDateString) && !alreadySentAnnouncements.contains(annonuncement.getLink())) {
                            if (announcementStatusMap.size() < 10000) {

                                if (!announcementStatusMap.contains(annonuncement)) {
                                    if(!(annonuncement.getDesc().contains("Dividend")||
                                            annonuncement.getDesc().contains("Investor Meet/Con. Call Updates")) ) {
                                        AnnonuncementPage annonuncementPage = announcementService.getAttachementUri(annonuncement.getLink());
                                        LOG.debug("Extracting zip url:{},{},{}", annonuncementPage.getZipUrl(),annonuncement.getCompany(),annonuncement.getDate());
                                        announcementService.extractZipAndSendEmail(annonuncementPage, annonuncement,currentDateString);
                                        fw.write(annonuncement.getLink()+"\n");
                                        fw.flush();
                                    }
                                    announcementStatusMap.add(annonuncement);
                                } else {
                                    LOG.debug("announcement already published:{},{}", annonuncement.getCompany(), annonuncement.getDesc());
                                }
                            } else {
                                Annonuncements firstEle = announcementStatusMap.iterator().next();
                                boolean dateAnnonuncementsEntry = announcementStatusMap.remove(firstEle);
                                LOG.debug("clearing history:{},published:{}", firstEle);
                            }
                        } else {
                            LOG.debug("skipping the announcement for:{},{}", annonuncement.getCompany(), annonuncement.getDesc());
                        }
                    } catch (Exception e) {
                        LOG.error("Exception:{}", e);
                    }
                }
            }
            readWholeNseAnnouncemtsOnce=true;
        }

    }

    private void createTodayDirectoryAndDelprevDayDir(String currentDateString, String prevDate) {
        File createdDir = new File("./announcements/"+currentDateString);
        if (!createdDir.exists()) {
            LOG.info("creating directory: " + createdDir.getName());
            boolean result = false;
            try{
                createdDir.mkdir();
            }
            catch(SecurityException se){
                LOG.info("unable to create Directory:{}" ,se.getCause());
                LOG.info("Exception:{}",se);
            }
        }
        if(prevDate==null) return;
        File delDir = new File("./announcements/"+prevDate);
        if(delDir.exists()&& delDir.isDirectory()){
            File[] files = delDir.listFiles();
            for (File file : files) {
                String fileName = file.getName();
                boolean delete = file.delete();
                if(delete){
                    LOG.info("deleted:{}",fileName);
                }
            }
            delDir.delete();
            LOG.info("deleted Directory:{}",delDir.getName());
        }
    }

    private void populateStockNamesAndAlreadyrReadedAnnouncements() {
        try {
            InputStream input = AppStart.class.getClassLoader().getResourceAsStream("stockNames.txt");
            BufferedReader br = new BufferedReader(new InputStreamReader(input));
            String Line = null;
            while ((Line = br.readLine()) != null) {
                stocks.add(Line.trim());
            }
            br.close();

            FileReader reader = new FileReader("./readedAnnouncements");
            if(reader!=null) {
                br = new BufferedReader(reader);
                while ((Line = br.readLine()) != null) {
                    alreadySentAnnouncements.add(Line);
                }
                br.close();
            }


        } catch (IOException e) {
            LOG.info("Exception:{}",e);
        }
    }
}