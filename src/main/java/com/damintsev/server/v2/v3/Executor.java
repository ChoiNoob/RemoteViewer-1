package com.damintsev.server.v2.v3;

import com.damintsev.client.old.devices.Item;
import com.damintsev.common.beans.Station;
import com.damintsev.common.beans.Task;
import com.damintsev.common.beans.TaskState;
import com.damintsev.server.dao.DataBase;
import com.damintsev.server.db.DB;
import com.damintsev.server.v2.v3.connections.ConnectionPool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * User: Damintsev Andrey
 * Date: 07.10.13
 * Time: 23:53
 */
public class Executor {

    @Autowired
    private DataBase dataBase;

    private static final Logger logger = LoggerFactory.getLogger(Executor.class);

    private static Executor instance;

    private static Map<String, ThreadExecutor> threads = new HashMap<String, ThreadExecutor>();
    private Map<String, TaskState> stateMap = new ConcurrentHashMap<String, TaskState>();
    private String threadName = Thread.currentThread().getName() + " ";

    public static Executor getInstance() {
        if (instance == null) instance = new Executor();
        return instance;
    }

    public Executor() {
        List<Station> stations = DB.getInstance().getStationList();
        logger.info(threadName + "Loaded from instance " + stations.size() + " stations");
        for (Station station : stations) {
            createWorker(station);
        }
    }

    private void createWorker(Station station) {
        List<Task> tasks = DB.getInstance().loadTasksForStation(station);
        logger.info(threadName + "For station id=" + station.getId() + " name=" + station.getName() + " loaded tasks=" + tasks.size());
        ThreadExecutor thread = new ThreadExecutor(station, tasks, stateMap);
        threads.put(thread.getThreadId(), thread);
    }

    public void shutdown() {
        for (ThreadExecutor thread : threads.values()) {
            logger.info("Stopping thread with id=" + thread.getId());
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
