package com.damintsev.server.v2.v3.processor.impl;

import com.damintsev.gwt.client.source.uientity.ExecuteState;
import com.damintsev.gwt.client.source.uientity.TaskState;
import com.damintsev.server.v2.v3.processor.TaskProcessor;
import org.apache.log4j.Logger;

/**
 * User: Damintsev Andrey
 * Date: 06.10.13
 * Time: 16:55
 */
public class StatusChannelTask implements TaskProcessor {

    private static final Logger logger = Logger.getLogger(StatusChannelTask.class);

    @Override
    public TaskState process(String command)  {
        TaskState response = new TaskState();

        logger.debug("Executing command=" + command);
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
        response.setMessage(command);
        return response;
    }
}
