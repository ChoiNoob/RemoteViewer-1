package com.damintsev.server.db;

import com.damintsev.client.old.devices.Item;
import com.damintsev.common.uientity.Label;
import com.damintsev.common.uientity.Station;
import com.damintsev.common.uientity.Task;
import com.damintsev.common.visitor.Visitor;
import com.damintsev.server.dao.StationDao;
import com.damintsev.server.dao.UiItemDao;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.*;

/**
 * User: adamintsev Date: 15.10.13 Time: 16:51
 */
@Deprecated
@Component
public class SaveItem implements Visitor<Item> {

    private static final Logger logger = Logger.getLogger(SaveItem.class);

    @Autowired
    DataSource dataSource;

    @Autowired
    private UiItemDao dao;

    @Autowired
    private StationDao stationDao;

      public Label visit(Label label) {
          logger.info("save Label id=" + label.getId() + " name=" + label.getName());
          Long id = dao.saveLabel(label);
          label.setId(id);
          return label;
    }

    public Station visit(Station station) {
        logger.info("saving Station");
        Long id = stationDao.saveStation(station);
        station.setId(id);
        return station;
    }

    public Task visit(Task task) {
        logger.info("saving Task id=" + task.getId() + " name=" + task.getName());
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = dataSource.getConnection();
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
                        "command=?, type=?, station_id=? WHERE id=?");
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
