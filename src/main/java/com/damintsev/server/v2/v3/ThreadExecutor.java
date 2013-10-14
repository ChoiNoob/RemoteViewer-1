package com.damintsev.server.v2.v3;

import com.damintsev.client.v3.items.Station;
import com.damintsev.client.v3.items.task.Task;
import com.damintsev.server.v2.v3.connections.Connection;
import com.damintsev.server.v2.v3.connections.ConnectionPool;
import com.damintsev.client.v3.items.task.ExecuteState;
import com.damintsev.client.v3.items.task.TaskState;
import com.damintsev.server.v2.v3.exceptions.ConnectException;
import com.damintsev.server.v2.v3.exceptions.ExecutingTaskException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

    private static final Logger logger = LoggerFactory.getLogger(ThreadExecutor.class);
    private Station station;
    private List<Task> tasks;
    private Map<String, TaskState> taskStates;
    private Map<String, Integer> errors;
    private boolean start = false;
    private static long threadId = 0;
    private long thisThreadId = ++threadId;

    public ThreadExecutor(final Station station, List<Task> tasks, Map<String, TaskState> map) {
        logger.info("initializing Tread executor with station=" + station.getId() + " name=" + station.getName());
        this.station = station;
        this.tasks = tasks;
        this.taskStates = map;
        errors = new HashMap<String, Integer>(tasks.size() + 1);
//        new Runnable() {
//            public void run() {
//                try {
//                    ConnectionPool.getInstance().create(station);
//                } catch (ConnectException conn) {
//                    //todo придумать что делать с айдишниками
//                }
//            }
//        }.run();
        for(Task task : tasks) {
            taskStates.put(task.getStringId(), new TaskState(ExecuteState.INIT));
        }
        start();
        logger.info("Starting current thread");
    }

    private void executeTask(Task task) {
        TaskState state;
        try {
            logger.info("Execturing task id=" + task.getStringId() + " name=" + task.getName() + " type=" + task.getType());
            Connection connection = ConnectionPool.getInstance().getConnection(task);
            System.out.println("conn=" + connection);
            state = connection.execute(task);
            state.setId(task.getStringId());
            checkForErrors(task);
        }
//        catch (NullPointerException e) {
//            System.out.println("cufkf");
//            state = new TaskState();
//        }
        catch (ConnectException conn) {
            logger.info("Caught connection error " + conn.getLocalizedMessage());
            state = createConnectionError(task.getStringId(), conn, true);
        }   catch (ExecutingTaskException exec) {
            logger.info("Caught executing error " + exec.getLocalizedMessage());
            state = createConnectionError(task.getStringId(), exec, false);
        }
        taskStates.put(task.getStringId(), state);
    }

    private void checkForErrors(Task task) {
        if(errors.get(task.getStringId()) != null && errors.get(task.getStringId()) > 0)
            errors.put(task.getStringId(), 0);
    }

    public void run() {
        Thread.currentThread().setName("threadI=" + thisThreadId + " id=" + station.getId() + " name=" + station.getName());
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
            taskStates.put(task.getStringId(), new TaskState());
        }
        super.interrupt();
    }

    public String getThreadId() {
        return station.getStringId();
    }

    private TaskState createConnectionError(String taskId, Exception conn, boolean connectionError) {
        logger.info("Created connection error!");
        TaskState task = new TaskState(ExecuteState.ERROR, conn.getMessage());
        task.setId(taskId);
        if (errors.get(taskId) == null) errors.put(taskId, 1);
        if (errors.get(taskId) <= 2) {
            task.setState(ExecuteState.WARNING);
        } else if (errors.get(taskId) > 2) {
            logger.info("Setting ERROR to taskId=" + taskId);
            task.setState(ExecuteState.ERROR);
            if (connectionError) {
                for (TaskState state : taskStates.values()) {
                    if (state.getId().equals(taskId)) continue;     //todo нйти все таски для станции и пометить их Андейфайнед
                        state.setState(ExecuteState.UNKNOWN);
                }
            }
        }
        errors.put(taskId, errors.get(taskId) + 1);
        return task;
    }

    public void updateTask(Task newTask) {
        if(start) interrupt();
        for(Task oldTask : tasks) {
            if(oldTask.getStringId().equals(newTask.getStringId())) {
                tasks.remove(oldTask);
                tasks.add(newTask);
            }
        }
        taskStates.put(newTask.getStringId(), new TaskState());
        if(!start) start();
    }

    public void updateStation(Station station) {
        if(start) interrupt();
        ConnectionPool.getInstance().dropConnection(station);
        for(Task task : tasks) {  //todo не очень красиво как мне кажется
            task.setStation(station);
        }
        if(!start) start();

    }
}
