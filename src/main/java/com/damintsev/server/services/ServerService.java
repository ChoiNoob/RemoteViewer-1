package com.damintsev.server.services;

import com.damintsev.client.devices.*;
import com.damintsev.client.service.ClientService;
import com.damintsev.server.db.DatabaseProxy;
import com.damintsev.server.telnet.Scheduler;
import com.damintsev.server.telnet.SchedulerNew;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import java.util.List;

/**
 * User: Damintsev Andrey
 * Date: 08.08.13
 * Time: 0:22
 */
public class ServerService extends RemoteServiceServlet implements ClientService {

    public Boolean saveItems(List<Item> items) {
        DatabaseProxy proxy = new DatabaseProxy();
        proxy.saveItems(items);
//        for(Item item : items) {
//            SchedulerNew.getInstance().addDevice(item.getData());
//        }
        return true;
    }

    public List<Item> loadItems() {
        DatabaseProxy proxy = new DatabaseProxy();
        List<Item> items = proxy.loadItemPositions();
//        for(Item item : items) {
//            SchedulerNew.getInstance().addDevice(item.getData());
//        }
        return items;
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
        return Scheduler.getInstance().test(device); //todo
    }

    public Device checkDevice(Device device) {
        System.out.println("Call checkDevice");
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
