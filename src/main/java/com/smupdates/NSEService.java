package com.smupdates;

import com.smupdates.model.AnnonuncementPage;
import com.smupdates.model.response.Annonuncements;
import com.smupdates.model.response.AnnonuncementsResult;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicHeader;
import org.codehaus.jackson.map.ObjectMapper;
import org.jsoup.Jsoup;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.*;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;


/**
 * Created by srikanth on 16/10/17.
 */
public class NSEService {
    private static final Logger LOG = LoggerFactory.getLogger(NSEService.class);

    private HttpClient client;
    private ObjectMapper mapper = new ObjectMapper();
    private Properties properties = new Properties();
    private String to = "srikanthkann525@yahoo.com";//change accordingly
    private final String user = "srikanthkann525@yahoo.com";//change accordingly
    private final String user1 = "srikanthkanna525@yahoo.com";
    private final String password = "rockvscena@w28";
    private final String password1 = "Snapdeal@123";//change accordingly
    private static int i=0;
    private Long cutrrentDate;


    public NSEService() {


        //1) get the session object

        // Setup mail server
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.mail.yahoo.com");
        properties.put("mail.smtp.user", user);
        properties.put("mail.smtp.password", password);
        properties.put("mail.smtp.port", "587");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");

        RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(30 * 1000).setSocketTimeout(30 * 1000).build();
        this.client = HttpClientBuilder.create().setDefaultRequestConfig(requestConfig).build();
    }

    public void setDate(Long date){
        this.cutrrentDate=date;
    }

    public AnnonuncementsResult getLatestAnnouncements(int offset, int limit, int retry, boolean flag) {
        AnnonuncementsResult resultListt = null;
        try{
            if( System.currentTimeMillis()> (cutrrentDate+30600000l)  && System.currentTimeMillis()<(cutrrentDate+55800000l)){
                Thread.sleep(15000L);
            }
            if(flag) {
                Thread.sleep(30000l);
            }else {
                Thread.sleep(5000l);
            }
        }catch (InterruptedException e) {
            LOG.info("Interrupted exception:{}", e.getMessage());
        }
        String url = "https://www.nseindia.com/corporates/directLink/latestAnnouncementsCorpHome.jsp?start=" + offset + "&LIMIT=" + limit;
        HttpGet get = new HttpGet(url);
        try {
            Header header = new BasicHeader("content-type", "application/json");
            HttpResponse res = client.execute(get);

            InputStream inputStream = res.getEntity().getContent();
            BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
            StringBuffer buffer = new StringBuffer();
            String line = null;
            while ((line = br.readLine()) != null) {
                buffer.append(line);
            }
            String s = buffer.toString();
            s = s.replace("company:", "\"company\":");
            s = s.replace("symbol:", "\"symbol\":");
            s = s.replace("desc:", "\"desc\":");
            s = s.replace("link:", "\"link\":");
            s = s.replace("date:", "\"date\":");


            if (res.getStatusLine().getStatusCode() == 200) {
                resultListt = mapper.readValue(s, AnnonuncementsResult.class);
            }
            LOG.info("getting results: {}", res.getStatusLine());
            inputStream.close();
            br.close();
        } catch (Exception e) {
            LOG.info("{}", e);
            try {
                Thread.sleep(30000L);
            } catch (InterruptedException e1) {
                LOG.error("Interrupted Exception:{}", e.getMessage());
            }
            if (retry > 1)
                getLatestAnnouncements(offset, limit, retry--, flag);
        }
        return resultListt;
    }

    public AnnonuncementPage getAttachementUri(String link) throws IOException {
        AnnonuncementPage annonuncementPage = new AnnonuncementPage();
        String zipUrl = "";
        String url = "https://www.nseindia.com" + link;
        LOG.info("url:{}", url);
        HttpGet get = new HttpGet(url);
        HttpResponse response = client.execute(get);
        LOG.info(response.getStatusLine().toString());

        InputStream content = response.getEntity().getContent();
        BufferedReader br = new BufferedReader(new InputStreamReader(content));
        StringBuffer data = new StringBuffer();
        String line = null;
        while ((line = br.readLine()) != null) {
            data.append(line);
        }
        br.close();
        content.close();

        annonuncementPage.setPage(data.toString());
        org.jsoup.nodes.Document document = Jsoup.parse(data.toString());
        Elements elements = document.select("a");
        zipUrl = elements.size() != 1 ? document.select("a").get(1).attr("href") : null;

        try {
            String annoncementDescription = document.select("td").get(14).childNodes().get(0).toString();
            annonuncementPage.setPage(annoncementDescription);
        } catch (Exception e) {
            LOG.error("Exception:{}", e);
        }
        annonuncementPage.setZipUrl(zipUrl);
        return annonuncementPage;
    }


