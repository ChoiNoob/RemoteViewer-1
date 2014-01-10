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
import java.sql.SQLException;

/**
 * Created by adamintsev
 * Date: 28.08.13 12:11
 */
public class ShutdownServlet implements ServletContextListener{

//    private ScheduledExecutorService scheduler;

    public void contextInitialized(ServletContextEvent servletContextEvent) {
    }

    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        System.out.println("CPT!!!!!");
        Executor.getInstance().shutdown();
        try {
            Mysql.shutdownConnections();
        } catch (SQLException e) {
            e.printStackTrace();  //Todo change body of catch statement use File | Settings | File Templates.
        }
//        BillingWorker.getInstance().stopWorker();
//        TelnetScheduler.getInstance().stopWorker();
//        scheduler.shutdownNow();
//        BillingStatistics.getInstance().stopWorker();
//        DatabaseConnector.getInstance().stopDatabaseConnector();
        com.damintsev.server.old.Executor.getInstance().shutdownAll();
    }
}
