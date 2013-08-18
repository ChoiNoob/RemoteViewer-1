package com.damintsev.server.telnet;

import com.damintsev.client.devices.*;
import com.damintsev.client.devices.enums.DeviceType;
import com.damintsev.client.devices.enums.Status;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * User: Damintsev Andrey
 * Date: 13.08.13
 * Time: 1:49
 */
public class TelnetScheduler {

    private static final Logger logger = LoggerFactory.getLogger(TelnetScheduler.class);

    private static TelnetScheduler instance;

    public static TelnetScheduler getInstance() {
        if (instance == null) instance = new TelnetScheduler();
        return instance;
    }

    private Map<Long, TelnetWorker> telnetStation;
    private ConcurrentHashMap<Long, Device> devices;
    private Timer timer;

    private TelnetScheduler() {
        telnetStation = new HashMap<Long, TelnetWorker>();
        devices = new ConcurrentHashMap<Long, Device>();
    }

    public Device getDeviceState(Device device) {
        Device response;
        if (!devices.containsKey(device.getId())) {
            addDevice(device);
            response = device;
            response.setResponse(TelnetScheduler.createInitResponse());
        } else {
            response = devices.get(device.getId());
        }
        return response;
    }

    private void addDevice(Device device) {
        devices.put(device.getId(), device);
        if (device instanceof Station) {
            initConnection((Station) device);
        }
    }

    private boolean initConnection(Station station) {
        TelnetWorker telnet = new TelnetWorker();
        telnet.setHost(station.getHost());
        telnet.setPort(station.getPort());
        telnet.setLogin(station.getLogin());
        telnet.setPassword(station.getPassword());
        Response resp;// = telnet.connect();
        if ((resp = telnet.connect()).isResult()) {
            telnet.start();
            telnetStation.put(station.getId(), telnet);
            return resp.isResult();
        }
        return resp.isResult();
    }

    public void checkDevice(Device device) {
        logger.info("Calling checkDevice with type=" + device.getDeviceType() + " id=" + device.getId() + " name=" + device.getName());
        TelnetWorker telnet = getConnection( device.getStation());
        if (telnet == null ) {
//            Response response = telnet.getResponse();
//            device.setResponse(response);
            device.setStatus(Status.ERROR);
            return;
        }
        if (device instanceof Station) {
            checkStation(telnet,(Station) device);
        } else if (device.getDeviceType() == DeviceType.IP) {
            checkIP(telnet,device);
        } else if (device.getDeviceType() == DeviceType.ISDN) {
            checkISDN(telnet,device);
        }
    }

    private void checkISDN(TelnetWorker telnet, Device device) {
        Response resp = telnet.execute(((CommonDevice) device).getQuery());
        parseAliveResult(resp, device);
        device.setResponse(resp);
        devices.put(device.getId(), device);
    }

    private void checkIP(TelnetWorker telnet, Device device) {
        //Check alive
        Response resp = telnet.execute(((CommonDevice) device).getQuery());
        parsePingResult(resp, device);
        device.setResponse(resp);
        devices.put(device.getId(), device);
    }

    private void parsePingResult(Response resp, Device device) {
        logger.info("Parse server response for device id=" + device.getId() + " name=" + device.getName());
        String result = resp.getResultText();
        Pattern pattern = Pattern.compile("[0-9]{2,3} bytes from ");
        Matcher matcher = pattern.matcher(result);
        if(matcher.find()){
//        if(result.matches("[0-9]{2,3} bytes from  ")){
            logger.info("Pattern found. WORK");
            device.setStatus(Status.WORK);
            resp.setStatus(Status.WORK);
        } else {
            logger.info("Pattern not found. ERROR");
            device.setStatus(Status.ERROR);
            resp.setStatus(Status.ERROR);
        }

    }

