package com.damintsev.server.db;


import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

/**
 * User: Damintsev Andrey
 * Date: 19.08.13
 * Time: 23:18
 */
public class Mysql {

    private static Properties prop;

    public static Connection getConnection() {
        Connection connect = null;
        try {
            if (prop == null) {
                prop = new Properties();
                prop.load(Mysql.class.getClassLoader().getResourceAsStream("/database-prod.properties"));
            }
            Class.forName(prop.getProperty("driverClassName"));
            connect = DriverManager.getConnection(prop.getProperty("url"), prop);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return connect;
    }
}
