package com.damintsev.server.v2.v3.taskprocessors;

import com.damintsev.common.pojo.ExecuteState;
import com.damintsev.common.pojo.TaskState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * User: Damintsev Andrey
 * Date: 09.10.13
 * Time: 21:19
 */
public class IPTask extends TaskProcessor {

    private static final Logger logger = LoggerFactory.getLogger(TaskProcessor.class);

    @Override
    public TaskState process(String command) {
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
