package com.damintsev.server.db;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * User: Damintsev Andrey
 * Date: 19.08.13
 * Time: 2:01
 */
public class CleanManager {

    private static CleanManager instance;
    private static final Logger logger = LoggerFactory.getLogger(CleanManager.class);

    public static CleanManager getInstance() {
        if(instance == null) instance = new CleanManager();
        return instance;
    }

    private CleanManager() {
    }

    public void cleanBusyInfo() {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = Mysql.getConnection();
            statement = connection.prepareStatement("SELECT id FROM busyinfo WHERE date < ? ");
            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.DAY_OF_MONTH, -1);
            statement.setTimestamp(1, new Timestamp(cal.getTimeInMillis()));
            ResultSet resultSet = statement.executeQuery();
            List<Long> itemToDelete = new ArrayList<Long>();
            while (resultSet.next()) {
                itemToDelete.add(resultSet.getLong("id"));
            }
            for(Long deleteId : itemToDelete) {
                statement = connection.prepareStatement("DELETE FROM busyinfo WHERE id = ?");
                statement.setLong(1, deleteId);
                statement.executeUpdate();
            }


            resultSet.close();
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
}
