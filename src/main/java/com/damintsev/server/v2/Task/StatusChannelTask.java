package com.damintsev.server.v2.Task;

import com.damintsev.client.devices.Response;
import com.damintsev.client.devices.Station;
import com.damintsev.server.v2.Task.Task;
import com.damintsev.server.v2.connection.Connection;
import com.damintsev.server.v2.machine.state.TaskState;

/**
 * User: Damintsev Andrey
 * Date: 06.10.13
 * Time: 16:55
 */
public class StatusChannelTask extends Task {

    @Override
    public String getCommand() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Station getStation() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public TaskState process(Response execute) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
