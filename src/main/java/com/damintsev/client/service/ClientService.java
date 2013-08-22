package com.damintsev.client.service;

import com.damintsev.client.devices.*;
import com.damintsev.client.devices.enums.DeviceType;
import com.damintsev.client.devices.graph.BusyInfo;
import com.google.gwt.core.client.Callback;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.sencha.gxt.data.shared.loader.ListLoadResult;
import com.sencha.gxt.data.shared.loader.PagingLoadResult;

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

    public FTPSettings saveFTPSettings(FTPSettings settings);

    public FTPSettings loadFTPSettings(Station station);

    public void deleteDevice(Device device);

    public BusyInfo loadBusyInfo(Device device);

    public List<BusyInfo> loadBusyInfoStatistics(Device device);

    public void testFTP();

    public Device saveDevice(Device device);

    public Device loadDevice(Long debiceId, DeviceType deviceType);
    
    public List<BillingStats> getStatistisc();

    public PagingLoadResult<Station> getStationList() throws Exception;
}

