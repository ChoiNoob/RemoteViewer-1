package com.damintsev.server.v2.v3.connections;

import com.damintsev.common.beans.Station;
import com.damintsev.common.beans.Task;
import com.damintsev.common.beans.TaskType;
import com.damintsev.server.v2.v3.exceptions.ConnectException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * User: Damintsev Andrey
 * Date: 06.10.13
 * Time: 20:01
 */
public class ConnectionPool {
    private static final Logger logger = LoggerFactory.getLogger(ConnectionPool.class);

    private static ConnectionPool instance;

    public static ConnectionPool getInstance() {
        if (instance == null) instance = new ConnectionPool();
        return instance;
    }

    private Map<String, Connection> connectionMap;

    private ConnectionPool() {
        connectionMap = new HashMap<String, Connection>();
    }

    public Connection getConnection(Task task) throws ConnectException {
        Connection connection = connectionMap.get(task.getStringId() + task.getType());
        if (connection == null) {
            logger.info("Connection not fount i pool. Initialiing new connection");
            connection = create(task.getStation(), task.getType());
        }
        return connection;
    }

    public Connection create(Station station, TaskType type) throws ConnectException {
        Connection conn;
        switch (type) {
//            case TELNET:
            case ISDN:
                conn = new TelnetConnection().init(station);
                connectionMap.put(station.getStringId() + type, conn);
                return conn;
            case IP:
                conn = new PingConnection().init(station);
                connectionMap.put(station.getStringId() + type, conn);//todo параметризовать
                return conn;
            default:
                throw new ConnectException(new Exception("Connection not found"));
        }
//        return conn;
    }

    public void dropConnection(Station station) {
        if(connectionMap.get(station.getStringId()) != null) {
            System.out.println("destroyng connection=" +station.getStringId());
            connectionMap.get(station.getStringId()).destroy();
            connectionMap.remove(station.getStringId());
        }
    }

    public void dropConnections() {
        for(Connection connection : connectionMap.values()) {
            connection.destroy();
        }
        connectionMap.clear();
    }
}
