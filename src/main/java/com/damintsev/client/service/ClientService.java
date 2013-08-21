package com.damintsev.client.service;

import com.damintsev.client.devices.*;
import com.damintsev.client.devices.enums.DeviceType;
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

    public FTPSettings saveFTPSettings(FTPSettings settings);

    public FTPSettings loadFTPSettings(Station station);

    public void deleteDevice(Device device);

    public BusyInfo loadBusyInfo(Device device);

    public void testFTP();

    public Device saveDevice(Device device);

    public Device loadDevice(Long debiceId, DeviceType deviceType);
    
    public List<BillingStats> getStatistisc();
}

