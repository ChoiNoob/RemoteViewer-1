package com.damintsev.server.services;

import com.damintsev.client.devices.*;
import com.damintsev.client.service.ClientService;
import com.damintsev.server.db.DatabaseProxy;
import com.damintsev.server.telnet.Scheduler;
import com.damintsev.server.telnet.SchedulerNew;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * User: Damintsev Andrey
 * Date: 08.08.13
 * Time: 0:22
 */
public class ServerService extends RemoteServiceServlet implements ClientService {

    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(ServerService.class);

    public Boolean saveItems(List<Item> items) {
        logger.debug("Call saveItems()");
        DatabaseProxy proxy = new DatabaseProxy();
        proxy.saveItems(items);
        return true;
    }

    public List<Item> loadItems() {
        logger.debug("Call loadItems()");
        DatabaseProxy proxy = new DatabaseProxy();
        return proxy.loadItemPositions();
    }

    public Device getState() {
        return null;
    }

    public void stopScheduler() {
//        Scheduler.getInstance().stop();
    }

    public void startScheduler() {
//        Scheduler.getInstance().start();
    }

    public TestResponse test(Station device) {
        return SchedulerNew.getInstance().test(device); //todo
    }

    public Device checkDevice(Device device) {
        logger.debug("Calling checkDevice with type=" + device.getDeviceType() + " id=" + device.getId() + " name=" + device.getName());
        System.out.println("with id=" + device.getId() + " name=" + device.getName());
        Device result = null;
        try {
            result = SchedulerNew.getInstance().checkDevice(device);
            return result;
        }catch (Exception e) {
            System.out.println("FUCK U!! " + e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
