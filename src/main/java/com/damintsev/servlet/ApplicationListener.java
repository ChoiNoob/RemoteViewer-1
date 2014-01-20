package com.damintsev.servlet;

/**
 * User: Damintsev Andrey
 * Date: 06.10.13
 * Time: 11:13
 */

import com.damintsev.server.db.Mysql;
import com.damintsev.server.v2.v3.Executor;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * Created by adamintsev
 * Date: 28.08.13 12:11
 */
public class ApplicationListener implements ServletContextListener {

    public void contextInitialized(ServletContextEvent servletContextEvent) {
        try {
            Mysql.get();
            Executor.getInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        Executor.getInstance().shutdown();
        Mysql.get().shutdownConnections();
    }
}
