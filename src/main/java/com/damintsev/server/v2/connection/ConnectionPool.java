package com.damintsev.server.v2.connection;

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

    public ConnectionPool addConnection(Connection connection) {
        connectionMap.put(connection.getId(), connection);
        return this;
    }

    public Connection getConnection(Long id) {
        return connectionMap.get(id);
    }
}
