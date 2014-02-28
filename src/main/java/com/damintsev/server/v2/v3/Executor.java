package com.damintsev.server.v2.v3;

import com.damintsev.client.old.devices.Item;
import com.damintsev.common.uientity.Station;
import com.damintsev.common.uientity.Task;
import com.damintsev.common.uientity.TaskState;
import com.damintsev.server.db.DB;
import com.damintsev.server.v2.v3.connections.ConnectionPool;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * User: Damintsev Andrey
 * Date: 07.10.13
 * Time: 23:53
 */
@Component
public class Executor {

    private static final Logger logger = Logger.getLogger(Executor.class);

    @Autowired
    private DB db;

    private Map<String, ThreadExecutor> threads = new HashMap<>();
    private Map<String, TaskState> stateMap = new ConcurrentHashMap<>();

    @PostConstruct
    public void init() {
        List<Station> stations = db.getStationList();
        logger.info("Loaded from instance " + stations.size() + " stations");
        for (Station station : stations) {
            createWorker(station);
        }
    }

    private void createWorker(Station station) {
        List<Task> tasks = db.loadTasksForStation(station);
        logger.info("For station id=" + station.getId() + " name=" + station.getName() + " loaded tasks=" + tasks.size());
        ThreadExecutor thread = new ThreadExecutor(station, tasks, stateMap);
        threads.put(thread.getThreadId(), thread);
    }

    @PreDestroy
    public void shutdown() {
        logger.info("Destroying all threads!");
        for (ThreadExecutor thread : threads.values()) {
            logger.info("Stopping thread with id=" + thread.getThreadId());
            thread.destroyProcess();
        }
        ConnectionPool.getInstance().dropConnections();
    }

    public Map<String, TaskState> getStates() {
        return stateMap;
    }

    public void updateEvent(Item item) {
        if(item.getStation() == null) return;
        ThreadExecutor threadExecutor = threads.get(item.getStation().getStringId());
        threadExecutor.destroyProcess();
        createWorker(item.getStation());
    }
}
