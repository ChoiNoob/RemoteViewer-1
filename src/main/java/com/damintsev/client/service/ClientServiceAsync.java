package com.damintsev.client.service;

import com.damintsev.client.devices.Device;
import com.damintsev.client.devices.Item;
import com.damintsev.client.devices.Station;
import com.damintsev.client.devices.TestResponse;
import com.google.gwt.user.client.rpc.AsyncCallback;

import java.util.List;

/**
 * Created by adamintsev
 * Date: 08.08.13 15:32
 */
public interface ClientServiceAsync {

    void saveItems(List<Item> items, AsyncCallback<Boolean> callback);

    void loadItems(AsyncCallback<List<Item>> callback);

    void getState(AsyncCallback<Device> callback);

    void stopScheduler(AsyncCallback<Void> callback);

    void test(Station device, AsyncCallback<TestResponse> callback);

    void startScheduler(AsyncCallback<Void> async);

    void checkDevice(Device device, AsyncCallback<Device> callback);
}
