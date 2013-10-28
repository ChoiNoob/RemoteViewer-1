package com.damintsev.server.services;

import com.damintsev.client.old.devices.Item;
import com.damintsev.common.beans.Label;
import com.damintsev.common.beans.Station;
import com.damintsev.client.service.DatabaseService;
import com.damintsev.common.beans.TaskState;
import com.damintsev.common.beans.Task;
import com.damintsev.server.db.DB;
import com.damintsev.server.v2.v3.SoA;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.sencha.gxt.data.shared.loader.PagingLoadResult;
import com.sencha.gxt.data.shared.loader.PagingLoadResultBean;

import java.util.ArrayList;
import java.util.List;

/**
 * User: Damintsev Andrey
 * Date: 06.10.13
 * Time: 11:24
 */
public class DatabaseServerService extends RemoteServiceServlet implements DatabaseService {

    public DatabaseServerService() {
        SoA.getInstance();
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

    public void deleteStation(Station station) {
        DB.getInstance().deleteStation(station);
        SoA.getInstance().updateEvent(station);
    }

    public List<TaskState> loadTaskStates() {
        return new ArrayList<TaskState>(SoA.getInstance().getStates().values());
    }

    public void saveItemPosition(List<Item> items) {
        DB.getInstance().saveItemPosition(items);
    }

    public void deleteTask(Task task) {
        DB.getInstance().deleteTask(task);
        SoA.getInstance().updateEvent(task);
    }

    public PagingLoadResult<Station> getStationList() {
        List<Station> stations = DB.getInstance().getStationList();   //todo мб можно обойтись одной строкой
        return new PagingLoadResultBean<Station>(stations, 0, stations.size());
    }

    public void deleteLabel(Label label) {
        DB.getInstance().deleteLabel(label);
    }

    public Label loadLabel(Long id) {
        return DB.getInstance().getLabel(id);
    }

    public Item saveItem(Item item) {
        item = DB.getInstance().saveItem(item);
        SoA.getInstance().updateEvent(item);
        return item;
    }

    public void saveImage(String type) {
        DB.getInstance().saveImage(type);
    }
}
