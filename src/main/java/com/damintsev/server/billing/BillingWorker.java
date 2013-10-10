package com.damintsev.server.billing;

import com.damintsev.client.devices.BillingInfo;
import com.damintsev.client.devices.Device;
import com.damintsev.client.devices.Station;
//import com.damintsev.server.db.DatabaseConnector;
import com.damintsev.server.BillingStatistics;
import com.damintsev.server.telnet.TelnetWorker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by adamintsev
 * Date: 23.08.13 12:05
 */
public class BillingWorker extends Thread {

    private static final Logger logger = LoggerFactory.getLogger(BillingWorker.class);
    private static BillingWorker instance;

    public static BillingWorker getInstance() {
        if(instance == null) instance = new BillingWorker();
        return instance;
    }

    private BufferedReader reader;
    private TelnetWorker telnet = new TelnetWorker();
    private Station station;
    private Timer timer;
    private SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm");
    
    private BillingWorker() {
        logger.info("Initializing BillingWorker");
        try {
            Thread.sleep(20000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
//                if (station == null) station = DatabaseConnector.getInstance().getStationWithBilling();
                if (station == null) return;
                if(station.getAllowStatistics())
                    if(!telnet.isConnected())
                        initConnection();
            }
        },1000 * 60 * 1, 1000 * 60 * 1);
        logger.info("Billing worker successfully initialized");
    }

    private void initConnection() {
        try {
//            if (station == null) station = DatabaseConnector.getInstance().getStationWithBilling();
//            if (station == null) return;
            logger.info("Initializing connection to host=" + station.getHost() +
                    " port=" + station.getPort() +
                    " login=" + station.getLogin() +
                    " password=" + station.getPassword());
            telnet.setHost(station.getHost());
            telnet.setPort("1202");
            telnet.setAutnentication(false);
            telnet.setKeepAlive(true);
            telnet.setAllowTimeout(false);
            if (telnet.connect().isResult()) {
                logger.info("Telnet connection established!");
                reader = new BufferedReader(new InputStreamReader(telnet.getInputStream()));
                this.start();
            }
        } catch (Exception e) {
            logger.error("Error while connecting to server =" + e.getMessage(), e);
        }
    }

    @Override
    public void run() {
        try {
            logger.info("Reading thread started...");
            int ch;
            boolean read = false;
            StringBuilder sb = null;
            while (true) {
                ch = reader.read();
                if(ch == '$') {
                    read = true;
                    sb = new StringBuilder(100);
                }
                if (read) {
                    sb.append((char)ch);
                }
                if (ch == '%') {
                    parseReaded(sb.toString());
                    read = false;
                }
            }
        } catch (Exception e) {
            logger.info("Catched exception while read stream from server =" + e.getMessage(), e);
            reconnect();
        }
    }

    private void reconnect() {
        logger.info("Reconnecting to server");
        disconnect();
        initConnection();
    }

    private void disconnect() {
        try{
            logger.info("Interrupting");
            this.interrupt();
            if(reader != null) reader.close();
            if(telnet.isConnected()) {
                logger.info("Telnet disconnecting");
                telnet.disconnect();
            }
        } catch (IOException e) {
            logger.info("Error while disconnecting from server =" + e.getMessage(), e);
//            e.printStackTrace();
        }
    }

    private void parseReaded(String result) throws ParseException {
        logger.info("Readed string =" + result);
        result = result.replace("$", "").replace("%", "");
        String[] fields = result.split(";");
        if (fields.length == 6) {
            BillingInfo info = new BillingInfo();
            logger.info("[0]=" + fields[0] +
                    " [1]=" + fields[1] +
                    " [2]=" + fields[2] +
                    " [4]=" + fields[3] +
                    " [4]=" + fields[4] +
                    " [5]=" + fields[5] );
            logger.info("Parsing [0]=" + format.parse(fields[0] + " " + fields[1]));
            info.setDate(format.parse(fields[0] + " " + fields[1]));
            info.setNumberFrom(fields[2].trim());
            info.setNumber(fields[3].trim());
            String shortNum = fields[3].length() >= 6 ? fields[3].substring(0, 6) : fields[3];
            info.setNumberShort(shortNum);
            info.setCallDuration(fields[4]);
            if(!fields[5].equals("") || fields[5] !=null)
                info.setTrunkNumber(Long.valueOf(fields[5]));
            logger.info("Adding new Billing info");
            BillingStatistics.getInstance().addBilling(info);
        }
    }

    public void updateStation(Device station) {
        if(!(station instanceof Station))
            return;
        this.station = (Station) station;
        reconnect();
    }

    public void deleteStation(Device device) {
        if(device instanceof Station) {
            disconnect();
            station = null;
        }
    }
}
