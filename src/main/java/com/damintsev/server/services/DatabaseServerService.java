package com.damintsev.server.services;

import com.damintsev.client.old.devices.Item;
import com.damintsev.client.service.DatabaseService;
import com.damintsev.common.uientity.Label;
import com.damintsev.common.uientity.Station;
import com.damintsev.common.uientity.Task;
import com.damintsev.common.uientity.TaskState;
import com.damintsev.server.db.DB;
import com.damintsev.server.v2.v3.Executor;
import com.sencha.gxt.data.shared.loader.PagingLoadResult;
import com.sencha.gxt.data.shared.loader.PagingLoadResultBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * User: Damintsev Andrey
 * Date: 06.10.13
 * Time: 11:24
 */
@Service
public class DatabaseServerService implements DatabaseService {

    @Autowired
    private DB db;
    @Autowired
    private Executor executor;

    public Task loadTask(Long id) {
        return db.getTask(id);
    }

    public List<Item> loadUIItems() {
            return db.getUIItemList();
    }

    public Station loadStation(Long id) {
        return db.getStation(id);
    }

    public void deleteStation(Station station) {
        db.deleteStation(station);
        executor.updateEvent(station);
    }

    public List<TaskState> loadTaskStates() {
        return new ArrayList<>(executor.getStates().values());
    }

    public void saveItemPosition(List<Item> items) {
        db.saveItemPosition(items);
    }

    public void deleteTask(Task task) {
        db.deleteTask(task);
        executor.updateEvent(task);
    }

    public PagingLoadResult<Station> getStationList() {
        List<Station> stations = db.getStationList();   //todo мб можно обойтись одной строкой
        return new PagingLoadResultBean<Station>(stations, 0, stations.size());
    }

    public void deleteLabel(Label label) {
        db.deleteLabel(label);
    }

    public Label loadLabel(Long id) {
        return db.getLabel(id);
    }

    public Item saveItem(Item item) {
        item = db.saveItem(item);
        executor.updateEvent(item);
        return item;
    }

    public void saveImage(String type) {
        db.saveImage(type);
    }
}
