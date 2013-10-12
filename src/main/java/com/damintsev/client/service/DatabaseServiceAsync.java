package com.damintsev.client.service;

import com.damintsev.client.devices.Item;
import com.damintsev.client.v3.items.Station;
import com.damintsev.client.v3.items.task.Task;
import com.damintsev.client.v3.items.task.TaskState;
import com.google.gwt.user.client.rpc.AsyncCallback;

import java.util.List;

public interface DatabaseServiceAsync {
    void saveTask(Task task, AsyncCallback<Long> async);

    void loadTask(Long id, AsyncCallback<Task> async);

    void loadUIItems(AsyncCallback<List<Item>> async);

    void saveStation(Station station, AsyncCallback<Station> async);

    void loadStation(Long id, AsyncCallback<Station> async);

    //todo
    void loadTaskStates(AsyncCallback<List<TaskState>> async);

    void deleteStation(Long id, AsyncCallback<Void> async);
}
