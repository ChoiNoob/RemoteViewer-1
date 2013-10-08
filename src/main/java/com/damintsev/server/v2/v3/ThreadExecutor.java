package com.damintsev.server.v2.v3;

import com.damintsev.client.devices.Response;
import com.damintsev.client.devices.Station;
import com.damintsev.server.v2.Task.Task;
import com.damintsev.server.v2.connection.Connection;
import com.damintsev.server.v2.connection.ConnectionPool;
import com.damintsev.server.v2.machine.state.State;
import com.damintsev.server.v2.machine.state.TaskState;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * User: Damintsev Andrey
 * Date: 07.10.13
 * Time: 22:35
 */
public class ThreadExecutor extends Thread {

    private Long stationId;
    private List<Task> tasks;
    private Map<String, TaskState> taskStates;
    private boolean start = false;

    public ThreadExecutor(final Station station, List<Task> tasks, Map<String, TaskState> map) {
        this.stationId = station.getId();
        this.tasks = tasks;
        this.taskStates = map;
        new Runnable() {
            public void run() {
                try {
                    ConnectionPool.getInstance().create(station);
                } catch (Exception e) {
                    //todo set error to station
                    int i;
                }
            }
        }.run();
        for(Task task : tasks) {
            taskStates.put(task.getId(), new TaskState());
        }
    }

    private void executeTask(Task task) {
        Response response = null;
        try {
            Connection connection = ConnectionPool.getInstance().getConnection(task);
            response = connection.process(task);
        }   catch (Exception e) {
            response = new Response();
        }
        taskStates.put("",new TaskState());//todo
    }

    public void run() {
        Iterator<Task> iterator = tasks.iterator();
        while (start) {
            if(iterator.hasNext())
                executeTask(iterator.next());
            else
                iterator = tasks.iterator();
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void start() {
        start = true;
        super.start();
    }

    @Override
    public void interrupt() {
        start = false;
        for(Task task : tasks) {
            taskStates.put(task.getId(), new TaskState());
        }
        super.interrupt();
    }

    public String getThreadId() {
        return Thread.currentThread().getName() + ":" + stationId;
    }
}
