package com.damintsev.client.v3.items.task.executors;

import com.damintsev.client.v3.items.task.ExecuteState;
import com.damintsev.client.v3.items.task.TaskState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * User: Damintsev Andrey
 * Date: 09.10.13
 * Time: 21:19
 */
public class PingTask extends TaskExecutor {

    private static final Logger logger = LoggerFactory.getLogger(TaskExecutor.class);

    @Override
    protected TaskState process(String command) {
        logger.info("Command=" + command);
        TaskState state = new TaskState();
        Pattern pattern = Pattern.compile("[0-9]{1,3} bytes from ");
        Matcher matcher = pattern.matcher(command);
        if (matcher.find()) {
            logger.info("Pattern found. WORK");
            state.setState(ExecuteState.WORK);
        } else {
            logger.info("Pattern not found. ERROR");
            state.setState(ExecuteState.ERROR);
        }
        return state;
    }
}
