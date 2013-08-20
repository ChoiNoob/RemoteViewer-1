package com.damintsev.server.db.xmldao;

import com.damintsev.client.devices.*;
import com.damintsev.client.devices.enums.DeviceType;
import com.damintsev.client.devices.enums.Status;
import com.damintsev.client.devices.graph.BusyInfo;
import com.damintsev.server.db.Mysql;
import com.damintsev.server.services.ServerService;
import org.slf4j.LoggerFactory;

import javax.sql.rowset.serial.SerialArray;
import java.sql.*;
import java.sql.Date;
import java.util.*;

/**
 * User: Damintsev Andrey
 * Date: 19.08.13
 * Time: 23:36
 */
public class DatabaseConnector {

    private static DatabaseConnector instance;
    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(DatabaseConnector.class);

    public static DatabaseConnector getInstance() {
        if (instance == null) instance = new DatabaseConnector();
        return instance;
    }

    public Device saveDevice(Device device) {
        logger.info("Save device");
        if (device instanceof Station) {
            return saveStation((Station) device);
        } else {
            return saveCommonDevice((CommonDevice) device);
        }
    }

    private Device saveCommonDevice(CommonDevice device) {
        Connection connection = Mysql.getConnection();
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            if (device.getId() == null) {
                statement = connection.prepareStatement("INSERT INTO device (query, queryBusy, name, comment, deviceType, station_id) " +
                        "values (?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
                statement.setString(1, device.getQuery());
                statement.setString(2, device.getQueryBusy());
                statement.setString(3, device.getName());
                statement.setString(4, device.getComment());
                statement.setString(5, device.getDeviceType().name());
                statement.setLong(6, device.getStation().getId());

                statement.executeUpdate();

                resultSet = statement.getGeneratedKeys();
                if (resultSet.next()) {
                    device.setId(resultSet.getLong(1));
                }

            } else {
                statement = connection.prepareStatement("UPDATE device SET query = ?, queryBusy = ?, name = ?, comment = ?, deviceType = ?," +
                        "station_id = ? WHERE id = ?");
                statement.setString(1, device.getQuery());
                statement.setString(2, device.getQueryBusy());
                statement.setString(3, device.getName());
                statement.setString(4, device.getComment());
                statement.setString(5, device.getDeviceType().name());
                statement.setLong(6, device.getStation().getId());
                statement.setLong(7, device.getId());
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
        return device;
    }

    private Device saveStation(Station station) {
        logger.info("saving Station");
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = Mysql.getConnection();
            if (station.getId() == null) {
                statement = connection.prepareStatement("INSERT INTO station(comment,deviceType,host,login,name,password,port) " +
                        "VALUES (?,?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
                statement.setString(1, station.getComment());
                statement.setString(2, station.getDeviceType().name());
                statement.setString(3, station.getHost());
                statement.setString(4, station.getLogin());
                statement.setString(5, station.getName());
                statement.setString(6, station.getPassword());
                statement.setString(7, station.getPort());

                statement.executeUpdate();
                resultSet = statement.getGeneratedKeys();
                while (resultSet.next()) {
                    station.setId(resultSet.getLong(1));
                }
                resultSet.close();
            } else {
                statement = connection.prepareStatement("UPDATE station SET comment=?, " +
                        "host=?, login=?, name=?, password=?, port=? WHERE station_id=?");
                statement.setString(1, station.getComment());
                statement.setString(2, station.getHost());
                statement.setString(3, station.getLogin());
                statement.setString(4, station.getName());
                statement.setString(5, station.getPassword());
                statement.setString(6, station.getPort());
                statement.setLong(7, station.getId());
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

    public void saveUIPosition(List<Item> items) {
        logger.info("saving items.size=" + items);
        for (Item item : items) {
            savePosition(item);
        }
    }

    private void savePosition(Item item) {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = Mysql.getConnection();
            if (item.getData() instanceof Station)
                statement = connection.prepareStatement("REPLACE INTO station_pos SET id = ?, x = ?, y = ?");
            else
                statement = connection.prepareStatement("REPLACE INTO device_pos SET id = ?, x = ?, y = ?");
            logger.info("Saving item: " + statement.toString());
            statement.setLong(1, item.getId());
            statement.setLong(2, item.getCoordX());
            statement.setLong(3, item.getCoordY());
            statement.executeUpdate();
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

    public List<Item> loadItems() {
        List<Item> items = new ArrayList<Item>();
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = Mysql.getConnection();
            statement = connection.prepareStatement("SELECT * FROM device LEFT JOIN device_pos ON device.id = device_pos.id ");
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                CommonDevice device = new CommonDevice();
                device.setId(resultSet.getLong("device.id"));
                device.setStatus(Status.INIT);
                device.setQuery(resultSet.getString("query"));
                device.setQueryBusy(resultSet.getString("queryBusy"));
                device.setDeviceType(DeviceType.valueOf(resultSet.getString("deviceType")));
                device.setName(resultSet.getString("name"));
                device.setStation(findStation(connection, resultSet.getLong("station_id")));

                Item<CommonDevice> item = new Item<CommonDevice>();
                item.setData(device);
                item.setCoordX(resultSet.getInt("x"));
                item.setCoordY(resultSet.getInt("y"));
                items.add(item);
            }

            statement = connection.prepareStatement("SELECT * FROM station LEFT JOIN station_pos ON station.station_id = station_pos.id");
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Station station = new Station();
                station.setId(resultSet.getLong("station_id"));
                station.setComment(resultSet.getString("comment"));
                station.setStatus(Status.INIT);
                station.setHost(resultSet.getString("host"));
                station.setPort(resultSet.getString("port"));
                station.setLogin(resultSet.getString("login"));
                station.setPassword(resultSet.getString("password"));
                station.setName(resultSet.getString("name"));

                Item<Station> item = new Item<Station>();
                item.setData(station);
                item.setCoordX(resultSet.getInt("x"));
                item.setCoordY(resultSet.getInt("y"));
                items.add(item);
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
        return items;
    }

    private Station findStation(Connection connection, Long stationId) {
        List<Item> items = new ArrayList<Item>();
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Station station = null;
        try {
            connection = Mysql.getConnection();
            statement = connection.prepareStatement("SELECT * FROM station WHERE station_id = ?");
            statement.setLong(1, stationId);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                station = new Station();
                station.setId(resultSet.getLong("station_id"));
                station.setComment(resultSet.getString("comment"));
                station.setStatus(Status.INIT);
                station.setHost(resultSet.getString("host"));
                station.setPort(resultSet.getString("port"));
                station.setLogin(resultSet.getString("login"));
                station.setPassword(resultSet.getString("password"));
                station.setName(resultSet.getString("name"));
//                station.set
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

    public Device loadDevice(Long deviceId, DeviceType deviceType) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Device device = null;
        try {
            connection = Mysql.getConnection();
            if (deviceType == DeviceType.STATION) {
                device = findStation(connection, deviceId);
                return device;
            } else
                statement = connection.prepareStatement("SELECT * FROM  DEVICE WHERE id = ?");
            statement.setLong(1, deviceId);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                CommonDevice dev = new CommonDevice();
                dev.setId(resultSet.getLong("device.id"));
                dev.setStatus(Status.INIT);
                dev.setQuery(resultSet.getString("query"));
                dev.setQueryBusy(resultSet.getString("queryBusy"));
                dev.setDeviceType(DeviceType.valueOf(resultSet.getString("deviceType")));
                dev.setName(resultSet.getString("name"));
                dev.setStation(findStation(connection, resultSet.getLong("station_id")));
                return dev;
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
        return null;  //To change body of created methods use File | Settings | File Templates.
    }

    public void saveBusyInfo(Long id, BusyInfo info) {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = Mysql.getConnection();
            statement = connection.prepareStatement("INSERT INTO busyinfo (device_id, busy, max, date) VALUES (?,?,?,?)");
            statement.setLong(1, id);
            statement.setLong(2, info.getBusy());
            statement.setLong(3, info.getMax());
            statement.setDate(4, new Date(new java.util.Date().getTime()));
            statement.executeUpdate();
        } catch (Exception e) {
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

    public BusyInfo getBusyInfo(Device device) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = Mysql.getConnection();
                statement = connection.prepareStatement("SELECT * FROM  busyinfo WHERE id = ? order by date desc ");
            statement.setMaxRows(1);
            statement.setLong(1, device.getId());
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                BusyInfo info = new BusyInfo();
                info.setId(resultSet.getLong("id"));
                info.setBusy(resultSet.getLong("busy"));
                info.setMax(resultSet.getLong("max"));
                info.setDate(resultSet.getDate("date"));
                info.setDeviceId(device.getId());
                return info;
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
        return null;
    }

    public void deleteDevice(Device device){
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try{
            connection = Mysql.getConnection();
            if(device instanceof Station) {
                statement = connection.prepareStatement("DELETE FROM station WHERE station_id = ?");
                statement.setLong(1, device.getId());
                statement.executeUpdate();
                statement = connection.prepareStatement("DELETE FROM station_pos WHERE id = ?");
                statement.setLong(1, device.getId());
                statement.executeUpdate();

                statement = connection.prepareStatement("SELECT id FROM device WHERE station_id = ? ");
                resultSet = statement.executeQuery();
                List<Long> devices = new ArrayList<Long>();

                while (resultSet.next()) {
                    devices.add(resultSet.getLong("id"));
                }
                statement = connection.prepareStatement("DELETE FROM device WHERE id in (?)");
                statement.setArray(1, connection.createArrayOf("Long", devices.toArray()));
                statement.executeUpdate();

                statement = connection.prepareStatement("DELETE FROM device_pos WHERE id in (?)");
                statement.setArray(1, connection.createArrayOf("Long", devices.toArray()));
                statement.executeUpdate();

            } else {
                statement = connection.prepareStatement("DELETE FROM device WHERE id = ?");
                statement.setLong(1, device.getId());
                statement.executeUpdate();
                statement = connection.prepareStatement("DELETE FROM device_pos WHERE id = ?");
                statement.setLong(1, device.getId());
                statement.executeUpdate();
            }

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
