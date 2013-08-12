package com.damintsev.server.services;

import com.damintsev.client.devices.Device;
import com.damintsev.client.devices.Item;
import com.damintsev.client.devices.Station;
import com.damintsev.client.service.MyClientService;
import com.damintsev.server.db.DatabaseProxy;
import com.damintsev.server.telnet.Scheduler;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import java.util.List;

/**
 * User: Damintsev Andrey
 * Date: 08.08.13
 * Time: 0:22
 */
public class ServerService extends RemoteServiceServlet implements MyClientService {

    public ServerService(){
        System.out.println("AKSKASFSKJFLKS");
    }

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
        return proxy.loadItemPositions();
    }

    public Device getState() {
        return Scheduler.getInstance().getState();
    }

    public void stopScheduler() {
        Scheduler.getInstance().stop();
    }

    public String test(Station device) {
        return "" + Scheduler.getInstance().test(device); //todo
    }
}
