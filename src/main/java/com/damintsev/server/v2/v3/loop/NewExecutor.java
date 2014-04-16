package com.damintsev.server.v2.v3.loop;

import com.damintsev.server.dao.StationDao;
import com.damintsev.server.db.DB;
import com.damintsev.server.v2.v3.Executor;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Author damintsev
 * 4/16/2014
 */
@Component
public class NewExecutor {

    private static final Logger logger = Logger.getLogger(NewExecutor.class);

    @Autowired
    private DB db;

    @Autowired
    private StationDao stationDao;

    @PostConstruct
    public void init() {

    }



    class ConnectionPool {
        ScheduledThreadPoolExecutor executorService = new ScheduledThreadPoolExecutor(12);
        
        public void addTask(Process process) {

            executorService.scheduleAtFixedRate(process, 1, 100, TimeUnit.SECONDS);
//            executorService.g
        }
        
    }
}
