package com.damintsev.server.v2.v3;

import com.damintsev.client.v3.items.Station;
import com.damintsev.server.db.DB;
import com.damintsev.client.v3.items.task.Task;
import com.damintsev.server.v2.v3.connections.ConnectionPool;
import com.damintsev.client.v3.items.task.TaskState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * User: Damintsev Andrey
 * Date: 07.10.13
 * Time: 23:53
 */
public class SoA {

    private static SoA instance;
    private static Map<String, ThreadExecutor> threads = new HashMap<String, ThreadExecutor>();
    private Map<String, TaskState> stateMap = new ConcurrentHashMap<String, TaskState>();
    private static final Logger logger = LoggerFactory.getLogger(SoA.class);
    private String threadName = Thread.currentThread().getName() + " ";

    public static SoA getInstance() {
        if (instance == null) {instance = new SoA();logger.info("new instance!");}
        return instance;
    }

    public SoA() {
                List<Station> stations = DB.getInstance().getStationList();
        logger.info(threadName + "Loaded from database " + stations.size() + " stations");
        for (Station station : stations) {
            createWorker(station);
        }
//                logger.info(threadName + "Loaded from database " + stations.size() + " stations");
//                for (Station station : stations) {
//                    List<Task> tasks = DB.getInstance().loadTasksForStation(station);
//                    logger.info(threadName + "For station id=" + station.getId() + " name=" + station.getName() + " loaded tasks=" + tasks.size());
//                    ThreadExecutor thread = new ThreadExecutor(station, tasks, stateMap);
//                    threads.put(thread.getThreadId(), thread);
//                }
//            }
//        }.run();
    }

    private void createWorker(Station station) {
        List<Task> tasks = DB.getInstance().loadTasksForStation(station);
        logger.info(threadName + "For station id=" + station.getId() + " name=" + station.getName() + " loaded tasks=" + tasks.size());
        ThreadExecutor thread = new ThreadExecutor(station, tasks, stateMap);
        threads.put(thread.getThreadId(), thread);
    }

    public void shutdown() {
        for (ThreadExecutor thread : threads.values()) {
            thread.interrupt();
        }
        ConnectionPool.getInstance().dropConnections();
    }

    public Map<String, TaskState> getStates() {
        return stateMap;
    }

    public void updateStation(Station station) {
        ThreadExecutor thread = threads.get(station.getStringId());
        thread.updateStation(station);
    }

    public void deleteStation(Station station) {
        threads.get(station.getStringId()).destroyProcess();
        threads.remove(station.getStringId());
    }

    public void updateTask(Task task) {
        String stationId = task.getParentId();
        ThreadExecutor thread = threads.get(stationId);
        thread.updateTask(task);
    }

    public void deleteTask(Task task) {
        ThreadExecutor executor = threads.get(task.getParentId());
        executor.deleteTask(task);
    }
}
