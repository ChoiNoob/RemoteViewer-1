package com.damintsev.gwt.client.source.v3.utilities;

import com.damintsev.gwt.client.source.uientity.Item;
import com.damintsev.gwt.client.source.uiitems.UIItem;
import com.damintsev.gwt.client.source.service.Service;
import com.damintsev.gwt.client.source.v3.pages.frames.MonitoringFrame;
import com.google.gwt.user.client.rpc.AsyncCallback;

import java.util.ArrayList;
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
        Service.instance.loadUIItems(new AsyncCallback<List<Item>>() {
            public void onFailure(Throwable caught) {
                //todo!!
            }

            public void onSuccess(List<Item> result) {
                for(Item item : result) {
                    System.out.println("res=" + item + " id=" + item.getId());
                    MonitoringFrame.get().add(item);
                    MonitoringFrame.get().start();
                }
            }
        });
    }

    public void saveUIItems(Collection<UIItem> uiItems) {
        List<Item> items = new ArrayList<Item>();
        for(UIItem uiItem : uiItems) {
            items.add(uiItem.getItem());
        }
        Service.instance.saveItemPosition(items, new AsyncCallback<Void>() {
            public void onFailure(Throwable caught) {
                //todo!!
            }

            public void onSuccess(Void result) {
                //todo todo !?!? ^)
            }
        });
    }
}
