package com.damintsev.client.v3.utilities;

import com.damintsev.client.devices.Item;
import com.damintsev.client.devices.UIItem;
import com.damintsev.client.service.Service;
import com.damintsev.client.service.Service2;
import com.damintsev.client.v3.pages.frames.MonitoringFrame;
import com.google.gwt.user.client.rpc.AsyncCallback;

import java.util.Collection;
import java.util.List;

/**
 * User: Damintsev Andrey
 * Date: 10.10.13
 * Time: 23:08
 */
public class DataLoader {

    private static DataLoader instance;

    public static DataLoader getInstance() {
        if(instance == null) instance = new DataLoader();
        return instance;
    }

    public void load() {
        Service2.database.loadUIItems(new AsyncCallback<List<Item>>() {
            public void onFailure(Throwable caught) {
                //todo!!
            }

            public void onSuccess(List<Item> result) {
                for(Item item : result) {
                    MonitoringFrame.get().addItem(item);
                }
            }
        });
    }

    public void saveUIItems(Collection<UIItem> values) {
        //todo
    }
}
