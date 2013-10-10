package com.damintsev.client.service;

import com.damintsev.client.devices.Station;
import com.damintsev.client.devices.UIItem;
import com.damintsev.client.v3.items.task.Task;
import com.google.gwt.user.client.rpc.AsyncCallback;

import java.util.List;

public interface DatabaseServiceAsync {
    void saveTask(Task task, AsyncCallback<Long> async);

    void loadTask(Long id, AsyncCallback<Task> async);

    void loadUIItems(AsyncCallback<List<UIItem>> async);

    void saveStation(Station station, AsyncCallback<Void> async);

    void loadStation(Long id, AsyncCallback<Station> async);
}
