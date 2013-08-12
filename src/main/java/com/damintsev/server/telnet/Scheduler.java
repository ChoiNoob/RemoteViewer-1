package com.damintsev.server.telnet;

import com.damintsev.client.devices.CommonDevice;
import com.damintsev.client.devices.Device;
import com.damintsev.client.devices.Station;
import com.damintsev.client.devices.enums.Status;
import com.damintsev.client.devices.TestResponse;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

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

    private Map<Station, TelnetClient> telnetStation;
    private Map<Station, List<Device>> stationDevices;
    private BlockingQueue<Device> queue;
    private Timer timer;
    private boolean running = false;

    public Scheduler() {
        queue = new ArrayBlockingQueue<Device>(100);
        stationDevices = new HashMap<Station, List<Device>>();
        telnetStation = new HashMap<Station, TelnetClient>();
        timer = new Timer();
        System.out.println("new timer");
    }

    public void addDevice(Device device) {
        if(device instanceof Station) {
            Station station = (Station) device;
            try {
                initConnection(station);
            } catch (IOException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
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
    }

    private void initConnection(Station station) throws IOException {
        TelnetClient telnet = new TelnetClient();
        telnet.setHost(station.getHost());
        telnet.setPort(station.getPort());
        telnet.setLogin(station.getLogin());
        telnet.setPassword(station.getPassword());
        if(telnet.connect()) {
            telnetStation.put(station, telnet);
        }
    }

    private TelnetClient getConnection(Station station) throws IOException {
        if(!telnetStation.containsKey(station))
            initConnection(station);
        return telnetStation.get(station);
    }

    public void scheduler() throws IOException {
        for(Map.Entry<Station, List<Device>> entry : stationDevices.entrySet()) {
            Station station = entry.getKey();
            TelnetClient telnet = getConnection(station);
            for(Device devices : entry.getValue()) {
                CommonDevice device  = (CommonDevice) devices;
                String result = telnet.execute(device.getQuery());
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
    
    private TestResponse sendTestCommand(Station station) {
        TelnetClient connection = telnetStation.get(station);
        return connection.testConnection();
    }

    public static void main(String[] args) {
        Scheduler scheduler = new Scheduler();
        scheduler.parseResult(new CommonDevice(),"");
    }
    private void parseResult(CommonDevice isdn, String result) {
//        Pattern pattern = new Pattern();
//       result.split()
        result = "DIS-SDSU:SPEC,,PEN,PER2,1,1,5,0;\n" +
                "H500:  AMO SDSU  STARTED\n" +
                "\n" +
                "  LTG1 (PERIPHERY)\n" +
                "------\n" +
                "  MOUNTING LOCATION    MODULE NAME     BDL BD(#=ACT)  STATUS\n" +
                "  -------------------  LTG    1 --------------------- READY\n" +
                "  -AP370013-----SG  1  LTU    1 --------------------- READY\n" +
                "  P102.AP3 1.AP3 1.005 DIU-N2          A   Q2196-X    READY\n" +
                "             CCT  LINE         STNO  SI BUS TYPE\n" +
                "             000  1550                  PP NW         asf\n" +
                "\n" +
                "AMO-SDSU -111       STATUS DISPLAY IN SWITCHING UNIT\n" +
                "DISPLAY COMPLETED;\n" +
                "\n" ;

        int index = result.indexOf("PP NW");
        System.out.println("index = " + index + " length=" + result.length());
        result = result.substring(index, result.length());
        System.out.println(result);
        if(result.contains("READY")) {
            isdn.setStatus(Status.WORK);
            System.out.println("WORK");
        } else if(result.contains("NEC")) {
            isdn.setStatus(Status.WARNING);
            System.out.println("WARNING");
        } else {
            isdn.setStatus(Status.ERROR);
            System.out.println("ERROR");
        }
        isdn.setComment(result);
    }

    public Device getState() {
        System.out.println("poll size = " + queue.size());
        Device device = queue.poll();
        System.out.println("device=" + device.toString());
        return device;
    }

    public void start() {
        if (!running) {
            System.out.println("starting");
            timer.scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                    try {
                        scheduler();
                    } catch (IOException e) {
                        e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                    }
                }
            }, 1000, 1000);
            running = true;
        }
    }

    public void stop() {
        timer.cancel();
        running = false;
    }

    public TestResponse test(Device device) {
        TestResponse response = null;
        if(device instanceof Station) {
            Station station = (Station) device;
            try {
                initConnection(station);
                response = sendTestCommand(station);
                if(response == null) { response = new TestResponse();
                    response.setResult(false);
                    response.setResultText("error bl9");
                }
            } catch (IOException e) {
                e.printStackTrace();
                response = new TestResponse();
                response.setResult(false);
                response.setResultText("error bl9");
            }
        }
        return response;
    }
}