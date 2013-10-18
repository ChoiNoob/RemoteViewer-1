//package com.damintsev.server.db;
//
//import com.damintsev.client.old.devices.*;
//import com.damintsev.client.old.devices.enums.DeviceType;
//import com.damintsev.client.old.devices.enums.Status;
//import com.damintsev.client.old.devices.graph.BusyInfo;
//import com.damintsev.common.pojo.Station;
//import org.slf4j.LoggerFactory;
//
//import java.sql.*;
//import java.util.*;
//
///**
//* User: Damintsev Andrey
//* Date: 19.08.13
//* Time: 23:36
//*/
//public class DatabaseConnector {
//
//    private static DatabaseConnector instance;
//    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(DatabaseConnector.class);
//
//    public static DatabaseConnector getInstance() {
//        if (instance == null) instance = new DatabaseConnector();
//        return instance;
//    }
//
//    public Device saveDevice(Device device) {
//        logger.info("Save device");
////        if (device instanceof Station) {
////            return saveStation((Station) device);
////        } else {
////            return saveCommonDevice((CommonDevice) device);
////        }
//        return null;
//    }
//
//    private Device saveCommonDevice(CommonDevice device) {
//        Connection connection = Mysql.getConnection();
//        PreparedStatement statement = null;
//        ResultSet resultSet = null;
//        try {
//            if (device.getId() == null) {
//                statement = connection.prepareStatement("INSERT INTO device (query, queryBusy, name, comment, deviceType, station_id) " +
//                        "values (?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
//                statement.setString(1, device.getQuery());
//                statement.setString(2, device.getQueryBusy());
//                statement.setString(3, device.getName());
//                statement.setString(4, device.getComment());
//                statement.setString(5, device.getDeviceType().name());
//                statement.setLong(6, device.getStation().getId());
//
//                statement.executeUpdate();
//
//                resultSet = statement.getGeneratedKeys();
//                if (resultSet.next()) {
//                    device.setId(resultSet.getLong(1));
//                }
//
//            } else {
//                statement = connection.prepareStatement("UPDATE device SET query = ?, queryBusy = ?, name = ?, comment = ?, deviceType = ?," +
//                        "station_id = ? WHERE id = ?");
//                statement.setString(1, device.getQuery());
//                statement.setString(2, device.getQueryBusy());
//                statement.setString(3, device.getName());
//                statement.setString(4, device.getComment());
//                statement.setString(5, device.getDeviceType().name());
//                statement.setLong(6, device.getStation().getId());
//                statement.setLong(7, device.getId());
//                statement.executeUpdate();
//            }
//        } catch (Exception e) {
//            logger.error(e.getMessage(), e);
//            e.printStackTrace();
//        } finally {
//            try {
//                if (resultSet != null) {
//                    resultSet.close();
//                }
//                if (statement != null) {
//                    statement.close();
//                }
//                if (connection != null) {
//                    connection.close();
//                }
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//        }
//        return device;
//    }
//
//    private Device saveStation(Station station) {
//        logger.info("saving Station");
//        Connection connection = null;
//        PreparedStatement statement = null;
//        ResultSet resultSet = null;
//        try {
//            connection = Mysql.getConnection();
//            if (station.getId() == null) {
//                statement = connection.prepareStatement("INSERT INTO station(comment,deviceType,host,login,name,password,port,allowStatistics) " +
//                        "VALUES (?,?,?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
//                statement.setString(1, station.getComment());
//                statement.setString(2, station.getDeviceType().name());
//                statement.setString(3, station.getHost());
//                statement.setString(4, station.getLogin());
//                statement.setString(5, station.getName());
//                statement.setString(6, station.getPassword());
//                statement.setString(7, station.getPort());
//                statement.setBoolean(8, station.getAllowStatistics());
//
//                statement.executeUpdate();
//                resultSet = statement.getGeneratedKeys();
//                if (resultSet.next()) {
//                    station.setId(resultSet.getLong(1));
//                }
//                resultSet.close();
//            } else {
//                statement = connection.prepareStatement("UPDATE station SET comment=?, " +
//                        "host=?, login=?, name=?, password=?, port=?, allowStatistics=? WHERE station_id=?");
//                statement.setString(1, station.getComment());
//                statement.setString(2, station.getHost());
//                statement.setString(3, station.getLogin());
//                statement.setString(4, station.getName());
//                statement.setString(5, station.getPassword());
//                statement.setString(6, station.getPort());
//                statement.setBoolean(7, station.getAllowStatistics());
//                statement.setLong(8, station.getId());
//                statement.executeUpdate();
//            }
//        } catch (Exception e) {
//            logger.error(e.getMessage(), e);
//            e.printStackTrace();
//        } finally {
//            try {
//                if (resultSet != null) {
//                    resultSet.close();
//                }
//                if (statement != null) {
//                    statement.close();
//                }
//                if (connection != null) {
//                    connection.close();
//                }
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//        }
//        return station;
//    }
//
//    public void saveUIPosition(List<Item> items) {
//        logger.info("saving items.size=" + items);
//        for (Item item : items) {
//            savePosition(item);
//        }
//    }
//
//    private void savePosition(Item item) {
////        Connection connection = null;
////        PreparedStatement statement = null;
////        try {
////            connection = Mysql.getConnection();
////            if (item.getData() instanceof Station)
////                statement = connection.prepareStatement("REPLACE INTO station_pos SET id = ?, x = ?, y = ?");
////            else
////                statement = connection.prepareStatement("REPLACE INTO device_pos SET id = ?, x = ?, y = ?");
////            logger.info("Saving item: " + statement.toString());
////            statement.setLong(1, item.getId());
////            statement.setLong(2, item.getCoordX());
////            statement.setLong(3, item.getCoordY());
////            statement.executeUpdate();
////        } catch (Exception e) {
////            logger.error(e.getMessage(), e);
////            e.printStackTrace();
////        } finally {
////            try {
////                if (statement != null) {
////                    statement.close();
////                }
////                if (connection != null) {
////                    connection.close();
////                }
////            } catch (SQLException e) {
////                e.printStackTrace();
////            }
////        }
//    }
//
//    public List<Item> loadItems() {
////        List<Item> items = new ArrayList<Item>();
////        Connection connection = null;
////        PreparedStatement statement = null;
////        try {
////            connection = Mysql.getConnection();
////            statement = connection.prepareStatement("SELECT * FROM device LEFT JOIN device_pos ON device.id = device_pos.id ");
////            ResultSet resultSet = statement.executeQuery();
////            while (resultSet.next()) {
////                CommonDevice device = new CommonDevice();
////                device.setId(resultSet.getLong("device.id"));
////                device.setStatus(Status.INIT);
////                device.setQuery(resultSet.getString("query"));
////                device.setQueryBusy(resultSet.getString("queryBusy"));
////                device.setDeviceType(DeviceType.valueOf(resultSet.getString("deviceType")));
////                device.setName(resultSet.getString("name"));
////                device.setStation(findStation(connection, resultSet.getLong("station_id")));
////
////                Item<CommonDevice> item = new Item<CommonDevice>();
////                item.setData(device);
////                item.setCoordX(resultSet.getInt("x"));
////                item.setCoordY(resultSet.getInt("y"));
////                items.add(item);
////            }
////
////            statement = connection.prepareStatement("SELECT * FROM station LEFT JOIN station_pos ON station.station_id = station_pos.id");
////            resultSet = statement.executeQuery();
////            while (resultSet.next()) {
////                Station station = new Station();
////                station.setId(resultSet.getLong("station_id"));
////                station.setComment(resultSet.getString("comment"));
////                station.setStatus(Status.INIT);
////                station.setHost(resultSet.getString("host"));
////                station.setPort(resultSet.getString("port"));
////                station.setLogin(resultSet.getString("login"));
////                station.setPassword(resultSet.getString("password"));
////                station.setName(resultSet.getString("name"));
////
////                Item<Station> item = new Item<Station>();
////                item.setData(station);
////                item.setCoordX(resultSet.getInt("x"));
////                item.setCoordY(resultSet.getInt("y"));
////                items.add(item);
////            }
////
////            resultSet.close();
////        } catch (Exception e) {
////            logger.error(e.getMessage(), e);
////            e.printStackTrace();
////        } finally {
////            try {
////                if (statement != null) {
////                    statement.close();
////                }
////                if (connection != null) {
////                    connection.close();
////                }
////            } catch (SQLException e) {
////                e.printStackTrace();
////            }
////        }
////        return items;
//        return null;
//    }
//
//    private Station findStation(Connection connection, Long stationId) {
//        PreparedStatement statement = null;
//        ResultSet resultSet = null;
//        Station station = null;
//        try {
//            statement = connection.prepareStatement("SELECT * FROM station WHERE station_id = ?");
//            statement.setLong(1, stationId);
//            resultSet = statement.executeQuery();
//            if (resultSet.next()) {
//                station = new Station();
//                station.setId(resultSet.getLong("station_id"));
//                station.setComment(resultSet.getString("comment"));
//                station.setStatus(Status.INIT);
//                station.setHost(resultSet.getString("host"));
//                station.setPort(resultSet.getString("port"));
//                station.setLogin(resultSet.getString("login"));
//                station.setPassword(resultSet.getString("password"));
//                station.setName(resultSet.getString("name"));
//                station.setAllowStatistics(resultSet.getBoolean("allowStatistics"));
////                station.set
//            }
//            return station;
//        } catch (Exception e) {
//            logger.error(e.getMessage(), e);
//            e.printStackTrace();
//        } finally {
//            try {
//                if (resultSet != null) {
//                    resultSet.close();
//                }
//                if (statement != null) {
//                    statement.close();
//                }
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//        }
//        return null;
//    }
//
//    public Device loadDevice(Long deviceId, DeviceType deviceType) {
//        Connection connection = null;
//        PreparedStatement statement = null;
//        ResultSet resultSet = null;
//        Device device = null;
//        try {
//            connection = Mysql.getConnection();
//            if (deviceType == DeviceType.STATION) {
////                device = findStation(connection, deviceId);
//                return null;
//            } else
//                statement = connection.prepareStatement("SELECT * FROM  device WHERE id = ?");
//            statement.setLong(1, deviceId);
//            resultSet = statement.executeQuery();
//            if (resultSet.next()) {
//                CommonDevice dev = new CommonDevice();
//                dev.setId(resultSet.getLong("device.id"));
//                dev.setStatus(Status.INIT);
//                dev.setQuery(resultSet.getString("query"));
//                dev.setQueryBusy(resultSet.getString("queryBusy"));
//                dev.setDeviceType(DeviceType.valueOf(resultSet.getString("deviceType")));
//                dev.setName(resultSet.getString("name"));
//                dev.setStation(findStation(connection, resultSet.getLong("station_id")));
//                return dev;
//            }
//        } catch (Exception e) {
//            logger.error(e.getMessage(), e);
//            e.printStackTrace();
//        } finally {
//            try {
//                if (resultSet != null) {
//                    resultSet.close();
//                }
//                if (statement != null) {
//                    statement.close();
//                }
//                if (connection != null) {
//                    connection.close();
//                }
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//        }
//        return null;  //To change body of created methods use File | Settings | File Templates.
//    }
//
//    public void saveBusyInfo(Long id, BusyInfo info) {
//        Connection connection = null;
//        PreparedStatement statement = null;
//        try {
//            connection = Mysql.getConnection();
//            statement = connection.prepareStatement("INSERT INTO busyinfo (device_id, busy, max, date) VALUES (?,?,?,?)");
//            statement.setLong(1, id);
//            statement.setLong(2, info.getBusy());
//            statement.setLong(3, info.getMax());
//            statement.setTimestamp(4, new Timestamp(new java.util.Date().getTime()));
//            statement.executeUpdate();
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            try {
//                if (statement != null) {
//                    statement.close();
//                }
//                if (connection != null) {
//                    connection.close();
//                }
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//        }
//    }
//
//    public BusyInfo getBusyInfo(Device device) {
//        Connection connection = null;
//        PreparedStatement statement = null;
//        ResultSet resultSet = null;
//        try {
//            connection = Mysql.getConnection();
//                statement = connection.prepareStatement("SELECT * FROM  busyinfo WHERE device_id = ? order by date desc ");
//            statement.setMaxRows(1);
//            statement.setLong(1, device.getId());
//            resultSet = statement.executeQuery();
//            if (resultSet.next()) {
//                BusyInfo info = new BusyInfo();
//                info.setId(resultSet.getLong("id"));
//                info.setBusy(resultSet.getLong("busy"));
//                info.setMax(resultSet.getLong("max"));
//                info.setDate(resultSet.getTimestamp("date"));
//                info.setDeviceId(device.getId());
//                return info;
//            }
//
//
//        } catch (Exception e) {
//            logger.error(e.getMessage(), e);
//            e.printStackTrace();
//        } finally {
//            try {
//                if (resultSet != null) {
//                    resultSet.close();
//                }
//                if (statement != null) {
//                    statement.close();
//                }
//                if (connection != null) {
//                    connection.close();
//                }
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//        }
//        return null;
//    }
//
//    public void deleteDevice(Device device){
//        Connection connection = null;
//        PreparedStatement statement = null;
//        ResultSet resultSet = null;
//        try{
//            connection = Mysql.getConnection();
//            if(device instanceof Station) {
//                statement = connection.prepareStatement("DELETE FROM station WHERE station_id = ?");
//                statement.setLong(1, device.getId());
//                statement.executeUpdate();
//                statement = connection.prepareStatement("DELETE FROM station_pos WHERE id = ?");
//                statement.setLong(1, device.getId());
//                statement.executeUpdate();
//
//                statement = connection.prepareStatement("SELECT id FROM device WHERE station_id = ? ");
//                statement.setLong(1, device.getId());
//                resultSet = statement.executeQuery();
//                List<Long> devices = new ArrayList<Long>();
//                while (resultSet.next()) {
//                    devices.add(resultSet.getLong("id"));
//                }
//
//                if (devices.size() > 0) {
//                    String SQL = "DELETE FROM device WHERE id in (";
//                    for(int i = 0; i < devices.size(); i++) {
//                        if (i > 0) SQL +=",";
//                        SQL += "?";
//                    }
//                    SQL += ")";
//                    statement = connection.prepareStatement(SQL);
//                    for(int i = 0; i < devices.size(); i++) {
//                        statement.setLong(i + 1, devices.get(i));
//                    }
//                    statement.executeUpdate();
//
//                    SQL = "DELETE FROM device_pos WHERE id in (";
//                    for(int i = 0; i < devices.size(); i++) {
//                        if (i > 0) SQL +=",";
//                        SQL += "?";
//                    }
//                    SQL += ")";
//                    statement = connection.prepareStatement(SQL);
//                    for(int i = 0; i < devices.size(); i++) {
//                        statement.setLong(i + 1, devices.get(i));
//                    }
//                    statement.executeUpdate();
////                    statement = connection.prepareStatement("DELETE FROM device_pos WHERE id in (?)");
////                    statement.setArray(1, connection.createArrayOf("Long", devices.toArray()));
////                    statement.executeUpdate();
//                }
//
//                statement = connection.prepareStatement("DELETE FROM ftpsettings WHERE station_id = ?");
//                statement.setLong(1, device.getId());
//                statement.executeUpdate();
//
//            } else {
//                statement = connection.prepareStatement("DELETE FROM device WHERE id = ?");
//                statement.setLong(1, device.getId());
//                statement.executeUpdate();
//                statement = connection.prepareStatement("DELETE FROM device_pos WHERE id = ?");
//                statement.setLong(1, device.getId());
//                statement.executeUpdate();
//            }
//
//        } catch (Exception e) {
//            try {
//                connection.rollback();
//            } catch (SQLException e1) {
//                e1.printStackTrace();
//            }
//            logger.error(e.getMessage(), e);
//            e.printStackTrace();
//        } finally {
//            try {
//                if (statement != null) {
//                    statement.close();
//                }
//                if (connection != null) {
//                    connection.close();
//                }
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//        }
//    }
//
//    public FTPSettings saveFTPSettings(FTPSettings settings) {
//        Connection connection = null;
//        PreparedStatement statement = null;
//        ResultSet resultSet;
//        try{
//            connection = Mysql.getConnection();
//
//            if(settings.getId() == null) {
//                statement = connection.prepareStatement("INSERT INTO ftpsettings(dir,host,login,password,port,station_id)" +
//                        "VALUES (?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
//                statement.setString(1, settings.getDir());
//                statement.setString(2, settings.getHost());
//                statement.setString(3, settings.getLogin());
//                statement.setString(4, settings.getPassword());
//                statement.setString(5, settings.getPort());
//                statement.setLong(6, settings.getStation().getId());
//                statement.executeUpdate();
//                resultSet = statement.getGeneratedKeys();
//                if(resultSet.next()) {
//                    settings.setId(resultSet.getLong(1));
//                }
//                return settings;
//            } else {
//                statement = connection.prepareStatement("UPDATE ftpsettings SET dir=?,host=?,login=?,password=?,port=?,station_id=?" +
//                        " WHERE settings_id = ?");
//                statement.setString(1, settings.getDir());
//                statement.setString(2, settings.getHost());
//                statement.setString(3, settings.getLogin());
//                statement.setString(4, settings.getPassword());
//                statement.setString(5, settings.getPort());
//                statement.setLong(6, settings.getStation().getId());
//                statement.setLong(7, settings.getId());
//                statement.executeUpdate();
//                return settings;
//            }
//
//        } catch (Exception e) {
//            logger.error(e.getMessage(), e);
//            e.printStackTrace();
//        } finally {
//            try {
//                if (statement != null) {
//                    statement.close();
//                }
//                if (connection != null) {
//                    connection.close();
//                }
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//        }
//        return null;
//    }
//
//    public FTPSettings loadFTPSettings(Station station) {
//        Connection connection = null;
//        PreparedStatement statement = null;
//        ResultSet resultSet = null;
//        FTPSettings settings = null;
//        try {
//            connection = Mysql.getConnection();
//            statement = connection.prepareStatement("SELECT * FROM ftpsettings WHERE station_id = ?");
//            statement.setLong(1, station.getId());
//            resultSet = statement.executeQuery();
//            if (resultSet.next()) {
//                settings = new FTPSettings();
//                settings.setId(resultSet.getLong("settings_id"));
//                settings.setDir(resultSet.getString("dir"));
//                settings.setHost(resultSet.getString("host"));
//                settings.setLogin(resultSet.getString("login"));
//                settings.setPassword(resultSet.getString("password"));
//                settings.setPort(resultSet.getString("port"));
//                settings.setStationId(resultSet.getLong("station_id"));
//                settings.setStation(findStation(connection, resultSet.getLong("station_id")));
//            }
//            return settings;
//
//        } catch (Exception e) {
//            logger.error(e.getMessage(), e);
//            e.printStackTrace();
//        } finally {
//            try {
//                if (statement != null) {
//                    statement.close();
//                }
//                if (connection != null) {
//                    connection.close();
//                }
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//        }
//        return null;
//    }
//
//    public List<FTPSettings> listFTPSettings() {
//        Connection connection = null;
//        PreparedStatement statement = null;
//        ResultSet resultSet = null;
//        List<FTPSettings> settingses = new ArrayList<FTPSettings>();
//        try {
//            connection = Mysql.getConnection();
//            statement = connection.prepareStatement("SELECT * FROM ftpsettings");
//            resultSet = statement.executeQuery();
//            while (resultSet.next()) {
//               FTPSettings settings = new FTPSettings();
//                settings.setId(resultSet.getLong("settings_id"));
//                settings.setDir(resultSet.getString("dir"));
//                settings.setHost(resultSet.getString("host"));
//                settings.setLogin(resultSet.getString("login"));
//                settings.setPassword(resultSet.getString("password"));
//                settings.setPort(resultSet.getString("port"));
//                settings.setStationId(resultSet.getLong("station_id"));
//                settings.setStation(findStation(connection, resultSet.getLong("station_id")));
//                settingses.add(settings);
//            }
//            return settingses;
//        } catch (Exception e) {
//            logger.error(e.getMessage(), e);
//            e.printStackTrace();
//        } finally {
//            try {
//                if (statement != null) {
//                    statement.close();
//                }
//                if (connection != null) {
//                    connection.close();
//                }
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//        }
//        return null;
//    }
//
//    public List<BillingInfo> saveBillingInfo(List<BillingInfo> billingList) {
//        logger.info("Saving billing info...");
//        Connection connection = null;
//        PreparedStatement statement = null;
//        ResultSet resultSet = null;
//        try {
//            connection = Mysql.getConnection();
//            logger.info("Size of billing info is=" + billingList.size());
//            for (BillingInfo info : billingList) {
//                logger.info("Saving : " + info.getNumberShort());
//                statement = connection.prepareStatement("INSERT INTO billinginfo (date,source,destination,duration,trunk_id,shortDesination) " +
//                        "VALUES (?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
//                statement.setTimestamp(1, new Timestamp(info.getDate().getTime()));
//                statement.setString(2, info.getNumberFrom());
//                statement.setString(3, info.getNumber());
//                statement.setString(4, info.getCallDuration());
//                statement.setLong(5, info.getTrunkNumber());
//                statement.setString(6, info.getNumberShort());
//                statement.executeUpdate();
//                resultSet = statement.getGeneratedKeys();
//                if (resultSet.next()) {
//                    info.setId(resultSet.getLong(1));
//                }
//            }
//            return billingList;
//        } catch (Exception e) {
//            logger.error(e.getMessage(), e);
//            e.printStackTrace();
//        } finally {
//            try {
//                if (statement != null) {
//                    statement.close();
//                }
//                if (connection != null) {
//                    connection.close();
//                }
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//        }
//        return null;
//    }
//
//    public List<BillingInfo> loadBillingInfo() {
//        Connection connection = null;
//        PreparedStatement statement = null;
//        ResultSet resultSet = null;
//        List<BillingInfo> billingInfoList = new ArrayList<BillingInfo>();
//        try {
//            connection = Mysql.getConnection();
//            statement = connection.prepareStatement("SELECT * FROM billinginfo WHERE date >= ?");
//            Calendar calendar = Calendar.getInstance();
//            calendar.set(Calendar.HOUR, 0);
//            calendar.set(Calendar.MINUTE, 0);
//
//            statement.setTimestamp(1, new Timestamp(calendar.getTimeInMillis()));
//            resultSet = statement.executeQuery();
//            while (resultSet.next()) {
//                BillingInfo info = new BillingInfo();
//                info.setId(resultSet.getLong("id"));
//                info.setDate(resultSet.getDate("date"));
//                info.setCallDuration(resultSet.getString("duration"));
//                info.setNumberFrom(resultSet.getString("source"));
//                info.setNumber(resultSet.getString("destination"));
//                info.setNumberShort(resultSet.getString("shortDesination"));
//                info.setTrunkNumber(resultSet.getLong("trunk_id"));
//                billingInfoList.add(info);
//            }
//            return billingInfoList;
//        } catch (Exception e) {
//            logger.error(e.getMessage(), e);
//            e.printStackTrace();
//        } finally {
//            try {
//                if (statement != null) {
//                    statement.close();
//                }
//                if (connection != null) {
//                    connection.close();
//                }
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//        }
//        return null;
//    }
//
//    private HashMap<String, String> dictionary = new HashMap<String, String>();
//    public String getDestinationName(String prefix) {
//        if(dictionary.containsKey(prefix))
//            return dictionary.get(prefix);
//        else {
//            Connection connection = null;
//            PreparedStatement statement = null;
//            ResultSet resultSet;
//            try {
//                connection = Mysql.getConnection();
//                statement = connection.prepareStatement("SELECT name FROM dictionary WHERE prefix LIKE ?");
//
//                statement.setString(1, prefix);
//                resultSet = statement.executeQuery();
//                String name = null;
//                if(resultSet.next()) {
//                    name = resultSet.getString("name");
//                    dictionary.put(prefix, name);
//                }
//                return name;
//            } catch (Exception e) {
//                logger.error(e.getMessage(), e);
//                e.printStackTrace();
//            } finally {
//                try {
//                    if (statement != null) {
//                        statement.close();
//                    }
//                    if (connection != null) {
//                        connection.close();
//                    }
//                } catch (SQLException e) {
//                    e.printStackTrace();
//                }
//            }
//            return null;
//        }
//    }
//
//    public List<BusyInfo> getBusyInfoStatistics(Device device) {
//        Connection connection = null;
//        PreparedStatement statement = null;
//        ResultSet resultSet = null;
//        List<BusyInfo> busyInfoList = new ArrayList<BusyInfo>();
//        try {
//            connection = Mysql.getConnection();
//            statement = connection.prepareStatement("SELECT * FROM  busyinfo " +
//                    "WHERE id = ? " +
//                    " AND date > ? " +
//                    "order by date desc ");
//            statement.setLong(1, device.getId());
//            Calendar calendar = Calendar.getInstance();
//            calendar.add(Calendar.HOUR, -2);
//            statement.setTimestamp(2, new Timestamp(calendar.getTimeInMillis()));
//            resultSet = statement.executeQuery();
//            if (resultSet.next()) {
//                BusyInfo info = new BusyInfo();
//                info.setId(resultSet.getLong("id"));
//                info.setBusy(resultSet.getLong("busy"));
//                info.setMax(resultSet.getLong("max"));
//                info.setDate(resultSet.getDate("date"));
//                info.setDeviceId(device.getId());
//                busyInfoList.add(info);
//            }
//            return busyInfoList;
//        } catch (Exception e) {
//            logger.error(e.getMessage(), e);
//            e.printStackTrace();
//        } finally {
//            try {
//                if (resultSet != null) {
//                    resultSet.close();
//                }
//                if (statement != null) {
//                    statement.close();
//                }
//                if (connection != null) {
//                    connection.close();
//                }
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//        }
//        return null;
//    }
//
//    public List<Station> getStationList() {
//        List<Station> stations = new ArrayList<Station>();
//        PreparedStatement statement = null;
//        ResultSet resultSet = null;
//        Connection connection;
//        try {
//            connection = Mysql.getConnection();
//            statement = connection.prepareStatement("SELECT * FROM station");
//            resultSet = statement.executeQuery();
//            if (resultSet.next()) {
//                Station    station = new Station();
//                station.setId(resultSet.getLong("station_id"));
//                station.setComment(resultSet.getString("comment"));
//                station.setStatus(Status.INIT);
//                station.setHost(resultSet.getString("host"));
//                station.setPort(resultSet.getString("port"));
//                station.setLogin(resultSet.getString("login"));
//                station.setPassword(resultSet.getString("password"));
//                station.setName(resultSet.getString("name"));
//                stations.add(station);
//            }
//            return stations;
//        } catch (Exception e) {
//            logger.error(e.getMessage(), e);
//            e.printStackTrace();
//        } finally {
//            try {
//                if (resultSet != null) {
//                    resultSet.close();
//                }
//                if (statement != null) {
//                    statement.close();
//                }
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//        }
//        return null;
//    }
//
//    public Station getStationWithBilling() {
//        PreparedStatement statement = null;
//        ResultSet resultSet = null;
//        Station station = null;
//        Connection connection;
//        try {
//            connection = Mysql.getConnection();
//            statement = connection.prepareStatement("SELECT * FROM station WHERE allowStatistics = ?");
//            statement.setBoolean(1, true);
//            resultSet = statement.executeQuery();
//            if (resultSet.next()) {
//                station = new Station();
//                station.setId(resultSet.getLong("station_id"));
//                station.setComment(resultSet.getString("comment"));
//                station.setStatus(Status.INIT);
//                station.setHost(resultSet.getString("host"));
//                station.setPort(resultSet.getString("port"));
//                station.setLogin(resultSet.getString("login"));
//                station.setPassword(resultSet.getString("password"));
//                station.setName(resultSet.getString("name"));
//                station.setAllowStatistics(resultSet.getBoolean("allowStatistics"));
//            }
//            return station;
//        } catch (Exception e) {
//            logger.error(e.getMessage(), e);
//            e.printStackTrace();
//        } finally {
//            try {
//                if (resultSet != null) {
//                    resultSet.close();
//                }
//                if (statement != null) {
//                    statement.close();
//                }
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//        }
//        return null;
//    }
//
//    public TreeMap<String, String> loadPrefixMap() {
//        Connection connection = null;
//        PreparedStatement statement = null;
//        ResultSet resultSet;
//        TreeMap<String, String> map = new TreeMap<String, String>();
//        try {
//            connection = Mysql.getConnection();
//            statement = connection.prepareStatement("SELECT * FROM dictionary ");
//            resultSet = statement.executeQuery();
//            while (resultSet.next()) {
//                logger.info("Loading prefix=" + resultSet.getString("prefix"));
//                map.put(resultSet.getString("prefix"), resultSet.getString("number"));
//            }
//            return map;
//        } catch (Exception e) {
//            logger.error(e.getMessage(), e);
//            e.printStackTrace();
//        } finally {
//            try {
//                if (statement != null) {
//                    statement.close();
//                }
//                if (connection != null) {
//                    connection.close();
//                }
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//        }
//        return null;
//    }
//
//    public void savePrefixMap(TreeMap<String, String> prefixMap) {
//        logger.info("Saving prefix map");
//        Connection connection = null;
//        PreparedStatement statement = null;
//        try {
//            connection = Mysql.getConnection();
//
//            statement = connection.prepareStatement("SELECT prefix FROM dictionary");
//            ResultSet resultSet = statement.executeQuery();
//            List<String> toDelete = new ArrayList<String>();
//            while (resultSet.next()) {
//                logger.info("Found at instance existing prefix =" + resultSet.getString("prefix"));
//                toDelete.add(resultSet.getString("prefix"));
//            }
//            for(String delete : toDelete) {
//                logger.info("Removing existing prefix=" + delete);
//                statement = connection.prepareStatement("DELETE FROM dictionary WHERE prefix = ?");
//                statement.setString(1, delete);
//                statement.executeUpdate();
//            }
//            for (Map.Entry<String, String> entry : prefixMap.entrySet()) {
//                logger.info("Saving items prefix=" + entry.getKey() + " name=" + entry.getValue());
//                statement = connection.prepareStatement("INSERT INTO dictionary (prefix, number) VALUES(?,?)");
//                statement.setString(1, entry.getKey());
//                statement.setString(2, entry.getValue());
//                statement.executeUpdate();
//            }
//        } catch (Exception e) {
//            logger.error(e.getMessage(), e);
//            e.printStackTrace();
//        } finally {
//            try {
//                if (statement != null) {
//                    statement.close();
//                }
//                if (connection != null) {
//                    connection.close();
//                }
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//        }
//    }
//}
