package com.damintsev.server.services;

import com.damintsev.client.devices.Item;
import com.damintsev.client.service.ClientService;
import com.damintsev.server.db.DatabaseProxy;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import java.util.List;

/**
 * User: Damintsev Andrey
 * Date: 08.08.13
 * Time: 0:22
 */
public class ServerService extends RemoteServiceServlet implements ClientService {

    public Boolean saveItems(List<Item> items) {
        for(Item item : items) {
            System.out.println("name=" + item.getName());
            System.out.println("x=" + item.getCoordX());
            System.out.println("y=" + item.getCoordY());
        }
        DatabaseProxy proxy = new DatabaseProxy();
        proxy.saveItems(items);
        return true;
    }

    public List<Item> loadItems() {
        DatabaseProxy proxy = new DatabaseProxy();
        return proxy.loadItemPositions();
    }
}