    private void parseAliveResult(Response resp, Device device) {
        logger.info("Parse server response for device id=" + device.getId() + " name=" + device.getName());
        String result = resp.getResultText();
        logger.info(result);
        int index = result.indexOf("PP NW");
        if (index > 0) {
            result = result.substring(index, result.length());
            logger.info("After substring: " + result);
        }
        if (result.contains("READY")) {
            device.setStatus(Status.WORK);
            resp.setStatus(Status.WORK);
            logger.info("After parse result is WORK");
        } else if (result.contains("NEC")) {
            device.setStatus(Status.WARNING);
            resp.setStatus(Status.WARNING);
            logger.info("After parse result is WARNING");
        } else {
            device.setStatus(Status.ERROR);
            resp.setStatus(Status.ERROR);
            logger.info("After parse result is ERROR");
        }
//        device.setComment(result);
    }

    private void checkStation(TelnetWorker telnet, Station station) {
        Response resp = null;
        try {
            resp = telnet.sendAYT();
        } catch (Exception e) {
            logger.error("Caught exception while sending ASK command " + e.getLocalizedMessage());
            resp = new Response();
            resp.setStatus(Status.ERROR);
            resp.setResultText(e.getMessage());
            telnet.disconnect();
            telnetStation.remove(station.getId());
        }
        station.setResponse(resp);
        station.setStatus(resp.getStatus());
        devices.put(station.getId(), station);
    }

    public void start() {
        logger.info("Start timer");
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if(iterator == null)
                    createIterator();
                if (iterator.hasNext())
                    checkDevice(iterator.next());
                else
                    createIterator();
            }
        }, 3000, 3000);
    }

    public void stop() {
        logger.info("Stop timer");
        timer.cancel();
    }

    private Iterator<Device> iterator;

    private void createIterator() {
        iterator = devices.values().iterator();
    }

    private synchronized TelnetWorker getConnection(Station station) {
        if (!telnetStation.containsKey(station.getId())) {
            logger.info("connection for Station id=" + station.getId() + " name=" + station.getHost() + " not found. Initializing new one");
            initConnection(station);
        } else {
            logger.info("Connection for Station id=" + station.getId() + " name=" + station.getHost() + " found");
        }
        return telnetStation.get(station.getId());
    }

//    private void parseResult(CommonDevice isdn, String result) {
//        logger.info("Parse server responce for device id=" + isdn.getId() + " name=" + isdn.getName());
//        logger.info(result);
//        int index = result.indexOf("PP NW");
//        if (index > 0) {
//            result = result.substring(index, result.length());
//            logger.info("After substring: " + result);
//        }
//        if (result.contains("READY")) {
//            isdn.setStatus(Status.WORK);
//            System.out.println("WORK");
//        } else if (result.contains("NEC")) {
//            isdn.setStatus(Status.WARNING);
//            System.out.println("WARNING");
//        } else {
//            isdn.setStatus(Status.ERROR);
//            System.out.println("ERROR");
//        }
//        isdn.setComment(result);
//    }
//
//    public static void main(String[] args) {
//        TelnetScheduler newS = new TelnetScheduler();
//        Station station = new Station();
//        station.setHost("192.168.110.128");
//        station.setPort("23");
//        station.setLogin("sasha");
//        station.setPassword("1");
//        newS.checkDevice(station);
//        System.out.println("CPT1");
//        newS.checkDevice(station);
//        System.out.println("CPT2");
//        newS.checkDevice(station);
//        System.out.println("CPT3");
//        newS.checkDevice(station);
//        System.out.println("CPT4");
//        newS.checkDevice(station);
//        newS.checkDevice(station);
//    }

//    public TestResponse test(Station device) {
//        TestResponse response = new TestResponse();
//        try{
//            System.out.println("TEST CPT:");
////            response.setResult());
//           TelnetWorker client = getConnection(device);
//            response = client.sendAYT();
//
//        }catch (Exception e) {
//            System.out.println("FIFUFUFU" + e);
//            response.setResultText("Exception " + e.getMessage());
//        }
//        return response;
//    }

    public static Response createInitResponse() {
        Response resp = new Response();
        resp.setResultText("System initializing...");
        resp.setStatus(Status.INIT);
        return resp;
    }
}

