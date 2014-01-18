package com.damintsev.server.v2.v3;

import com.damintsev.common.beans.ExecuteState;
import com.damintsev.common.beans.Station;
import com.damintsev.common.beans.Task;
import com.damintsev.common.beans.TaskState;
import com.damintsev.server.v2.v3.connections.Connection;
import com.damintsev.server.v2.v3.connections.ConnectionPool;
import com.damintsev.server.v2.v3.exceptions.ConnectionException;
import com.damintsev.server.v2.v3.exceptions.ExecutingTaskException;
import com.damintsev.server.v2.v3.taskprocessors.TaskPool;
import com.damintsev.server.v2.v3.taskprocessors.TaskProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
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
    private int listPointer;
    private boolean start = false;
    private long thisThreadId = ++threadId;

    private boolean needToPause;
    private List<Task> tasks;
    private Map<String, TaskState> taskStates;
    private Map<String, Integer> errors;

    public ThreadExecutor(final Station station, List<Task> tasks, Map<String, TaskState> map) {
        logger.info("initializing Tread executor with station=" + station.getId() + " name=" + station.getName());
        this.station = station;
        delay = station.getDelay();
        this.tasks = tasks;
        this.taskStates = map;
        errors = new HashMap<String, Integer>(tasks.size() + 1);
        //todo надо инициализировать коннект сейчас
        for(Task task : tasks) {
            taskStates.put(task.getStringId(), new TaskState(task.getStringId(), ExecuteState.INIT));
        }
        start();
        logger.info("Starting current thread");
    }

    private void executeTask(Task task) {
        TaskState state;
        Connection connection;
        try {
            logger.info("Executing task id=" + task.getStringId() + " name=" + task.getName() + " type=" + task.getType() + " command=" + task.getCommand());
            connection = ConnectionPool.getInstance().getConnection(task);

            if (connection.isConnected())
                taskStates.put(task.getParentId(), new TaskState(task.getParentId(), ExecuteState.WORK));

            String result = connection.execute(task);

            TaskProcessor taskProcessor = TaskPool.getInstance().getTaskProcessor(task.getType());
            state = taskProcessor.process(result);
            state.setId(task.getStringId());
            checkForErrors(task);
        } catch (ConnectionException conn) {
            logger.info("Caught connection error " + conn.getLocalizedMessage());
            state = createConnectionError(task.getParentId(), conn);
            ConnectionPool.getInstance().dropConnection(task);
        }   catch (ExecutingTaskException exec) {
            logger.info("Caught executing error " + exec.getLocalizedMessage());
            state = createExecutionError(task.getStringId(), exec);
        }
        taskStates.put(state.getId(), state);
    }

    private void checkForErrors(Task task) {
        if(errors.get(task.getStringId()) != null)
            errors.put(task.getStringId(), 0);
    }

    private Task getNextTask() {
        if(listPointer == tasks.size()) listPointer = 0;
        return tasks.get(listPointer++);
    }

    public void run() {
        Thread.currentThread().setName("threadId=" + thisThreadId + " id=" + station.getId() + " name=" + station.getName());
        while (start) {
                executeTask(getNextTask());
            try {
                synchronized (this) {
                    this.wait(delay * 1000);// Thread.sleep(delay * 1000);
                    while (needToPause) {
                        this.wait();
                    }
                }
            } catch (InterruptedException e) {
//                e.printStackTrace();
                needToPause = false;
                start = false;
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
        needToPause = false;
        start = false;
        for(Task task : tasks) {
            taskStates.put(task.getStringId(), new TaskState(task.getStringId()));
        }
        super.interrupt();
    }

    public String getThreadId() {
        return station.getStringId();
    }

    private TaskState createExecutionError(String stringID, Exception conn) {
        logger.info("Created connection error!");
        TaskState task = new TaskState(stringID, ExecuteState.ERROR, conn.getMessage());
        if (errors.get(stringID) == null) errors.put(stringID, 1);
        if (errors.get(stringID) <= 2) {
            logger.info("Setting WARNING to taskId=" + stringID);
            task.setState(ExecuteState.WARNING);
        } else if (errors.get(stringID) > 2) {
            logger.info("Setting ERROR to taskId=" + stringID);
            task.setState(ExecuteState.ERROR);
        }
        errors.put(stringID, errors.get(stringID) + 1);
        return task;
    }

    private TaskState createConnectionError(String stringID, ConnectionException exception) {
        logger.info("Created connection error!");
        TaskState task = new TaskState(stringID, ExecuteState.ERROR, exception.getMessage());
        if (errors.get(stringID) == null) errors.put(stringID, 1);
        if (errors.get(stringID) <= 2) {
            logger.info("Setting WARNING to taskId=" + stringID);
            task.setState(ExecuteState.WARNING);
        } else {
            logger.info("Setting ERROR to taskId=" + stringID);
            task.setState(ExecuteState.ERROR);
            for (TaskState state : taskStates.values()) {
                if (state.getId().equals(stringID)) continue;
                System.out.println("For state id=" + state.getId());
                state.setState(ExecuteState.WARNING);
            }
        }
        errors.put(stringID, errors.get(stringID) + 1);
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
// todo       ConnectionPool.getInstance().dropConnection(station);
        ConnectionPool.getInstance().dropConnections();
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
