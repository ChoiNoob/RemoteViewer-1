package com.damintsev.server.services;

import com.damintsev.client.devices.Item;
import com.damintsev.client.v3.items.Station;
import com.damintsev.client.service.DatabaseService;
import com.damintsev.client.v3.items.task.TaskState;
import com.damintsev.client.v3.items.task.Task;
import com.damintsev.server.db.DB;
import com.damintsev.server.v2.v3.SoA;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import java.util.ArrayList;
import java.util.List;

/**
 * User: Damintsev Andrey
 * Date: 06.10.13
 * Time: 11:24
 */
public class DatabaseServerService extends RemoteServiceServlet implements DatabaseService {

    public Long saveTask(Task task) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public Task loadTask(Long id) {
        return DB.getInstance().getTask(id);
    }

    public List<Item> loadUIItems() {
       return DB.getInstance().getUIItemList();
    }

    public Station loadStation(Long id) {
        return DB.getInstance().getStation(id);
    }

    public Station saveStation(Station station) {
       return null;
    }

    public void deleteStation(Long id) {
        DB.getInstance().deleteStation(id);
    }

    public List<TaskState> loadTaskStates() {
        return new ArrayList<TaskState>(SoA.getInstance().getStates().values());
    }
}
