package com.damintsev.server.db;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * User: Damintsev Andrey
 * Date: 19.08.13
 * Time: 23:18
 */
public class Mysql {

    private static Properties prop;
    private static List<Connection> connections = new ArrayList<Connection>();

    public static Connection getConnection() {
        Connection connect = null;
        try {
            if (prop == null) {
                prop = new Properties();
                prop.load(Mysql.class.getClassLoader().getResourceAsStream("/database-local.properties"));
            }
            Class.forName(prop.getProperty("driverClassName"));
            connect = DriverManager.getConnection(prop.getProperty("url"), prop);
            connections.add(connect);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return connect;
    }

    public static void shutdownConnections() throws SQLException {
        for(Connection connection : connections) {
            if(!connection.isClosed()) connection.close();//todo create intelligent array!
        }
    }
}
