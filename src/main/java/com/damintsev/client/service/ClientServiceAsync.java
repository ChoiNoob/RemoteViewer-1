package com.damintsev.client.service;

import com.damintsev.client.devices.*;
import com.damintsev.utils.ListLoadResultImpl;
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


    void checkDevice(Device device, AsyncCallback<Device> callback);

    void getBillingInfo(AsyncCallback<ListLoadResultImpl<BillingInfo>> async);

    void saveFTPSettings(FTPSettings settings, AsyncCallback<Void> async);

    void loadFTPSettings(AsyncCallback<FTPSettings> async);

    void stop(AsyncCallback<Void> async);

    void start(AsyncCallback<Void> async);
}