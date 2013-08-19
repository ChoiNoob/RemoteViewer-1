package com.damintsev.client.service;

import com.damintsev.client.devices.*;
import com.damintsev.client.devices.graph.BusyInfo;
import com.damintsev.utils.ListLoadResultImpl;
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

    public Device checkDevice(Device device);

    public void start();

    public void stop();

    public void deleteItem(Device device);

    public ListLoadResultImpl<BillingInfo> getBillingInfo();

    public void saveFTPSettings(FTPSettings settings);

    public FTPSettings loadFTPSettings(Station station);

    public void deleteDevice(Device device);

    public List<BusyInfo> loadBusyInfo(Device device);
}

