package com.damintsev.server.v2.v3.taskprocessors;

import com.damintsev.common.uientity.TaskState;
import com.damintsev.server.v2.v3.exceptions.ExecutingTaskException;

/**
 * User: Damintsev Andrey
 * Date: 06.10.13
 * Time: 16:54
 */
public interface TaskProcessor {

    TaskState process(String command) throws ExecutingTaskException;
}
