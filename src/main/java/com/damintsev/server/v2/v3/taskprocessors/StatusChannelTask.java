package com.damintsev.server.v2.v3.taskprocessors;

import com.damintsev.common.beans.ExecuteState;
import com.damintsev.common.beans.TaskState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * User: Damintsev Andrey
 * Date: 06.10.13
 * Time: 16:55
 */
public class StatusChannelTask extends TaskProcessor {

    private static final Logger logger = LoggerFactory.getLogger(StatusChannelTask.class);

    @Override
    public TaskState process(String command)  {
        TaskState response = new TaskState();

        logger.info("Executing command=" + command);
        int index = command.indexOf("PP NW");
        if (index > 0) {
            command = command.substring(index, command.length());
            logger.debug("After substring: " + command);
        }
        if (command.contains("READY")) {
            logger.info("After parse result is WORK");
            response.setMessage("After parse result is WORK");
            response.setState(ExecuteState.WORK);
        } else if (command.contains("NEC")) {
            logger.info("After parse result is WARNING");
            response.setState(ExecuteState.WARNING);
        } else {
            logger.info("After parse result is ERROR");
            response.setState(ExecuteState.ERROR);
        }
        return response;
    }
}
