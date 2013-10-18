package com.damintsev.server.old.ftp;

import com.damintsev.client.devices.BillingInfo;
import com.damintsev.client.devices.FTPSettings;
//import com.damintsev.server.db.DatabaseConnector;
import com.damintsev.server.old.BillingStatistics;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * User: Damintsev Andrey
 * Date: 19.08.13
 * Time: 2:31
 */
public class FTPScheduler {

    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(FTPScheduler.class);
    private static FTPScheduler instance;

    public static FTPScheduler getInstance() {
        if(instance==null) instance = new FTPScheduler();
        return instance;
    }
    private Iterator<FTPSettings>  iterator;
    private List<FTPSettings> listSettings;
    private HashMap<Long, FTPWorker> ftpService;
    private SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm");
    private Timer timer;

    private FTPScheduler() {
//        listSettings = DatabaseConnector.getInstance().listFTPSettings();

        ftpService = new HashMap<Long, FTPWorker>();
        try {
            Thread.sleep(30000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        timer = new Timer();
        if(listSettings != null && listSettings.size() > 0)
            start();
    }

    private void createIterator() {
        iterator = listSettings.iterator();
    }

    private void scheduler() {
        if(iterator == null)
            createIterator();
        if(iterator.hasNext())
            getRemoteFile(iterator.next());
        else
            createIterator();
    }

    public void start() {
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                scheduler();
            }
        },20000,20000);
    }

    public void stop() {
        timer.cancel();
    }

    private void getRemoteFile(FTPSettings settings) {
        try {
            FTPWorker ftp = getConnection(settings);
            logger.info("Start moving file. Getting file content");
            byte[] file = ftp.getFileByteArray(settings.getDir());
            String newFile = "_working_" + settings.getDir(); 
            logger.info("Downloading complete. Start uploading it");
            ftp.sendFile(newFile, file);
            logger.info("Uploading complete. Start removing file");
            ftp.deleteFile(settings.getDir());
            logger.info("Removing sucsess");
            
            String fileContent = ftp.getFile(newFile);
            parseFileContent(fileContent);
            
        } catch (Exception e) {
            logger.error("Error while processing file =" + e.getMessage(), e);
            e.printStackTrace(); 
        }
        
    }

    private void parseFileContent(String fileContent) throws ParseException {
        List<BillingInfo> billingList = new ArrayList<BillingInfo>();
        String []records = fileContent.split("$");
        for(String record : records) {
            String[] fields = record.split(";");
            BillingInfo info = new BillingInfo();
            info.setDate(format.parse(fields[0] + " " + fields[1]));
            info.setNumberFrom(fields[2]);
            info.setNumber(fields[3]);
            info.setNumberShort(fields[3].substring(0,6));
            info.setCallDuration(fields[4]);
            info.setTrunkNumber(Long.valueOf(fields[5]));
            billingList.add(info);
        }
        BillingStatistics.getInstance().addBilling(billingList);

//        for(BillingInfo info : billingList) {
//            DatabaseConnector.getInstance().saveBillingInfo(info);
//        }
    }

    private FTPWorker getConnection(FTPSettings settings) {
        try {
            if (!ftpService.containsKey(settings.getId())) {
                initConnection(settings);
            }
            return ftpService.get(settings.getId());
        } catch (Exception e) {
            logger.info("Exception while calling getConnection =" + e.getMessage());
            ftpService.remove(settings.getId());
        }
        return null;
    }

    private void initConnection(FTPSettings settings) {
        FTPWorker ftp = new FTPWorker();
        ftp.setSettings(settings);
        ftp.connect();
        if(ftp.isConnected()) {
            ftpService.put(settings.getId(), ftp);
        }
    }


}
