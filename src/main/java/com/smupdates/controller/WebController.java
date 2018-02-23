package com.smupdates.controller;

/**
 * Created by srikanth on 18/10/17.
 */

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.HttpVersion;
import org.apache.http.impl.DefaultHttpResponseFactory;
import org.apache.http.message.BasicStatusLine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
public class WebController {
    private static final Logger LOG = LoggerFactory.getLogger(WebController.class);
    @Value("${announcemet.folder.path}")
    private String announcementFolder;
    private Map<String,Integer>  alreadyReadedFiles = new HashMap<>();

    @RequestMapping(value = "/healthCheck",method = RequestMethod.GET)
    public HttpResponse helloWorld() {
        return new DefaultHttpResponseFactory().newHttpResponse(new BasicStatusLine(HttpVersion.HTTP_1_1, HttpStatus.SC_OK, null), null);
    }

    @RequestMapping(value = "/dna",method = RequestMethod.GET)
    public String  getLatestAnnouncements_Desktop_Notification_Page() {
        return "desktopNotificationAnnouncements";
    }


    @RequestMapping(value = "/latestAnnouncements",method = RequestMethod.GET)
    @ResponseBody
    public List<String> getLatestAnnouncements_Data() {

        List<String> fileList = new ArrayList<>();
        String currentDateString = new SimpleDateFormat("MMM dd,").format(new Date()).toString();
        File folder = new File(announcementFolder+currentDateString+"/");

        LOG.info(announcementFolder);
        File[] listOfFiles = folder.listFiles();

        if(listOfFiles!=null) {
            for (int i = 0; i < listOfFiles.length; i++) {
                if (listOfFiles[i].isFile()) {
                    String fileName = listOfFiles[i].getName();
                   // LOG.info("File " + listOfFiles[i].getName());
                    Integer notificationCount = alreadyReadedFiles.get(fileName);
                    if (notificationCount!=null) {
                        if(notificationCount<2){
                            alreadyReadedFiles.put(fileName,++notificationCount);
                            fileList.add(fileName);
                        }

                    }else {
                        alreadyReadedFiles.put(fileName,1);
                        fileList.add(fileName);
                    }
                }
            }
        }
        Collections.reverse(fileList);
        return fileList;
    }

    @RequestMapping(value = "/dna/{page}",method = RequestMethod.GET)
    @ResponseBody
    public void getFile(HttpServletResponse response, @PathVariable("page") String fileName){
        String currentDateString = new SimpleDateFormat("MMM dd,").format(new Date()).toString();
        if(!fileName.endsWith(".pdf")){
            fileName=fileName+".pdf";
        }
        response.setContentType("text/pdf");
        String headerKey = "Content-Disposition";
        String headerValue = String.format("attachment; filename=\"%s\"", fileName);
        response.setHeader(headerKey, headerValue);
        try {
            writeDataToResponse(response,"./"+announcementFolder+currentDateString+"/"+fileName);
        } catch (IOException e) {
            LOG.error("Downloading Of " + fileName + " Failed ", e);
            try {
                OutputStream outStream = response.getOutputStream();
                outStream.write(("Internal Server Error, Unable To Download..").getBytes());
                outStream.close();
            } catch (IOException ex) {
                LOG.error("Error in closing output stream", ex);
            }

        }

    }

    protected void writeDataToResponse(HttpServletResponse response, String fileName) throws IOException {
        FileInputStream inputStream = new FileInputStream(fileName);
        OutputStream outStream = response.getOutputStream();

        byte[] buffer = new byte[4096];
        int bytesRead = -1;

        // write bytes read from the input stream into the output stream
        while ((bytesRead = inputStream.read(buffer)) != -1) {
            outStream.write(buffer, 0, bytesRead);
        }

        inputStream.close();
        outStream.close();
    }

}

