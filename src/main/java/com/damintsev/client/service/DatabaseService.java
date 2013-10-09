package com.damintsev.client.service;

import com.damintsev.client.devices.Station;
import com.damintsev.client.v3.items.task.Task;
import com.damintsev.client.v3.uiitems.UIItem;
import com.google.gwt.user.client.rpc.RemoteService;

import java.util.List;

/**
 * User: Damintsev Andrey
 * Date: 06.10.13
 * Time: 11:23
 */
public interface DatabaseService extends RemoteService {

    Long saveTask(Task task);
    Task loadTask(Long id);
    List<UIItem> loadUIItems();
    Station loadStation(Long id);
    void saveStation(Station station);

}
