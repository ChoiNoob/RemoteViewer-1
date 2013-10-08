package com.damintsev.server.v2.v3.connections;

import com.damintsev.client.devices.Station;
import com.damintsev.server.telnet.TelnetWorker;
import com.damintsev.server.v2.v3.task.Task;
import com.damintsev.server.v2.v3.task.executors.TaskExecutor;
import com.damintsev.server.v2.v3.task.TaskState;
import com.damintsev.server.v2.v3.exceptions.ConnectException;
import com.damintsev.server.v2.v3.exceptions.ExecutingTaskException;

/**
 * User: Damintsev Andrey
 * Date: 06.10.13
 * Time: 16:28
 */
public class TelnetConnection extends Connection {

    private TelnetWorker worker;
    private Station station;

    @Override
    public Connection init(Station station) throws ConnectException {
        worker = new TelnetWorker();
//        worker.setLogin(properties.getProperty("login"));
//        worker.setPassword(properties.getProperty("password"));
//        worker.setHost(properties.getProperty("host"));
//        worker.setPort(properties.getProperty("port"));
        worker.connect();
        return this;
    }

    @Override
    public String process(Task task) throws ExecutingTaskException {
       if(worker == null || !worker.isConnected())
           throw new RuntimeException("Telnet worker not initialized!");
        return worker.execute(task.getCommand()).getResultText(); //todo convert to string!
    }

    @Override
    public void destroy() {
        worker.disconnect();
    }

    @Override
    public Long getId() {
        return station.getId();
    }
}
