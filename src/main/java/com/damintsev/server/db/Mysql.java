package com.damintsev.server.db;


import com.mchange.v2.c3p0.ComboPooledDataSource;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

/**
 * User: Damintsev Andrey
 * Date: 19.08.13
 * Time: 23:18
 */
public class Mysql {

    private static Mysql instance;
    private static ComboPooledDataSource cpds;

    public static Mysql get() {
        if(instance == null) {
            try {
                instance = new Mysql();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return instance;
    }

    private Mysql() throws Exception {
        Properties prop = new Properties();
        prop.load(Mysql.class.getClassLoader().getResourceAsStream("/database-local.properties"));

        cpds = new ComboPooledDataSource();
        cpds.setDriverClass(prop.getProperty("driverClassName")); //loads the jdbc driver
        cpds.setJdbcUrl(prop.getProperty("url"));
        cpds.setUser(prop.getProperty("user"));
        cpds.setPassword(prop.getProperty("password"));

        cpds.setMinPoolSize(0);
        cpds.setAcquireIncrement(5);
        cpds.setMaxPoolSize(10);

    }

    public Connection getConnection() throws SQLException {
        return cpds.getConnection();
    }

    public void shutdownConnections() {
        cpds.close();
    }
}
