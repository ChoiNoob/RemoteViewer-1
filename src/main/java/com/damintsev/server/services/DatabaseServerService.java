package com.damintsev.server.services;

import com.damintsev.client.devices.Station;
import com.damintsev.client.devices.UIItem;
import com.damintsev.client.service.DatabaseService;
import com.damintsev.client.v3.items.DatabaseType;
import com.damintsev.client.v3.items.task.TaskType;
import com.damintsev.client.v3.uiitems.UIStation;
import com.damintsev.client.v3.uiitems.UITask;
import com.damintsev.client.v3.items.task.Task;
import com.damintsev.server.db.DB;
import com.damintsev.server.db.Mysql;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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

    public List<UIItem> loadUIItems() {
       return DB.getInstance().getUIItemList();
    }

    public Station loadStation(Long id) {
        return DB.getInstance().getStation(id);
    }

    public void saveStation(Station station) {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
