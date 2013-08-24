package com.damintsev.client.service;

import com.damintsev.client.devices.*;
import com.damintsev.client.devices.enums.DeviceType;
import com.damintsev.client.devices.graph.BusyInfo;
import com.google.gwt.core.client.Callback;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.sencha.gxt.data.shared.loader.ListLoadResult;
import com.sencha.gxt.data.shared.loader.PagingLoadResult;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by adamintsev
 * Date: 08.08.13 15:32
 */
public interface ClientServiceAsync {

    void saveItems(List<Item> items, AsyncCallback<Boolean> callback);

    void loadItems(AsyncCallback<List<Item>> callback);


    void saveFTPSettings(FTPSettings settings, AsyncCallback<FTPSettings> async);

    void loadFTPSettings(Station station, AsyncCallback<FTPSettings> async);

    void stop(AsyncCallback<Void> async);

    void start(AsyncCallback<Void> async);

    void loadBusyInfo(Device device, AsyncCallback<BusyInfo> async);

    void deleteDevice(Device device, AsyncCallback<Void> async);

    void saveDevice(Device device, AsyncCallback<Device> async);

    void loadDevice(Long debiceId, DeviceType deviceType, AsyncCallback<Device> async);

    void getStatistisc(AsyncCallback<List<BillingStats>> async);

    void loadBusyInfoStatistics(Device device, AsyncCallback<List<BusyInfo>> async);

    void getStationList(AsyncCallback<PagingLoadResult<Station>> async);

    void getItemsState(AsyncCallback<List<Device>> async);

    void hardReset(AsyncCallback<Void> async);

    void savePrefix(TreeMap<String, String> prefixMap, AsyncCallback<Void> async);

    void loadPrefix(AsyncCallback<TreeMap<String, String>> async);
}