package com.damintsev.server.db;

import com.damintsev.client.devices.Item;
import com.damintsev.client.v3.items.Station;
import com.damintsev.client.v3.items.DatabaseType;
import com.damintsev.client.v3.items.task.ImageWithType;
import com.damintsev.client.v3.items.task.Task;
import com.damintsev.client.v3.items.task.TaskType;
import org.slf4j.LoggerFactory;

import java.sql.*;
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
        return loadTasksForStation(station.getId());
    }

        public List<Task> loadTasksForStation(Long stationId) {
        List<Task> tasks = new ArrayList<Task>();
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Connection connection;
        try {
            connection = Mysql.getConnection();
            statement = connection.prepareStatement("SELECT * FROM Task t WHERE t.station_id = ?");
            statement.setLong(1, stationId);
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

    public List<Item> getUIItemList() {
        List<Item> uiItems = new ArrayList<Item>();
        PreparedStatement statement;
        try{
            statement = Mysql.getConnection().prepareStatement("SELECT * FROM uipositions");
            statement.execute();
            ResultSet resultSet = statement.getResultSet();
            while (resultSet.next()) {
                Item item = null;
                DatabaseType type = DatabaseType.valueOf(resultSet.getString("ref_type"));
                Long refId = resultSet.getLong("ref_id");
                int x = resultSet.getInt("x");
                int y = resultSet.getInt("y");
                switch (type) {
                    case TASK:
                        item = getTask(refId);
                        break;
                    case STATION:
                        item = getStation(refId);
                        break;
                    default:item=new Task();//todo сделать чтото а то не красиво
                }
                item.setPosition(x, y);
                uiItems.add(item);
            }
        }   catch (Exception e) {
            e.printStackTrace();
        }
        return uiItems;
    }

    public void deleteStation(Station station) {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = Mysql.getConnection();
            List<Task> tasks = loadTasksForStation(station.getId());
            for (Task task : tasks) {
                deleteTask(task);
            }
            statement = connection.prepareStatement("DELETE FROM station WHERE station_id = ?");
            statement.setLong(1, station.getId());
            statement.executeUpdate();
//            statement = connection.prepareStatement("DELETE FROM uipositions " +
//                    "WHERE ref_type = 'STATION' " +
//                    "AND ref_id = ? ");
//            statement.setLong(1, station.getId());
//            statement.executeUpdate();
            deleteFromUI(connection, station);

        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            logger.error(e.getMessage(), e);
            e.printStackTrace();
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private void deleteFromUI(Connection connection, Item item) {
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement("DELETE FROM uipositions " +
                    "WHERE ref_type = ? " +
                    "AND ref_id = ? ");
            statement.setString(1, item.getClass().getSimpleName().toUpperCase());
            statement.setLong(2, item.getId());
            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void deleteTask(Task task) {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = Mysql.getConnection();

            statement = connection.prepareStatement("DELETE FROM task WHERE id = ?");
            statement.setLong(1, task.getId());
            statement.executeUpdate();

            deleteFromUI(connection, task);

        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            logger.error(e.getMessage(), e);
            e.printStackTrace();
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public List<ImageWithType> loadImages() {
        Connection connection = null;
        PreparedStatement statement = null;
        List<ImageWithType> images = new ArrayList<ImageWithType>();
        try {
            connection = Mysql.getConnection();
            statement = connection.prepareStatement("SELECT * FROM images");
            statement.execute();
            ResultSet resultSet = statement.getResultSet();
            while (resultSet.next()) {
                ImageWithType imageWithType = new ImageWithType();
                imageWithType.setType(resultSet.getString("type"));
                imageWithType.setData(resultSet.getString("data"));
                images.add(imageWithType);
            }
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            logger.error(e.getMessage(), e);
            e.printStackTrace();
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return images;
    }

    public Station saveStation(Station station) {
        logger.info("saving Station");
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = Mysql.getConnection();
            if (station.getId() == null) {
                statement = connection.prepareStatement("INSERT INTO station(comment,deviceType,host,login,name,password,port,allowStatistics) " +
                        "VALUES (?,?,?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
                statement.setString(1, station.getComment());
                statement.setString(2, "null");//todo избавиться от этой строчки
                statement.setString(3, station.getHost());
                statement.setString(4, station.getLogin());
                statement.setString(5, station.getName());
                statement.setString(6, station.getPassword());
                statement.setString(7, station.getPort());
                statement.setBoolean(8, station.getAllowStatistics());

                statement.executeUpdate();
                resultSet = statement.getGeneratedKeys();
                if (resultSet.next()) {
                    station.setId(resultSet.getLong(1));
                }
                resultSet.close();
            } else {
                statement = connection.prepareStatement("UPDATE station SET comment=?, " +
                        "host=?, login=?, name=?, password=?, port=?, allowStatistics=? WHERE station_id=?");
                statement.setString(1, station.getComment());
                statement.setString(2, station.getHost());
                statement.setString(3, station.getLogin());
                statement.setString(4, station.getName());
                statement.setString(5, station.getPassword());
                statement.setString(6, station.getPort());
                statement.setBoolean(7, station.getAllowStatistics());
                statement.setLong(8, station.getId());
                statement.executeUpdate();
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
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return station;
    }

    public void saveItemPosition(List<Item> items) {
        logger.info("saving items position");
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = Mysql.getConnection();
            statement = connection.prepareStatement("INSERT INTO uipositions (x, y, ref_id, ref_type)" +
                    "VALUES (?,?,?,?) " +
                    "ON DUPLICATE KEY UPDATE x = VALUES(x), y = VALUES(y)");
            for(Item item : items) {
                statement.setInt(1, item.getPosition().x);
                statement.setInt(2, item.getPosition().y);
                statement.setLong(3, item.getId());
                statement.setString(4, item.getClass().getSimpleName().toUpperCase());
                statement.addBatch();
            }
            statement.executeBatch();
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
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public Task saveTask(Task task) {
        logger.info("saving Tesk");
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = Mysql.getConnection();
            if (task.getId() == null) {
                statement = connection.prepareStatement("INSERT INTO task(name,command,type,station_id) " +
                        "VALUES (?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
                statement.setString(1, task.getName());
                statement.setString(2, task.getCommand());
                statement.setString(3, task.getType().toString());
                statement.setLong(4, task.getStation().getId());

                statement.executeUpdate();
                resultSet = statement.getGeneratedKeys();
                if (resultSet.next()) {
                    task.setId(resultSet.getLong(1));
                }
                resultSet.close();
            } else {
                statement = connection.prepareStatement("UPDATE task SET name=?, " +
                        "command=?, type=?, station_id=? WHERE station_id=?");
                statement.setString(1, task.getName());
                statement.setString(2, task.getCommand());
                statement.setString(3, task.getType().toString());
                statement.setLong(4, task.getStation().getId());
                statement.setLong(5, task.getId());
                statement.executeUpdate();
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
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return task;
    }
}
