package com.damintsev.server.db;

import com.damintsev.client.old.devices.Item;
import com.damintsev.common.beans.Label;
import com.damintsev.common.beans.Station;
import com.damintsev.common.beans.Task;
import com.damintsev.common.visitor.Visitor;
import org.slf4j.LoggerFactory;

import java.sql.*;

/**
 * User: adamintsev Date: 15.10.13 Time: 16:51
 */
public class SaveItem implements Visitor<Item> {

    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(SaveItem.class);

      public Label visit(Label label) {
          logger.info("save Label id=" + label.getId() + " name=" + label.getName());
          ResultSet resultSet = null;
          Connection connection = null;
          PreparedStatement statement = null;
          try {
              connection = Mysql.getConnection();
              if (label.getId() == null) {
                  statement = connection.prepareStatement("INSERT INTO labels (name) values (?)", Statement.RETURN_GENERATED_KEYS);
                  statement.setString(1, label.getName());
                  statement.executeUpdate();
                  resultSet = statement.getGeneratedKeys();
                  if (resultSet.next())
                      label.setId(resultSet.getLong(1));
              } else {
                  statement = connection.prepareStatement("UPDATE labels SET name=? WHERE id=?");
                  statement.setString(1, label.getName());
                  statement.setLong(2, label.getId());

                  statement.executeUpdate();
              }
          } catch (Exception e) {
              logger.error(e.getMessage(), e);
              e.printStackTrace();
          } finally {
              try {
                  if (resultSet != null)
                      resultSet.close();
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
          return label;
    }

    public Station visit(Station station) {
        logger.info("saving Station");
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = Mysql.getConnection();
            if (station.getId() == null) {
                statement = connection.prepareStatement("INSERT INTO station(comment,deviceType,host,login,name,password,port,allowStatistics, delay) " +
                        "VALUES (?,?,?,?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
                statement.setString(1, station.getComment());
                statement.setString(2, "null");//todo избавиться от этой строчки
                statement.setString(3, station.getHost());
                statement.setString(4, station.getLogin());
                statement.setString(5, station.getName());
                statement.setString(6, station.getPassword());
                statement.setString(7, station.getPort());
                statement.setBoolean(8, station.getAllowStatistics());
                statement.setInt(9, station.getDelay() == null ? 5 : station.getDelay());

                statement.executeUpdate();
                resultSet = statement.getGeneratedKeys();
                if (resultSet.next()) {
                    station.setId(resultSet.getLong(1));
                }
                resultSet.close();
            } else {
                statement = connection.prepareStatement("UPDATE station SET comment=?, " +
                        "host=?, login=?, name=?, password=?, port=?, allowStatistics=?, delay=? WHERE station_id=?");
                statement.setString(1, station.getComment());
                statement.setString(2, station.getHost());
                statement.setString(3, station.getLogin());
                statement.setString(4, station.getName());
                statement.setString(5, station.getPassword());
                statement.setString(6, station.getPort());
                statement.setBoolean(7, station.getAllowStatistics());
                statement.setInt(8, station.getDelay() == null ? 5 : station.getDelay());
                statement.setLong(9, station.getId());
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

    public Task visit(Task task) {
        logger.info("saving Task id=" + task.getId() + " name=" + task.getName());
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
