package com.damintsev.server.services;

import com.damintsev.client.devices.*;
import com.damintsev.client.devices.enums.DeviceType;
import com.damintsev.client.devices.graph.BusyInfo;
import com.damintsev.client.service.ClientService;
import com.damintsev.server.db.CleanManager;
import com.damintsev.server.db.DatabaseProxy;
import com.damintsev.server.db.Hibernate;
import com.damintsev.server.db.xmldao.DatabaseConnector;
import com.damintsev.server.telnet.TelnetScheduler;
import com.damintsev.utils.ListLoadResultImpl;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import org.hibernate.Query;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * User: Damintsev Andrey
 * Date: 08.08.13
 * Time: 0:22
 */
public class ServerService extends RemoteServiceServlet implements ClientService {

    private static final Logger logger = LoggerFactory.getLogger(ServerService.class);

    public ServerService() {
        Timer timer = new Timer();
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.add(Calendar.DAY_OF_MONTH, 1);
        timer.schedule(
                new TimerTask() {
                    @Override
                    public void run() {
                        CleanManager.getInstance().cleanBusyInfo();
                    }
                },
                cal.getTime(),
                1000 * 60 * 60 * 24 * 7
        );
    }

    public Boolean saveItems(List<Item> items) {
        logger.info("Call saveItems()");
        DatabaseConnector.getInstance().saveUIPosition(items);
        return true;
    }

    public List<Item> loadItems() {
        return DatabaseConnector.getInstance().loadItems();
    }

    public Device getState() {
        return null;
    }

//    public TestResponse test(Station device) {
//        return TelnetScheduler.getInstance().test(device); //todo
//    }

    public Device checkDevice(Device device) {
        logger.info("Calling checkDevice with type=" + device.getDeviceType() + " id=" + device.getId() + " name=" + device.getName());
        return TelnetScheduler.getInstance().getDeviceState(device);
    }

    public void start() {
        TelnetScheduler.getInstance().start();
    }

    public void stop() {
        TelnetScheduler.getInstance().stop();
    }

    public void deleteItem(Device device) {
        TelnetScheduler.getInstance().deleteItem(device);
    }

    public ListLoadResultImpl<BillingInfo> getBillingInfo() {
        ListLoadResultImpl<BillingInfo> list = new ListLoadResultImpl<BillingInfo>();
//        list.setData(FTPService.getInstance().getBills());
        return list;
    }

    public FTPSettings saveFTPSettings(FTPSettings settings) {
//        DatabaseProxy proxy = new DatabaseProxy();
//        proxy.saveFTP(settings);
       return DatabaseConnector.getInstance().saveFTPSettings(settings);
    }

    public FTPSettings loadFTPSettings(Station station) {
//        DatabaseProxy proxy = new DatabaseProxy();
//        return proxy.loadFTP(station);
        return DatabaseConnector.getInstance().loadFTPSettings(station);
    }

    public void deleteDevice(Device device) {
        DatabaseConnector.getInstance().deleteDevice(device);
        TelnetScheduler.getInstance().deleteDevice(device);
    }

    public BusyInfo loadBusyInfo(Device device) {
        return DatabaseConnector.getInstance().getBusyInfo(device);
    }

    public void testFTP() {
//        System.out.println("CPT=" + FTPService.getInstance().getBills());
    }

    public Device saveDevice(Device device) {
        logger.info("calling saveDevice()");
        Device dev = DatabaseConnector.getInstance().saveDevice(device);
        TelnetScheduler.getInstance().updateDevice(dev);
        return dev;
    }

    public Device loadDevice(Long deviceId, DeviceType deviceType) {
        return DatabaseConnector.getInstance().loadDevice(deviceId, deviceType);
    }

    public List<BillingStats> getStatistisc() {
        return BillingStatistics.getInstance().getStatistics();
    }

    public List<BusyInfo> loadBusyInfoStatistics(Device device) {
        return DatabaseConnector.getInstance().getBusyInfoStatistics(device);
    }
}
