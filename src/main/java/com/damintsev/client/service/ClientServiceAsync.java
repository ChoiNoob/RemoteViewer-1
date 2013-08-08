package com.damintsev.client.service;

import com.damintsev.client.dao.Item;
import com.google.gwt.user.client.rpc.AsyncCallback;

import java.util.List;

/**
 * Created by adamintsev
 * Date: 08.08.13 15:32
 */
public interface ClientServiceAsync {
    void getMessage(AsyncCallback<String> callback);

    void saveItems(List<Item> items, AsyncCallback<Boolean> callback);

    void loadItems(AsyncCallback<List<Item>> callback);
}
