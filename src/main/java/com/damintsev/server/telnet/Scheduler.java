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

    Map<Station, Telnet> telnetStation;
    Map<Station, List<Device>> stationDevices;
    BlockingQueue<Device> queue;

    public Scheduler() {
        queue = new ArrayBlockingQueue<Device>(100);
    }

    public void addStation(Station station) {
        if (telnetStation == null) telnetStation = new HashMap<Station, Telnet>();
        initConnection(station);
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                scheduler();
            }
        },1000);
    }

    private void initConnection(Station station) {
        Telnet telnet = new Telnet();
        telnet.setHost(station.getHost());
        telnet.setPort(station.getPort());
        telnet.setLogin(station.getLogin());
        telnet.setPassword(station.getPassword());
        if(telnet.connect()) {
            telnetStation.put(station, telnet);
        }
    }

    private Telnet getConnection(Station station) {
        if(!telnetStation.containsKey(station))
            initConnection(station);
        return telnetStation.get(station);
    }

    public synchronized void scheduler() {
        for(Map.Entry<Station, List<Device>> entry : stationDevices.entrySet()) {
            Station station = entry.getKey();
            Telnet telnet = getConnection(station);
            for(Device devices : entry.getValue()) {
                CommonDevice device  = (CommonDevice) devices;
                String result = telnet.executeCommand(device.getQuery());
                parseResult(device, result);
                queue.add(device);
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void parseResult(CommonDevice isdn, String result) {
//        Pattern pattern = new Pattern();
//       result.split()
        isdn.setStatus(Status.WORK);
    }
}