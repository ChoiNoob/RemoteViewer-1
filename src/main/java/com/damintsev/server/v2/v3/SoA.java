package com.damintsev.server.v2.v3;

import com.damintsev.client.devices.Station;
import com.damintsev.server.db.DatabaseConnector;
import com.damintsev.server.v2.Task.Task;
import com.damintsev.server.v2.connection.ConnectionPool;
import com.damintsev.server.v2.machine.state.TaskState;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * User: Damintsev Andrey
 * Date: 07.10.13
 * Time: 23:53
 */
public class SoA {

    private static SoA instance;
    private static Map<String, ThreadExecutor> threads = new HashMap<String, ThreadExecutor>();

    public static SoA getInstance() {
        if (instance == null) instance = new SoA();
        return instance;
    }

    private SoA() {
        List<Station> stations = DatabaseConnector.getInstance().getStationList();
        for(Station station : stations) {
            List<Task> tasks = new ArrayList<Task>() ;
            ThreadExecutor thread = new ThreadExecutor(station, tasks, new HashMap<String, TaskState>());
            threads.put("" + station.getId(), thread);
        }
    }

    public void shutdown() {
        for(ThreadExecutor thread : threads.values()) {
            thread.interrupt();
        }
        ConnectionPool.getInstance().dropConnections();
    }
}
