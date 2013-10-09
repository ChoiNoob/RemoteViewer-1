package com.damintsev.server.db;

import com.damintsev.client.devices.Station;
import com.damintsev.client.devices.enums.Status;
import com.damintsev.client.v3.items.DatabaseType;
import com.damintsev.client.v3.items.task.Task;
import com.damintsev.client.v3.items.task.TaskType;
import com.damintsev.client.v3.uiitems.UIItem;
import com.damintsev.client.v3.uiitems.UIStation;
import com.damintsev.client.v3.uiitems.UITask;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * User: Damintsev Andrey
 * Date: 09.10.13
 * Time: 0:25
 */
public class DB {

    private static DB instance;
    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(DB.class);

    public static DB getInstance() {
        if (instance == null) instance = new DB();
        return instance;
    }

    public List<Station> getStationList() {
        List<Station> stations = new ArrayList<Station>();
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Connection connection;
        try {
            connection = Mysql.getConnection();
            statement = connection.prepareStatement("SELECT * FROM station");
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Station station = new Station();
                station.setId(resultSet.getLong("station_id"));
                station.setComment(resultSet.getString("comment"));
                station.setHost(resultSet.getString("host"));
                station.setPort(resultSet.getString("port"));
                station.setLogin(resultSet.getString("login"));
                station.setPassword(resultSet.getString("password"));
                station.setName(resultSet.getString("name"));
                stations.add(station);
            }
            return stations;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (statement != null) {
                    statement.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public Station getStation(Long stationId) {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Station station = null;
        try {
            statement = Mysql.getConnection().prepareStatement("SELECT * FROM station WHERE station_id = ?");
            statement.setLong(1, stationId);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                station = new Station();
                station.setId(resultSet.getLong("station_id"));
                station.setComment(resultSet.getString("comment"));
                station.setHost(resultSet.getString("host"));
                station.setPort(resultSet.getString("port"));
                station.setLogin(resultSet.getString("login"));
                station.setPassword(resultSet.getString("password"));
                station.setName(resultSet.getString("name"));
                station.setAllowStatistics(resultSet.getBoolean("allowStatistics"));
            }
            return station;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (statement != null) {
                    statement.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public List<Task> loadTasksForStation(Station station) {
        List<Task> tasks = new ArrayList<Task>();
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Connection connection;
        try {
            connection = Mysql.getConnection();
            statement = connection.prepareStatement("SELECT * FROM Task t WHERE t.station_id = ?");
            statement.setLong(1, station.getId());
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Task task = new Task();
                task.setId(resultSet.getLong("id"));
                task.setName(resultSet.getString("name"));
                task.setCommand(resultSet.getString("command"));
                task.setType(TaskType.valueOf(resultSet.getString("type")));
                task.setStation(loadStationById(resultSet.getLong("station_id")));
                tasks.add(task);
            }
            return tasks;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (statement != null) {
                    statement.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public Task getTask(Long taskId) {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Connection connection;
        try {
            connection = Mysql.getConnection();
            statement = connection.prepareStatement("SELECT * FROM Task t WHERE t.id = ?");
            statement.setLong(1, taskId);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Task task = new Task();
                task.setId(resultSet.getLong("id"));
                task.setName(resultSet.getString("name"));
                task.setCommand(resultSet.getString("command"));
                task.setType(TaskType.valueOf(resultSet.getString("type")));
                task.setStation(loadStationById(resultSet.getLong("station_id")));
                return task;
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (statement != null) {
                    statement.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    private Station loadStationById(Long stationId) {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Station station = null;
        try {
            statement = Mysql.getConnection().prepareStatement("SELECT * FROM station WHERE station_id = ?");
            statement.setLong(1, stationId);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                station = new Station();
                station.setId(resultSet.getLong("station_id"));
                station.setComment(resultSet.getString("comment"));
                station.setHost(resultSet.getString("host"));
                station.setPort(resultSet.getString("port"));
                station.setLogin(resultSet.getString("login"));
                station.setPassword(resultSet.getString("password"));
                station.setName(resultSet.getString("name"));
                station.setAllowStatistics(resultSet.getBoolean("allowStatistics"));
            }
            return station;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (statement != null) {
                    statement.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public List<UIItem> getUIItemList() {
        List<UIItem> uiItems = new ArrayList<UIItem>();
        PreparedStatement statement;
        try{
            statement = Mysql.getConnection().prepareStatement("SELECT * FROM uipositions");
            statement.execute();
            ResultSet resultSet = statement.getResultSet();
            while (resultSet.next()) {
                UIItem item = null;
                DatabaseType type = DatabaseType.valueOf(resultSet.getString("ref_type"));
                Long refId = resultSet.getLong("ref_id");
                int x = resultSet.getInt("x");
                int y = resultSet.getInt("y");
                switch (type) {
                    case TASK:
                        item = new UITask();
                        ((UITask)item).setTask(getTask(refId));
                        item.setPosition(x,y);
                        break;
                    case STATION:
                        item = new UIStation();
                        ((UIStation)item).setStation(getStation(refId));
                        item.setPosition(x,y);
                        break;
                }
                uiItems.add(item);
            }
        }   catch (Exception e) {
            e.printStackTrace();
        }
        return uiItems;
    }
}
