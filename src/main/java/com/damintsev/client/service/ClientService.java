package com.damintsev.client.service;

import com.damintsev.client.devices.Device;
import com.damintsev.client.devices.Item;
import com.damintsev.client.devices.Station;
import com.damintsev.client.devices.TestResponse;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import java.util.List;


/**
 * User: Damintsev Andrey
 * Date: 08.08.13
 * Time: 0:19
 */
@RemoteServiceRelativePath("service")
public interface ClientService extends RemoteService {

    public Boolean saveItems(List<Item> items);

    public List<Item> loadItems();

    public Device getState();

    public void stopScheduler();

    public void startScheduler();

    public TestResponse test(Station device);

    public Device checkDevice(Device device);
}

