package com.damintsev.server.v2.v3.processor;

import com.damintsev.gwt.client.source.uientity.TaskState;
import com.damintsev.server.v2.v3.exceptions.ExecutingTaskException;

/**
 * User: Damintsev Andrey
 * Date: 06.10.13
 * Time: 16:54
 */
public interface TaskProcessor {

    TaskState process(String command) throws ExecutingTaskException;
}
