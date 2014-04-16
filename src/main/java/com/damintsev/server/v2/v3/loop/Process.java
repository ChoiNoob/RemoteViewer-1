package com.damintsev.server.v2.v3.loop;

import com.damintsev.common.uientity.ExecuteState;
import com.damintsev.common.uientity.Station;
import com.damintsev.common.uientity.Task;
import com.damintsev.common.uientity.TaskState;
import com.damintsev.server.v2.v3.connections.Connection;
import com.damintsev.server.v2.v3.connections.ConnectionPool;
import com.damintsev.server.v2.v3.exceptions.ConnectionException;
import com.damintsev.server.v2.v3.exceptions.ExecutingTaskException;
import com.damintsev.server.v2.v3.processor.TaskPool;
import com.damintsev.server.v2.v3.processor.TaskProcessor;
import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Author damintsev
 * 4/16/2014
 */
public class Process implements Runnable {

    private static long processId = 0;
    private static final Logger logger = Logger.getLogger(Process.class);

    private final Station station;
    private final int delay;
    private final long thisProcessId = ++processId;
    private int listPointer;

    private List<Task> tasks;
    private Map<String, TaskState> taskStates;
    private Map<String, Integer> errors;

    public Process(final Station station, List<Task> tasks, Map<String, TaskState> map) {
        logger.info("Initializing Process #" + thisProcessId + " with station=" + station.getId() + " name=" + station.getName());
        this.station = station;
        delay = station.getDelay() == null ? 5 : station.getDelay();
        this.tasks = tasks;
        this.taskStates = map;
        errors = new HashMap<>(tasks.size() + 1);
        for(Task task : tasks) {
            taskStates.put(task.getStringId(), new TaskState(task.getStringId(), ExecuteState.INIT));
        }
    }

    @Override
    public void run() {
        Task task = getNextTask();
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
    //todo убрать !!! методы вообще одинаковые
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

        }
        errors.put(stringID, errors.get(stringID) + 1);
        return task;
    }
}
