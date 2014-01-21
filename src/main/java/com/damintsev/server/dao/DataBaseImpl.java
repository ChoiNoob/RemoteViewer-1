package com.damintsev.server.dao;

import com.damintsev.common.beans.Station;
import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * User: adamintsev
 * Date: 21.01.14
 * //todo написать комментарии
 */
@Repository
public class DataBaseImpl implements DataBase {

    @Autowired
    private DataSource dataSource;

    public List<Station> getStationList() {
        List<Station> stations = new ArrayList<Station>();
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Connection connection;
        try {
            connection = dataSource.getConnection();
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
//            logger.error(e.getMessage(), e);
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


    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }
}
