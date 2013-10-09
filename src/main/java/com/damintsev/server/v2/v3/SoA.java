package com.damintsev.server.v2.v3;

import com.damintsev.client.devices.Station;
import com.damintsev.server.db.DB;
import com.damintsev.client.v3.items.task.Task;
import com.damintsev.server.v2.v3.connections.ConnectionPool;
import com.damintsev.client.v3.items.task.TaskState;

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
    private Map<Long, TaskState> stateMap = new ConcurrentHashMap<Long, TaskState>();

    public static SoA getInstance() {
        if (instance == null) instance = new SoA();
        return instance;
    }

    private SoA() {
        List<Station> stations = DB.getInstance().getStationList();
        for(Station station : stations) {
            List<Task> tasks = DB.getInstance().loadTasksForStation(station);
            ThreadExecutor thread = new ThreadExecutor(station, tasks, stateMap);
            threads.put(thread.getThreadId(), thread);
        }
    }

    public void shutdown() {
        for(ThreadExecutor thread : threads.values()) {
            thread.interrupt();
        }
        ConnectionPool.getInstance().dropConnections();
    }

    public Map<Long, TaskState> getStates() {
        return stateMap;
    }
}
