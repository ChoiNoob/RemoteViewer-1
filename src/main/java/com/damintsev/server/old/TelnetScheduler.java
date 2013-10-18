//package com.damintsev.server.telnet;
//
//import com.damintsev.client.devices.*;
//import com.damintsev.client.devices.enums.DeviceType;
//import com.damintsev.client.devices.enums.Status;
//import com.damintsev.client.devices.graph.BusyInfo;
//import com.damintsev.server.old.Executor;
//import com.damintsev.server.old.SingleThread;
//import com.damintsev.server.db.DatabaseConnector;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import java.io.*;
//import java.util.*;
//import java.util.concurrent.ConcurrentHashMap;
//import java.util.concurrent.ScheduledExecutorService;
//import java.util.concurrent.TimeUnit;
//import java.util.regex.Matcher;
//import java.util.regex.Pattern;
//
///**
// * User: Damintsev Andrey
// * Date: 13.08.13
// * Time: 1:49
// */
//public class TelnetScheduler {
//
//    private static final Logger logger = LoggerFactory.getLogger(TelnetScheduler.class);
//    private static final Long HOUR = 1000 * 60 * 60L;
//    private static TelnetScheduler instance;
//    private static int count = 1;
//    private SingleThread executor = Executor.getInstance().createThread(TelnetScheduler.class);
//
//    public static TelnetScheduler getInstance() {
//        if (instance == null) instance = new TelnetScheduler();
//        return instance;
//    }
//
//    private Map<Long, TelnetWorker> telnetStation;
//    private ConcurrentHashMap<Long, Device> devices;
////    private Timer timer;
////    private Timer databaseLoader;
//
//    private TelnetScheduler() {
//        logger.info("Initializing TelnetSheduler id=" + count++);
//        telnetStation = new HashMap<Long, TelnetWorker>();
//        devices = new ConcurrentHashMap<Long, Device>();
//        loadFromDB();
//        logger.info("Telnet Scheduler sucsessfylly constructed");
////        start();
//    }
//
//    private boolean initConnection(Station station) {
//        TelnetWorker telnet = new TelnetWorker();
//        telnet.setHost(station.getHost());
//        telnet.setPort(station.getPort());
//        telnet.setLogin(station.getLogin());
//        telnet.setPassword(station.getPassword());
//        Response resp;
//        if ((resp = telnet.connect()).isResult()) {
//            telnet.start();
//            telnetStation.put(station.getId(), telnet);
//            return resp.isResult();
//        }
//        return resp.isResult();
//    }
//
//    public synchronized void checkDevice(Device device) {
//        logger.info("Calling checkDevice with type=" + device.getDeviceType() + " id=" + device.getId() + " name=" + device.getName());
//        try {
//            TelnetWorker telnet = getConnection(device.getStation());
////            if (telnet == null) {
////                device.setStatus(Status.ERROR);
////                return;
////            }
//            if (device instanceof Station) {
//                checkStation(telnet, (Station) device);
//            } else if (device.getDeviceType() == DeviceType.IP) {
//                checkIP(telnet, device);
//            } else if (device.getDeviceType() == DeviceType.ISDN) {
//                checkISDN(telnet, device);
//            }
//        } catch (Exception e) {
//            logger.info("Exception wile checking device =" + e.getMessage());
//            TelnetWorker telnet = telnetStation.get(device.getId());
//            telnetStation.remove(device.getId());
//            if(telnet!=null)telnet.disconnect();
//            device.setStatus(Status.ERROR);
//            devices.put(device.getId(), device);
//        }
//    }
//
//    private void checkISDN(TelnetWorker telnet, Device device) {
//        Response resp = telnet.execute(((CommonDevice) device).getQuery());
//        parseAliveResult(resp, device);
//        device.setResponse(resp);
//        devices.put(device.getId(), device);
//        if(((CommonDevice) device).getQueryBusy() != null) {
//            resp = telnet.execute(((CommonDevice) device).getQueryBusy());
//            parseBusyResponse(resp, device);
//        }
//    }
//
//    private void parseBusyResponse(Response resp, Device device) {
//        String result = resp.getResultText();
//        logger.debug("After ask busy result is: " + result);
//        int index = result.indexOf("OTHER");
//        BusyInfo info;
//        String busy = null;
//        if (index > 0) {
//            result = result.substring(index + 5);
//            logger.debug("After substring result is =" + result);
//            result = result.replace("   ", " ");
//            result = result.replace("  ", " ");
//            String[] table = result.trim().split(" ");
//            logger.debug("After replace =" + result);
//            if (table.length >= 3) {
//                logger.info("table[0]=" + table[0] + "table[1]=" + table[1] + "table[2]=" + table[2] + "table[3]=" + table[3]);
//                busy = table[3];
//                info = new BusyInfo();
//                info.setBusy(Long.parseLong(busy));
//                info.setDate(new Date());
//                info.setDeviceId(device.getId());
//                info.setMax(Long.parseLong(table[2]));
//                DatabaseConnector.getInstance().saveBusyInfo(device.getId(), info);
//                        ((CommonDevice) device).setBusyInfo(info);
//            }
//        }
//    }
//
//    private void checkIP(TelnetWorker telnet, Device device) throws IOException {
//        //Check alive
////        Response resp = telnet.execute(((CommonDevice) device).getQuery());
////        parsePingResult(resp, device);
////        device.setResponse(resp);
////        devices.put(device.getId(), device);
////        if(((CommonDevice) device).getQueryBusy() != null) {
////            resp = telnet.execute(((CommonDevice) device).getQueryBusy());
////            parseBusyResponse(resp, device);
////        }
//        Response resp = new Response();
//        InputStream in = null;
//        BufferedReader reader = null;
//        Process execute = null;
//        try {
//            execute = Runtime.getRuntime().exec(((CommonDevice) device).getQuery());
//            in = execute.getInputStream();
//            reader = new BufferedReader(new InputStreamReader(in));
//            StringBuilder sb = new StringBuilder();
//            String line;
//            while ((line = reader.readLine()) != null) {
//                sb.append(line);
//            }
//            resp.setResultText(sb.toString());
//            parsePingResult(resp, device);
//
//        } catch (Exception e) {
//            resp.setResult(false);
//            resp.setStatus(Status.ERROR);
//            device.setStatus(Status.ERROR);
//        } finally {
//            if (reader != null) {
//                reader.close();
//            }
//            if (in != null) {
//                in.close();
//            }
//            if (execute != null) {
//                execute.destroy();
//            }
//        }
//    }
//
//    private void parsePingResult(Response resp, Device device) {
//        logger.info("Parse server response for device id=" + device.getId() + " name=" + device.getName());
//        String result = resp.getResultText();
//        Pattern pattern = Pattern.compile("[0-9]{2,3} bytes from ");
//        Matcher matcher = pattern.matcher(result);
//        if(matcher.find()){
////        if(result.matches("[0-9]{2,3} bytes from  ")){
//            logger.info("Pattern found. WORK");
//            device.setStatus(Status.WORK);
//            resp.setStatus(Status.WORK);
//        } else {
//            logger.info("Pattern not found. ERROR");
//            device.setStatus(Status.ERROR);
//            resp.setStatus(Status.ERROR);
//        }
//    }
//
//    private void parseAliveResult(Response resp, Device device) {
//        logger.info("Parse server response for device id=" + device.getId() + " name=" + device.getName());
//        String result = resp.getResultText();
//        logger.debug(result);
//        int index = result.indexOf("PP NW");
//        if (index > 0) {
//            result = result.substring(index, result.length());
//            logger.debug("After substring: " + result);
//        }
//        if (result.contains("READY")) {
//            device.setStatus(Status.WORK);
//            resp.setStatus(Status.WORK);
//            logger.info("After parse result is WORK");
//        } else if (result.contains("NEC")) {
//            device.setStatus(Status.WARNING);
//            resp.setStatus(Status.WARNING);
//            logger.info("After parse result is WARNING");
//        } else {
//            device.setStatus(Status.ERROR);
//            resp.setStatus(Status.ERROR);
//            logger.info("After parse result is ERROR");
//        }
////        device.setComment(result);
//    }
//
//    private void checkStation(TelnetWorker telnet, Station station) {
//        Response resp = null;
//        try {
////            resp = new Response("sucsess");
////            resp.setStatus(Status.WORK);
//            resp = telnet.execute("dis-date;");
//            parseStationResponse(resp, station);
////            telnet.execute()
//        } catch (Exception e) {
//            logger.error("Caught exception while sending ASK command " + e.getLocalizedMessage());
//            resp = new Response();
//            resp.setStatus(Status.ERROR);
//            resp.setResultText(e.getMessage());
//            telnet.disconnect();
//            telnetStation.remove(station.getId());
//        }
//        station.setResponse(resp);
//        station.setStatus(resp.getStatus());
//        devices.put(station.getId(), station);
//        try {
//            Thread.sleep(5000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//    }
//
//    private void parseStationResponse(Response resp, Station station) {
//        if (resp.getResultText().contains("STARTED") && resp.getResultText().contains("AMO DATE")) {
//            logger.info("After Station parse result is WORK");
//            station.setStatus(Status.WORK);
//            resp.setStatus(Status.WORK);
//        }
//        else {
//            logger.info("After Station parse result is ERROR");
//            station.setStatus(Status.ERROR);
//            resp.setStatus(Status.ERROR);
//        }
//    }
//
//    public void start() {
//        logger.info("Start timer");
//            executor.scheduleAtFixedRate(new Runnable() {
//                public void run() {
//                    Thread.currentThread().setName("telnet sched");
//                    if (iterator == null)
//                        createIterator();
//                    if (iterator.hasNext())
//                        checkDevice(iterator.next());
//                    else
//                        createIterator();
//                }
//            }, 10, 10, TimeUnit.SECONDS);
//
////        if (timer == null) {
////            timer = new Timer();
////            timer.scheduleAtFixedRate(new TimerTask() {
////                @Override
////                public void run() {
////                    if (iterator == null)
////                        createIterator();
////                    if (iterator.hasNext())
////                        checkDevice(iterator.next());
////                    else
////                        createIterator();
////                }
////            }, 20000, 10000);
////        }
////        if (databaseLoader == null) {
////            databaseLoader = new Timer();
////            databaseLoader.scheduleAtFixedRate(new TimerTask() {
////                @Override
////                public void run() {
////                    loadFromDB();
////                }
////            }, HOUR, HOUR);
////        }
//    }
//
//    public void stop() {
//        logger.info("Stop timer");
////        timer.cancel();
////        timer = null;
//
////        if(!shed.())
////            shed.shutdown();
////        databaseLoader.cancel();
////        databaseLoader = null;
//    }
//
//    private Iterator<Device> iterator;
//
//    private void createIterator() {
//        iterator = devices.values().iterator();
//    }
//
//    private synchronized TelnetWorker getConnection(Station station) {
//        if (!telnetStation.containsKey(station.getId())) {
//            logger.info("connection for Station id=" + station.getId() + " name=" + station.getHost() + " not found. Initializing new one");
//            initConnection(station);
//        } else {
//            logger.info("Connection for Station id=" + station.getId() + " name=" + station.getHost() + " found");
//        }
//        return telnetStation.get(station.getId());
//    }
//
//    public void updateDevice(Device device) {
//        stop();
//        if(device instanceof Station) {
//            TelnetWorker telnet = telnetStation.get(device.getId());
//            if(telnet!=null) {
//                telnet.disconnect();
//            }
//            telnetStation.remove(device.getId());
//            devices.put(device.getId(), device);
//        } else {
//            devices.put(device.getId(), device);
//        }
//        start();
//    }
//
//    public void deleteDevice(Device device) {
//        stop();
//        if(device instanceof Station) {
//            TelnetWorker telnet = telnetStation.get(device.getId());
//            if(telnet!=null){
//                telnet.disconnect();
//            }
//            telnetStation.remove(device.getId());
//            for(Device dev : devices.values()) {
//                if(dev.getStation().getId().equals(dev.getId())) {
//                    devices.remove(dev.getId());
//                }
//            }
//        } else {
//            devices.remove(device.getId());
//        }
//        start();
//    }
//
//    public List<Device> getDeviceState() {
//        return new ArrayList<Device>(devices.values());
//    }
//
//    private void loadFromDB() {
////        if(timer != null)
//            stop();
//        if (devices == null) devices = new ConcurrentHashMap<Long, Device>();
//        List<Item> items =  DatabaseConnector.getInstance().loadItems();
//        logger.info("Loading information from database: loaded=" + items.size() + " items.");
//        for(Item item : items) {
//            logger.info("Adding item=" + item.getId() + " name=" + item.getName() + " type=" + item.getType());
//            devices.put(item.getId(), item.getData());
////            if(item.getData() instanceof Station) {
////                Station station = (Station) item.getData();
////                TelnetWorker telnet = getConnection(station);
////                if(!station.getHost().equals(telnet.getHost()) ||
////                        !station.getPort().equals(telnet.getPort()) ||
////                        !station.getLogin().equals(telnet.getLogin()) ||
////                        !station.getPassword().equals(telnet.getPassword())) {
////                    updateDevice(station);
////                }
////            }
//        }
////        if(timer != null)
//            start();
//    }
//
//    public void hardReset() {
//        stop();
//        for(Device device : devices.values()) {
//            device.setStatus(Status.INIT);
//        }
//        for(TelnetWorker telnet : telnetStation.values()) {
//            telnet.disconnect();
//        }
//        start();
//    }
//}
//
