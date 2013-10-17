package com.damintsev.server.v2.v3;

import com.damintsev.client.v3.items.Station;
import com.damintsev.client.v3.items.task.Task;
import com.damintsev.client.v3.items.task.executors.TaskPool;
import com.damintsev.client.v3.items.task.executors.TaskProcessor;
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

    private static long threadId = 0;
    private static final Logger logger = LoggerFactory.getLogger(ThreadExecutor.class);
    private Station station;
    private int delay;
    private List<Task> tasks;
    private Map<String, TaskState> taskStates;
    private Map<String, Integer> errors;
    private boolean start = false;
    private long thisThreadId = ++threadId;
    private Iterator<Task> iterator;
    private boolean needToPause;

    public ThreadExecutor(final Station station, List<Task> tasks, Map<String, TaskState> map) {
        logger.info("initializing Tread executor with station=" + station.getId() + " name=" + station.getName());
        this.station = station;
        delay = station.getDelay() == null ? 5 : station.getDelay();
        this.tasks = tasks;
        this.taskStates = map;
        errors = new HashMap<String, Integer>(tasks.size() + 1);
        //todo надо ли инициализировать коннект сейчас ?
        for(Task task : tasks) {
            taskStates.put(task.getStringId(), new TaskState(ExecuteState.INIT));
        }
        start();
        logger.info("Starting current thread");
    }

    private void executeTask(Task task) {
        TaskState state;
        try {
            logger.info("Executing task id=" + task.getStringId() + " name=" + task.getName() + " type=" + task.getType());
            Connection connection = ConnectionPool.getInstance().getConnection(task);
            TaskProcessor taskProcessor = TaskPool.getInstance().getTaskProcessor(task.getType());
            state = taskProcessor.process(connection.execute(task));
            state.setId(task.getStringId());
            checkForErrors(task);
        } catch (ConnectException conn) {
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
        while (start) {
            if (iterator == null) iterator = tasks.iterator();
            if (iterator.hasNext())
                executeTask(iterator.next());
            else
                iterator = tasks.iterator();
            try {
                Thread.sleep(delay * 1000);
                synchronized (this) {
                    while (needToPause) {
                        wait();
                    }
                }
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
        needToPause = false;
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

//    public void updateTask(Task newTask) {
//        pause();
//        Task taskToRemove = null;
//        for (Task oldTask : tasks) {
//            if (oldTask.getStringId().equals(newTask.getStringId())) {
//                taskToRemove = oldTask;
//            }
//        }
//        if (taskToRemove != null) {
//            logger.info("Deleting old task");
//            tasks.remove(taskToRemove);
//            //todo не нравится мне это!!!
//            ConnectionPool.getInstance().dropConnection(taskToRemove.getStation());
//        }
//        tasks.add(newTask);
//        taskStates.put(newTask.getStringId(), new TaskState());
//
//        iterator = null;
//        unpause();
//    }
//
//    public void updateStation(Station station) {
//        pause();
//        ConnectionPool.getInstance().dropConnection(station);
//        System.out.println("dropped");
//        for(Task task : tasks) {  //todo не очень красиво как мне кажется
//            task.setStation(station);
//        }
//        iterator = null;
//        unpause();
//    }

    public void destroyProcess() {
        ConnectionPool.getInstance().dropConnection(station);
        tasks.clear();
        taskStates.clear();
        errors.clear();
        interrupt();
    }

//    public void deleteTask(Task task) {
//        pause();
//        for(Task oldTask : tasks) {
//            if(oldTask.getStringId().equals(task.getStringId())) {
//                tasks.remove(oldTask);
//            }
//        }
//        taskStates.remove(task.getStringId());
//        errors.remove(task.getStringId());
//        iterator = null;
//        unpause();
//    }

    public void pause() {
        System.out.println("CPT PAUSE");
        needToPause = true;
    }

    public synchronized void  unpause() {
        System.out.println("CPT UNPAUSE");
        needToPause = false;
        this.notifyAll();
    }
}
