package com.damintsev.server.v2.v3;

import com.damintsev.client.devices.Station;
import com.damintsev.client.v3.items.task.Task;
import com.damintsev.server.v2.v3.connections.Connection;
import com.damintsev.server.v2.v3.connections.ConnectionPool;
import com.damintsev.client.v3.items.task.ExecuteState;
import com.damintsev.client.v3.items.task.TaskState;
import com.damintsev.server.v2.v3.exceptions.ConnectException;
import com.damintsev.server.v2.v3.exceptions.ExecutingTaskException;

import java.util.HashMap;
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
    private Map<Long, Integer> errors;
    private boolean start = false;

    public ThreadExecutor(final Station station, List<Task> tasks, Map<String, TaskState> map) {
        this.stationId = station.getId();
        this.tasks = tasks;
        this.taskStates = map;
        errors = new HashMap<Long, Integer>(tasks.size() + 1);
        new Runnable() {
            public void run() {
                try {
                    ConnectionPool.getInstance().create(station);
                } catch (ConnectException conn) {
                    //todo придумать что делать с айдишниками
                }
            }
        }.run();
        for(Task task : tasks) {
            taskStates.put(task.getStringId(), new TaskState(ExecuteState.INIT));
        }
    }

    private void executeTask(Task task) {
        TaskState state;
        try {
            Connection connection = ConnectionPool.getInstance().getConnection(task);
            state = connection.execute(task);
            checkForErrors(task);
        }   catch (ConnectException conn) {
            state = createConnectionError(task.getId(), conn, true);
        }   catch (ExecutingTaskException exec) {
            state = createConnectionError(task.getId(), exec, false);
        }
        taskStates.put(task.getStringId(), state);
    }

    private void checkForErrors(Task task) {
        if(errors.get(task.getId()) != null && errors.get(task.getId()) > 0)
            errors.put(task.getId(), 0);
    }

    public void run() {
        Iterator<Task> iterator = tasks.iterator();
        while (start) {
            if(iterator.hasNext())
                executeTask(iterator.next());
            else
                iterator = tasks.iterator();
            try {
                Thread.sleep(5000);   //todo parametrize
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
            taskStates.put(task.getStringId(), new TaskState(ExecuteState.INIT));
        }
        super.interrupt();
    }

    public String getThreadId() {
        return Thread.currentThread().getName() + ":" + stationId;
    }

    private TaskState createConnectionError(Long taskId, Exception conn, boolean connectionError) {
        TaskState task = new TaskState(ExecuteState.ERROR, conn.getMessage());
        if (errors.get(taskId) == null) errors.put(taskId, 1);
        if (errors.get(taskId) <= 2) {
            task.setState(ExecuteState.WARNING);
        } else if (errors.get(taskId) > 2) {
            task.setState(ExecuteState.ERROR);
            if (connectionError) {
                for (TaskState state : taskStates.values()) {
                    if (state.getId().equals(taskId)) continue;
                    state.setState(ExecuteState.UNKNOWN);
                }
            }
        }
        errors.put(taskId, errors.get(taskId) + 1);
        return task;
    }
}
