package com.damintsev.servlet;

/**
 * User: Damintsev Andrey
 * Date: 06.10.13
 * Time: 11:13
 */

import com.damintsev.server.old.Executor;
import com.damintsev.server.old.Executor;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * Created by adamintsev
 * Date: 28.08.13 12:11
 */
public class Servlet implements ServletContextListener{

//    private ScheduledExecutorService scheduler;

    public void contextInitialized(ServletContextEvent servletContextEvent) {
    }

    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        System.out.println("CPT!!!!!");
//        BillingWorker.getInstance().stopWorker();
//        TelnetScheduler.getInstance().stopWorker();
//        scheduler.shutdownNow();
//        BillingStatistics.getInstance().stopWorker();
//        DatabaseConnector.getInstance().stopDatabaseConnector();
        Executor.getInstance().shutdownAll();
    }
}
