//package com.damintsev.server.telnet;
//
//import com.damintsev.client.dao.ISDN;
//import com.damintsev.client.dao.Device;
//import com.damintsev.client.dao.Station;
//
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.regex.Pattern;
//
///**
// * User: Damintsev Andrey
// * Date: 30.07.13
// * Time: 23:46
// */
//public class Scheduler {
//
//    Map<Station, Telnet> telnetStation;
//    Map<Station, List<Device>> stationDevices;
//    List<Device> errorList;
//
//    public void addStation(Station station) {
//        if (telnetStation == null) telnetStation = new HashMap<Station, Telnet>();
//        initConnection(station);
//    }
//
//    private void initConnection(Station station) {
//        Telnet telnet = new Telnet();
//        telnet.setHost(station.getHost());
//        telnet.setPort(station.getPort());
//        telnet.setLogin(station.getLogin());
//        telnet.setPassword(station.getPassword());
//        if(telnet.connect()) {
//            telnetStation.put(station, telnet);
//        }
//    }
//
//    private Telnet getConnection(Station station) {
//        if(!telnetStation.containsKey(station))
//            initConnection(station);
//        return telnetStation.get(station);
//    }
//
//    public void scheduler() {
//        for(Map.Entry<Station, List<Device>> entry : stationDevices.entrySet()) {
//            Station station = entry.getKey();
//            Telnet telnet = getConnection(station);
//            for(Device devices : entry.getValue()) {
//                ISDN isdn  = (ISDN) devices;
//                String result = telnet.executeCommand(isdn.getQuery());
//                parseResult(isdn, result);
//            }
//        }
//    }
//
//    private void parseResult(ISDN isdn, String result) {
////        Pattern pattern = new Pattern();
////       result.split()
//    }
//}