package com.damintsev.server.services;

import com.damintsev.client.devices.*;
import com.damintsev.client.devices.graph.BusyInfo;
import com.damintsev.client.service.ClientService;
import com.damintsev.server.db.CleanManager;
import com.damintsev.server.db.DatabaseProxy;
import com.damintsev.server.ftp.FTPService;
import com.damintsev.server.telnet.TelnetScheduler;
import com.damintsev.utils.ListLoadResultImpl;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
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
        DatabaseProxy proxy = new DatabaseProxy();
        proxy.saveItems(items);
        return true;
    }

    public List<Item> loadItems() {
        logger.info("Call loadItems()");
        DatabaseProxy proxy = new DatabaseProxy();
        return proxy.loadItemPositions();
    }

    public Device getState() {
        return null;
    }

//    public TestResponse test(Station device) {
//        return TelnetScheduler.getInstance().test(device); //todo
//    }

    public Device checkDevice(Device device) {
        logger.info("Calling checkDevice with type=" + device.getDeviceType() + " id=" + device.getId() + " name=" + device.getName());
//        Device result = null;
//        try {
//            result = TelnetScheduler.getInstance().checkDevice(device);
//            return result;
//        }catch (Exception e) {
//            logger.error("Error while processing checkDevice: ", e);
//            throw new RuntimeException(e);
//        }
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
        list.setData(FTPService.getInstance().getBills());
        return list;
    }

    public void saveFTPSettings(FTPSettings settings) {
        DatabaseProxy proxy = new DatabaseProxy();
        proxy.saveFTP(settings);
    }

    public FTPSettings loadFTPSettings(Station station) {
        DatabaseProxy proxy = new DatabaseProxy();
        return proxy.loadFTP(station);
    }

    public void deleteDevice(Device device) {
        DatabaseProxy proxy = new DatabaseProxy();
        proxy.delete(device);
    }

    public List<BusyInfo> loadBusyInfo(Device device) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
