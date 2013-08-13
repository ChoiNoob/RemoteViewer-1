package com.damintsev.server.telnet;

import com.damintsev.client.devices.CommonDevice;
import com.damintsev.client.devices.Device;
import com.damintsev.client.devices.Station;
import com.damintsev.client.devices.TestResponse;
import com.damintsev.client.devices.enums.Status;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
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

    private Map<Station, TelnetClient> telnetStation;
    private Map<Station, List<Device>> stationDevices;

    private SchedulerNew() {
        telnetStation = new HashMap<Station, TelnetClient>();
        stationDevices = new HashMap<Station, List<Device>>();
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
                telnetStation.put(station, telnet);
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
            else{
                device.setStatus(Status.ERROR);
            }
        } else {
            CommonDevice dev = (CommonDevice) device;
            TelnetClient telnet = getConnection(dev.getStation());
            if (telnet != null) {
                String result = telnet.execute(dev.getQuery());
                parseResult(dev, result);
            } else {
                dev.setStatus(Status.ERROR);
            }
            return dev;
        }
        return null;
    }

    private TelnetClient getConnection(Station station) {
        if (!telnetStation.containsKey(station)) {
//            try {
                System.out.println("getConnection. init connection!");
                initConnection(station);
//            } catch (IOException e) {
//                System.out.println("fail");
//                e.printStackTrace();
//                System.out.println("e:" + e.getMessage());
//                throw new RuntimeException(e);
//            }
        }
        System.out.println("CPT=" + telnetStation.get(station));
        return telnetStation.get(station);
    }

    private void parseResult(CommonDevice isdn, String result) {
//        Pattern pattern = new Pattern();
//       result.split()
//        result = "DIS-SDSU:SPEC,,PEN,PER2,1,1,5,0;\n" +
//                "H500:  AMO SDSU  STARTED\n" +
//                "\n" +
//                "  LTG1 (PERIPHERY)\n" +
//                "------\n" +
//                "  MOUNTING LOCATION    MODULE NAME     BDL BD(#=ACT)  STATUS\n" +
//                "  -------------------  LTG    1 --------------------- READY\n" +
//                "  -AP370013-----SG  1  LTU    1 --------------------- READY\n" +
//                "  P102.AP3 1.AP3 1.005 DIU-N2          A   Q2196-X    READY\n" +
//                "             CCT  LINE         STNO  SI BUS TYPE\n" +
//                "             000  1550                  PP NW         READY\n" +
//                "\n" +
//                "AMO-SDSU -111       STATUS DISPLAY IN SWITCHING UNIT\n" +
//                "DISPLAY COMPLETED;\n" +
//                "\n";

        int index = result.indexOf("PP NW");
        System.out.println("index = " + index + " length=" + result.length());
        result = result.substring(index, result.length());
        System.out.println(result);
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

