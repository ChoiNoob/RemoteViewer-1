package com.damintsev.client.service;

import com.damintsev.client.devices.Item;
import com.damintsev.client.v3.items.Station;
import com.damintsev.client.v3.items.task.Task;
import com.damintsev.client.v3.items.task.TaskState;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import java.util.List;

/**
 * User: Damintsev Andrey
 * Date: 06.10.13
 * Time: 11:23
 */
@RemoteServiceRelativePath("DatabaseServerService")
public interface DatabaseService extends RemoteService {

    Long saveTask(Task task);
    Task loadTask(Long id);
    List<Item> loadUIItems();
    Station loadStation(Long id);
    Station saveStation(Station station);
    void deleteStation(Long id);


    //todo
    List<TaskState> loadTaskStates();
}
