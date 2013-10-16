package com.damintsev.server.services;

import com.damintsev.client.devices.Item;
import com.damintsev.server.v2.v3.SaveItem;
import com.damintsev.client.v3.items.visitor.Visitor;
import com.damintsev.client.v3.items.visitor.UIVisitor;
import com.damintsev.client.v3.items.Label;
import com.damintsev.client.v3.items.Station;
import com.damintsev.client.service.DatabaseService;
import com.damintsev.client.v3.items.task.TaskState;
import com.damintsev.client.v3.items.task.Task;
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
    SoA soA;
    public DatabaseServerService() {
        SoA.getInstance();
    }

//    public Task saveTask(final Task task) {
//              new Thread(new Runnable() {
//                  public void run() {
//                      SoA.getInstance().updateTask(task);
//                  }
//              }).start();
//        return DB.getInstance().saveTask(task);
//    }

    public Task loadTask(Long id) {
        return DB.getInstance().getTask(id);
    }

    public List<Item> loadUIItems() {
       return DB.getInstance().getUIItemList();
    }

    public Station loadStation(Long id) {
        return DB.getInstance().getStation(id);
    }

//    public Station saveStation(Station station) {
//       SoA.getInstance().updateStation(station);
//       return DB.getInstance().saveStation(station);
//    }

    public void deleteStation(Station station) {
        SoA.getInstance().deleteStation(station);
        DB.getInstance().deleteStation(station);
    }

    public List<TaskState> loadTaskStates() {
        return new ArrayList<TaskState>(SoA.getInstance().getStates().values());
    }

    public void saveItemPosition(List<Item> items) {
        DB.getInstance().saveItemPosition(items);
    }

    public void deleteTask(Task task) {
        //todo delete from worker
        DB.getInstance().deleteTask(task);
    }

    public PagingLoadResult<Station> getStationList() {
                List<Station> stations = DB.getInstance().getStationList();   //todo мб можно обойтись одной строкой
        return new PagingLoadResultBean<Station>(stations, 0, stations.size());
    }

    public void deleteLabel(Label label) {
        DB.getInstance().deleteLabel(label);
    }

//    public Label saveLabel(Label label) {
//        return DB.getInstance().saveLabel(label);
//    }

    public Label loadLabel(Long id) {
        return DB.getInstance().getLabel(id);
    }

    public Item saveItem(Item item) {
        return DB.getInstance().saveItem(item);
    }

    public void saveImage(String type) {
        DB.getInstance().saveImage(type);
    }
}
