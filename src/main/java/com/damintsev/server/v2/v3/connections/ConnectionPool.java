package com.damintsev.server.v2.v3.connections;

import com.damintsev.client.v3.items.Station;
import com.damintsev.client.v3.items.task.Task;
import com.damintsev.client.v3.items.task.TaskType;
import com.damintsev.server.v2.v3.exceptions.ConnectException;

import java.util.HashMap;
import java.util.Map;

/**
 * User: Damintsev Andrey
 * Date: 06.10.13
 * Time: 20:01
 */
public class ConnectionPool {

    private static ConnectionPool instance;

    public static ConnectionPool getInstance() {
        if (instance == null) instance = new ConnectionPool();
        return instance;
    }

    private Map<String, Connection> connectionMap;

    private ConnectionPool() {
        connectionMap = new HashMap<String, Connection>();
    }

//    public ConnectionPool addConnection(Connection connection) {
//        connectionMap.put(connection.getId(), connection);
//        return this;
//    }

    public Connection getConnection(Task task) throws ConnectException {
//        switch (task.getType()) {
//            case TELNET: {
                Connection connection = connectionMap.get(task.getStringId());
                if(connection == null) connection = create(task.getStation(), task.getType());
                return connection;
//            }
//            case IP: {
//                Connection connection = connectionMap.get(task.getStringId() + "IP");//todo красиво параметризовать
//                if(connection == null) connection = create(task.getStation(), task.getType());
//                return connection;
//            }
//            default:throw new ConnectException(new Exception("Connection not found!"));
//        }
//        return null;
    }

    public Connection create(Station station, TaskType type) throws ConnectException {
        Connection conn;
        System.err.println("TYPE=" + type);
        switch (type) {
            case TELNET:
                conn = new TelnetConnection().init(station);
                connectionMap.put(station.getStringId(), conn);
                return conn;
            case IP:
            case ISDN:
                conn = new PingConnection().init(station);
                connectionMap.put(station.getStringId() + "IP", conn);//todo параметризовать
                return conn;
            default:
                throw new ConnectException(new Exception("Connection not found"));
        }
//        return conn;
    }

    public void dropConnection(Station station) {
        if(connectionMap.get(station.getStringId()) != null)
            connectionMap.get(station.getStringId()).destroy();
    }

    public void dropConnections() {
        for(Connection connection : connectionMap.values()) {
            connection.destroy();
        }
    }
}
