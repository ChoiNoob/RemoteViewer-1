package com.damintsev.server.v2.v3.connections;

import com.damintsev.client.v3.items.Station;
import com.damintsev.client.v3.items.task.Task;
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

    private Map<Long, Connection> connectionMap;

    private ConnectionPool() {
        connectionMap = new HashMap<Long, Connection>();
    }

//    public ConnectionPool addConnection(Connection connection) {
//        connectionMap.put(connection.getId(), connection);
//        return this;
//    }

    public Connection getConnection(Task task) throws ConnectException {
        switch (task.getType()) {
            case TELNET: {
                Connection connection = connectionMap.get(task.getStation().getId());
                if(connection == null) connection = create(task.getStation());
                return connection;
            }
            case IP: {
                //todo
                System.out.println("case ip");
                throw new ConnectException();
            }
            default:throw new ConnectException();
        }
//        return null;
    }

    public Connection create(Station station) throws ConnectException {
//        switch (station.getType()) {
//            case TELNET:
        Connection conn = new TelnetConnection().init(station);
        connectionMap.put(station.getId(), conn);
//            default: return null;
//        }
        return conn;
    }

    public void dropConnection(Station station) {
        if(connectionMap.get(station.getId()) != null)
            connectionMap.get(station.getId()).destroy();
    }

    public void dropConnections() {
        for(Connection connection : connectionMap.values()) {
            connection.destroy();
        }
    }
}
