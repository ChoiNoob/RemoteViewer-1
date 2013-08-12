package com.damintsev.server.telnet;

import com.damintsev.client.devices.CommonDevice;
import com.damintsev.client.devices.Device;
import com.damintsev.client.devices.Station;
import com.damintsev.client.devices.enums.Status;

import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.regex.Pattern;

/**
* User: Damintsev Andrey
* Date: 30.07.13
* Time: 23:46
*/
public class Scheduler {

    private static Scheduler instance;

    public static Scheduler getInstance() {
        if (instance == null) instance = new Scheduler();
        return instance;
    }

    private Map<Station, Telnet> telnetStation;
    private Map<Station, List<Device>> stationDevices;
    private boolean firstTime = true;
    private BlockingQueue<Device> queue;
    private Timer timer;

    public Scheduler() {
        queue = new ArrayBlockingQueue<Device>(100);
        stationDevices = new HashMap<Station, List<Device>>();
        timer = new Timer();
    }

    public void addDevice(Device device) {
        if (telnetStation == null) telnetStation = new HashMap<Station, Telnet>();
        if(device instanceof Station) {
            Station station = (Station) device;
            initConnection(station);
            if(!stationDevices.containsKey(station)) {
                System.out.println("station put");
                stationDevices.put(station, new ArrayList<Device>());
            }
        } else {
            if(stationDevices.containsKey(device.getStation())) {
                System.out.println("device put");
                stationDevices.get(device.getStation()).add(device);
            }
        }
        if (firstTime) {
//            new Thread(new Runnable() {
//                public void run() {
                    timer.scheduleAtFixedRate(new TimerTask() {
                        @Override
                        public void run() {
                            scheduler();
                        }
                    }, 1000, 1000);
//                }
//            }).start();


            firstTime = false;
        }

    }

    private void initConnection(Station station) {
        Telnet telnet = new Telnet();
        telnet.setHost(station.getHost());
        telnet.setPort(station.getPort());
        telnet.setLogin(station.getLogin());
        telnet.setPassword(station.getPassword());
//        if(telnet.connect()) {
//            telnetStation.put(station, telnet);
//        }
    }

    private Telnet getConnection(Station station) {
        if(!telnetStation.containsKey(station))
            initConnection(station);
        return telnetStation.get(station);
    }

    public void scheduler() {
        System.out.println("shdule");
        for(Map.Entry<Station, List<Device>> entry : stationDevices.entrySet()) {
            Station station = entry.getKey();
//            Telnet telnet = getConnection(station);
            for(Device devices : entry.getValue()) {
                CommonDevice device  = (CommonDevice) devices;
//                String result = telnet.executeCommand(device.getQuery());
                String result = "asdasd";
                parseResult(device, result);
                queue.add(device);
                System.out.println("quque add");
//                try {
////                    Thread.sleep(2000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
            }
        }
    }

    private void parseResult(CommonDevice isdn, String result) {
//        Pattern pattern = new Pattern();
//       result.split()
        isdn.setStatus(Status.WORK);
    }

    public Device getState() {
        System.out.println("poll size = " + queue.size());
        Device device = queue.poll();
        System.out.println("device=" + device.toString());
        return device;
    }
}