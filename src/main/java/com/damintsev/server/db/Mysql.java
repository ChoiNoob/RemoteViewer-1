package com.damintsev.server.db;


import java.sql.Connection;
import java.sql.DriverManager;

/**
 * User: Damintsev Andrey
 * Date: 19.08.13
 * Time: 23:18
 */
public class Mysql {

    public static Connection getConnection() {
        Connection connect = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connect = DriverManager.getConnection("jdbc:mysql://localhost/viewer?"
                    + "user=root&password=chiken");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return connect;
    }
}
