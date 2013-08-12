package com.damintsev.server.services;

import com.damintsev.client.devices.Device;
import com.damintsev.client.devices.Item;
import com.damintsev.client.devices.Station;
import com.damintsev.client.service.ClientService;
import com.damintsev.server.db.DatabaseProxy;
import com.damintsev.server.telnet.Scheduler;
import com.damintsev.client.devices.TestResponse;
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
        for(Item item : items) {
            Scheduler.getInstance().addDevice(item.getData());
        }
        return true;
    }

    public List<Item> loadItems() {
        DatabaseProxy proxy = new DatabaseProxy();
        List<Item> items = proxy.loadItemPositions();
        for(Item item : items) {
            Scheduler.getInstance().addDevice(item.getData());
        }
        return items;
    }

    public Device getState() {
        return Scheduler.getInstance().getState();
    }

    public void stopScheduler() {
        Scheduler.getInstance().stop();
    }

    public void startScheduler() {
        Scheduler.getInstance().start();
    }

    public TestResponse test(Station device) {
        return Scheduler.getInstance().test(device); //todo
    }
}
