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
        Connection connection = connectionMap.get(task.getParentId() + task.getType());
        if (connection == null) {
            logger.info("Connection not found in pool. Initialising new connection");
            connection = create(task.getStation(), task.getType());
        }
//        System.out.println("isConnected=" + connection.isConnected());
//        if(!connection.isConnected()) {
//            connectionMap.remove(task.getParentId() + task.getType());
//            throw new ConnectException("Connection not Alive");
//        }
        return connection;
    }

    public Connection create(Station station, TaskType type) throws ConnectException {
        logger.info("Createing connection with id=" + station.getStringId() + type);
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

    public void dropConnection(Station station, TaskType type) {
        if(connectionMap.get(station.getStringId() + type) != null) {
            System.out.println("destroyng connection=" +station.getStringId() + type);
            connectionMap.get(station.getStringId() + type).destroy();
            connectionMap.remove(station.getStringId() + type);
        }
    }

    public void dropConnections() {
        for(Connection connection : connectionMap.values()) {
            connection.destroy();
        }
        connectionMap.clear();
    }
}
