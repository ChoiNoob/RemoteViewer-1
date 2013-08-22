package com.damintsev.client.service;

import com.damintsev.client.devices.*;
import com.damintsev.client.devices.enums.DeviceType;
import com.damintsev.client.devices.graph.BusyInfo;
import com.google.gwt.core.client.Callback;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.sencha.gxt.data.shared.loader.ListLoadResult;
import com.sencha.gxt.data.shared.loader.PagingLoadResult;

import java.util.List;

/**
 * Created by adamintsev
 * Date: 08.08.13 15:32
 */
public interface ClientServiceAsync {

    void saveItems(List<Item> items, AsyncCallback<Boolean> callback);

    void loadItems(AsyncCallback<List<Item>> callback);

    void getState(AsyncCallback<Device> callback);

    void checkDevice(Device device, AsyncCallback<Device> callback);

    void saveFTPSettings(FTPSettings settings, AsyncCallback<FTPSettings> async);

    void loadFTPSettings(Station station, AsyncCallback<FTPSettings> async);

    void stop(AsyncCallback<Void> async);

    void start(AsyncCallback<Void> async);

    void deleteItem(Device device, AsyncCallback<Void> async);

    void loadBusyInfo(Device device, AsyncCallback<BusyInfo> async);

    void deleteDevice(Device device, AsyncCallback<Void> async);

    void testFTP(AsyncCallback<Void> async);

    void saveDevice(Device device, AsyncCallback<Device> async);

    void loadDevice(Long debiceId, DeviceType deviceType, AsyncCallback<Device> async);

    void getStatistisc(AsyncCallback<List<BillingStats>> async);

    void loadBusyInfoStatistics(Device device, AsyncCallback<List<BusyInfo>> async);

    void getStationList(AsyncCallback<PagingLoadResult<Station>> async);
}