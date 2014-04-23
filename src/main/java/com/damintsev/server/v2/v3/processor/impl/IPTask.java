package com.damintsev.server.v2.v3.processor.impl;

import com.damintsev.gwt.client.source.uientity.ExecuteState;
import com.damintsev.gwt.client.source.uientity.TaskState;
import com.damintsev.server.v2.v3.processor.TaskProcessor;
import org.apache.log4j.Logger;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * User: Damintsev Andrey
 * Date: 09.10.13
 * Time: 21:19
 */
public class IPTask implements TaskProcessor {

    private static final Logger logger = Logger.getLogger(TaskProcessor.class);

    @Override
    public TaskState process(String command) {
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