    public void extractZipAndSendEmail(AnnonuncementPage page, Annonuncements annonuncements,String currentDateString) {
        URL url = null;
        try {
            if (page.getZipUrl() == null) {
                sendWithFileAttachment(null, annonuncements, page, 2);
            }
            url = new URL("https://www.nseindia.com" + page.getZipUrl());
            InputStream inputStream = url.openConnection().getInputStream();
            ZipInputStream zip = new ZipInputStream(inputStream);
            ZipEntry entry = null;

            while ((entry = zip.getNextEntry()) != null) {
                String dateString ="";
                if(annonuncements.getDate()!=null){
                    Date parseDate = null;
                    try {
                        parseDate = new SimpleDateFormat("MMM dd, HH:mm", Locale.ENGLISH).parse(annonuncements.getDate());
                        dateString =new SimpleDateFormat("HH:mm").format(parseDate).toString();
                    } catch (ParseException e) {
                        dateString = new SimpleDateFormat("hh mm").format(new Date()).toString();
                    }

                }else{
                    dateString = new SimpleDateFormat("hh mm").format(new Date()).toString();
                }

                extractEntry(entry, zip,annonuncements.getSymbol(),dateString,currentDateString);
                LOG.info(annonuncements.getDate());
                //sendWithFileAttachment(entry.getName(), annonuncements, page, 2);
               // File fs = new File(entry.getName());
                //boolean delete = fs.delete();
                //LOG.debug("Deleted extracted zip file from local:{},{}", entry.getName(), delete);
            }
            zip.close();
            inputStream.close();


        } catch (IOException e) {
            LOG.error("Unable to Open Connection:{}",e);
        } catch (MessagingException e) {
            e.printStackTrace();
        }

    }

    private void extractEntry(final ZipEntry entry, InputStream is, String prefix, String dateString,String currentDate) throws IOException {
        String exractedFile = "./announcements/"+currentDate+"/"+dateString+dateString+"_"+prefix+"_"+entry.getName();
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(exractedFile);
            final byte[] buf = new byte[1024];
            int read = 0;
            int length;
            while ((length = is.read(buf, 0, buf.length)) >= 0) {
                fos.write(buf, 0, length);
            }
        } catch (IOException ioex) {
            fos.close();
        }
    }


    public void sendWithFileAttachment(String fileName, Annonuncements annonuncements, AnnonuncementPage page, int retry) throws MessagingException {

        i++;
        if(i<20){
            properties.put("mail.smtp.user", user);
        }else {
            properties.put("mail.smtp.user", user1);
            i=0;
        }

        //2) compose message
        // Get the default Session object.
        Session session = Session.getDefaultInstance(properties);
        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(user));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
            StringBuffer subject = new StringBuffer().append(annonuncements.getCompany()).append("-").append(annonuncements.getDesc());

            message.setSubject(subject.toString());

            //3) create MimeBodyPart object and set your message content
            BodyPart messageBodyPart1 = new MimeBodyPart();
            messageBodyPart1.setText(page.getPage());


            //5) create Multipart object and add MimeBodyPart objects to this object
            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(messageBodyPart1);


            if (fileName != null) {
                //4) create new MimeBodyPart object and set DataHandler object to this object
                MimeBodyPart messageBodyPart2 = new MimeBodyPart();

                DataSource source = new FileDataSource(fileName);
                messageBodyPart2.setDataHandler(new DataHandler(source));
                messageBodyPart2.setFileName(fileName);
                multipart.addBodyPart(messageBodyPart2);
            }

            //6) set the multiplart object to the message object
            message.setContent(multipart);

            //7) send message
            Transport transport = session.getTransport("smtp");
            Thread.sleep(10000L);
            transport.send(message, user, password);
            transport.close();
            LOG.info("message sent....");
        } catch (MessagingException ex) {
            LOG.error("MessagingException : {}", ex);
            try {
                Thread.sleep(600000L);
            }catch (InterruptedException e) {
                LOG.error("Interrupted exception:{}", e.getMessage());
            }
            sendWithFileAttachment(fileName, annonuncements, page, retry--);
        }catch (InterruptedException e) {
            LOG.error("Interrupted exception:{}", e.getMessage());
        }

    }

    public boolean isAnnoncementAlreadyPublished(Map<Annonuncements, Boolean> announcementStatusMap, Annonuncements annonuncement) {
        Boolean aBoolean = announcementStatusMap.get(annonuncement);
        return aBoolean == null ? false : aBoolean;
    }
}


