package com.damintsev.server.db;

import com.damintsev.client.old.devices.Item;
import com.damintsev.common.uientity.Label;
import com.damintsev.common.uientity.Station;
import com.damintsev.common.uientity.Task;
import com.damintsev.common.uientity.TaskType;
import com.damintsev.server.dao.StationDao;
import com.damintsev.server.dao.UiItemDao;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import javax.sql.DataSource;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
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
 * @deprecated
 */
@Component
@Deprecated
public class DB {

    private static final Logger logger = Logger.getLogger(DB.class);
    @Autowired
    private DataSource dataSource;
    @Autowired
    private SaveItem saveItem;
    @Autowired
    private UiItemDao newDao;
    @Autowired
    private StationDao stationDao;

    @Deprecated
    public List<Station> getStationList() {
        List<Station> stations = new ArrayList<Station>();
        String query = "SELECT * FROM station";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            ResultSet resultSet = statement.executeQuery();
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
        }
        return null;
    }

    @Deprecated
    public Station getStation(Long stationId) {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Station station = null;
        try (Connection connection = dataSource.getConnection()) {
            statement = connection.prepareStatement("SELECT * FROM station WHERE station_id = ?");
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
                station.setImage(resultSet.getLong("imageId"));
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
        try(Connection connection = dataSource.getConnection()) {
            statement = connection.prepareStatement("SELECT * FROM task t WHERE t.station_id = ?");
            statement.setLong(1, stationId);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Task task = new Task();
                task.setId(resultSet.getLong("id"));
                task.setName(resultSet.getString("name"));
                task.setCommand(resultSet.getString("command"));
                task.setType(TaskType.valueOf(resultSet.getString("type")));
                task.setStation(loadStationById(resultSet.getLong("station_id")));
                task.setImage(resultSet.getLong("imageId"));
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
        try(Connection connection = dataSource.getConnection()) {
            statement = connection.prepareStatement("SELECT * FROM task t WHERE t.id = ?");
            statement.setLong(1, taskId);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Task task = new Task();
                task.setId(resultSet.getLong("id"));
                task.setName(resultSet.getString("name"));
                task.setCommand(resultSet.getString("command"));
                task.setType(TaskType.valueOf(resultSet.getString("type")));
                task.setStation(loadStationById(resultSet.getLong("station_id")));
                task.setImage(resultSet.getLong("imageId"));
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
        try(Connection connection = dataSource.getConnection()) {
            statement = connection.prepareStatement("SELECT * FROM station WHERE station_id = ?");
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
                station.setDelay(resultSet.getInt("delay"));
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

    //todo переписать метод. возможно переделать архитектуру
    public List<Item> getUIItemList() {
        List<Item> uiItems = new ArrayList<Item>();
        Connection connection = null;
        PreparedStatement statement = null;
        try{
            connection = dataSource.getConnection();
            statement = connection.prepareStatement("SELECT * FROM uipositions");
            statement.execute();
            ResultSet resultSet = statement.getResultSet();
            while (resultSet.next()) {
                Item item;
                DatabaseType type = DatabaseType.valueOf(resultSet.getString("ref_type"));
                Long refId = resultSet.getLong("ref_id");
                int x = resultSet.getInt("x");
                int y = resultSet.getInt("y");
                switch (type) {
                    case TASK:
                        item = getTask(refId);
                        break;
                    case STATION:
                        item = stationDao.loadStation(refId);
                        break;
                    case LABEL:
                        item = getLabel(refId);
                        break;
                    default:throw new Exception();//todo сделать чтото а то не красиво
                }
                item.setPosition(x, y);
                uiItems.add(item);
            }
        }   catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return uiItems;
    }

    public void deleteStation(Station station) {
        Connection connection = null;
        PreparedStatement statement = null;
        try  {
            connection = dataSource.getConnection();
            List<Task> tasks = loadTasksForStation(station.getId());
            for (Task task : tasks) {
                deleteTask(task);
            }
            statement = connection.prepareStatement("DELETE FROM station WHERE station_id = ?");
            statement.setLong(1, station.getId());
            statement.executeUpdate();
            deleteFromUI(connection, station);

        } catch (SQLException e) {
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
            connection = dataSource.getConnection();

            statement = connection.prepareStatement("DELETE FROM task WHERE id = ?");
            statement.setLong(1, task.getId());
            statement.executeUpdate();

            deleteFromUI(connection, task);

        } catch (SQLException e) {
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

    @Deprecated
    public void saveImage(String type) {
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            BufferedImage image = null;//todo  = ImageHandler.getInstance().getImage();
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            ImageIO.write(image, "png", os);
            InputStream is = new ByteArrayInputStream(os.toByteArray());

            connection = dataSource.getConnection();
            statement = connection.prepareStatement("INSERT INTO images (TYPE, DATA) VALUES (?,?) " +
                    "ON DUPLICATE KEY UPDATE data = VALUES(DATA)");
            statement.setString(1, type);

            statement.setBinaryStream(2, is);
            statement.executeUpdate();
            is.close();
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
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

    public void saveItemPosition(List<Item> items) {
        logger.info("saving items position");
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = dataSource.getConnection();
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

    public void deleteLabel(Label label) {
        logger.info("delete Label id=" + label.getId() + " name=" + label.getName());
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = dataSource.getConnection();
                statement = connection.prepareStatement("DELETE FROM labels WHERE id = ?");
                statement.setLong(1, label.getId());
                statement.executeUpdate();
            deleteFromUI(connection, label);
        } catch (Exception e) {
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

    public Label getLabel(Long id) {
        logger.info("load Label id=" + id);
        return newDao.loadLabel(id);
    }

    public Item saveItem(Item item) {
        return item.accept(saveItem);
    }
}
