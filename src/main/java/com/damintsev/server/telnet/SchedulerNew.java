package com.damintsev.server.telnet;

import com.damintsev.client.devices.CommonDevice;
import com.damintsev.client.devices.Device;
import com.damintsev.client.devices.Station;
import com.damintsev.client.devices.TestResponse;
import com.damintsev.client.devices.enums.Status;

import java.util.HashMap;
import java.util.Map;

/**
 * User: Damintsev Andrey
 * Date: 13.08.13
 * Time: 1:49
 */
public class SchedulerNew {

    private static SchedulerNew instance;

    public static SchedulerNew getInstance() {
        if (instance == null) instance = new SchedulerNew();
        return instance;
    }

    private Map<Long, TelnetClient> telnetStation;
    private Map<Long, Station> stationDevices;

    private SchedulerNew() {
        telnetStation = new HashMap<Long, TelnetClient>();
        stationDevices = new HashMap<Long, Station>();
    }

//    public void addDevice(Device device) {
//        System.out.println("addDevice id=" + device.getId());
//        if (device instanceof Station) {
//            Station station = (Station) device;
//            try {
//                initConnection(station);
//            } catch (IOException e) {
//                System.out.println("addDevice exception " + e.getMessage());
//                e.printStackTrace();
//                throw new RuntimeException(e);  //todo runtime exeption
//            }
//        } else {
////            if (s)
//        }
//    }

    private boolean initConnection(Station station) {
        TelnetClient telnet = new TelnetClient();
        telnet.setHost(station.getHost());
        telnet.setPort(station.getPort());
        telnet.setLogin(station.getLogin());
        telnet.setPassword(station.getPassword());
//        try {
            if (telnet.connect()) {
                telnet.start();
                System.out.println("telnetStation.put");
                telnetStation.put(station.getId(), telnet);
                return true;
            }
//        } catch (IOException e) {
//            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
//            throw new RuntimeException(e);
//        }
        return false;
    }

    public Device checkDevice(Device device) {
        if (device instanceof Station) {
            TelnetClient telnet = getConnection((Station) device);
            TestResponse response = telnet.testConnection();
            if(response.isResult())    {
                device.setStatus(Status.WORK);
            }
            else {
                device.setStatus(Status.ERROR);
            }
            return device;
        } else {
            CommonDevice dev = (CommonDevice) device;
            try{
            TelnetClient telnet = getConnection(dev.getStation());
            if (telnet != null) {
                String result = telnet.execute(dev.getQuery());
                parseResult(dev, result);
            } else {
                dev.setStatus(Status.ERROR);
            }
            }catch (Exception e) {
                System.out.println(e.getLocalizedMessage());
                e.printStackTrace();
                dev.setStatus(Status.ERROR);
            }
            return dev;
        }
    }

    private TelnetClient getConnection(Station station) {
        if (!telnetStation.containsKey(station.getId())) {
            System.out.println("getConnection. init connection!");
            initConnection(station);
        }
        System.out.println("CPT=" + telnetStation.get(station.getId()));
        return telnetStation.get(station.getId());
    }

    private void parseResult(CommonDevice isdn, String result) {
        int index = result.indexOf("PP NW");
        if (index > 0) {
            System.out.println("index = " + index + " length=" + result.length());
            result = result.substring(index, result.length());
            System.out.println(result);
        }
        if (result.contains("READY")) {
            isdn.setStatus(Status.WORK);
            System.out.println("WORK");
        } else if (result.contains("NEC")) {
            isdn.setStatus(Status.WARNING);
            System.out.println("WARNING");
        } else {
            isdn.setStatus(Status.ERROR);
            System.out.println("ERROR");
        }
        isdn.setComment(result);
    }

    public static void main(String[] args) {
        SchedulerNew newS = new SchedulerNew();
        Station station = new Station();
        station.setHost("192.168.110.128");
        station.setPort("23");
        station.setLogin("sasha");
        station.setPassword("1");
        newS.checkDevice(station);
        System.out.println("CPT1");
        newS.checkDevice(station);
        System.out.println("CPT2");
        newS.checkDevice(station);
        System.out.println("CPT3");
        newS.checkDevice(station);
        System.out.println("CPT4");
        newS.checkDevice(station);
        newS.checkDevice(station);
    }
}

