package com.damintsev.server.v2.v3.connections;

import com.damintsev.client.devices.Station;
import com.damintsev.server.telnet.TelnetWorker;
import com.damintsev.client.v3.items.task.Task;
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
        worker.setLogin(station.getLogin());
        worker.setPassword(station.getPassword());
        worker.setHost(station.getHost());
        worker.setPort(station.getPort());
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
