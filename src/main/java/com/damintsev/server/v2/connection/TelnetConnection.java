package com.damintsev.server.v2.connection;

import com.damintsev.client.devices.Response;
import com.damintsev.client.devices.Station;
import com.damintsev.server.telnet.TelnetWorker;
import com.damintsev.server.v2.Task.Task;

import java.util.Properties;

/**
 * User: Damintsev Andrey
 * Date: 06.10.13
 * Time: 16:28
 */
public class TelnetConnection extends Connection {

    private TelnetWorker worker;
    private Station station;

    @Override
    public Connection init(Station station) {
        worker = new TelnetWorker();
//        worker.setLogin(properties.getProperty("login"));
//        worker.setPassword(properties.getProperty("password"));
//        worker.setHost(properties.getProperty("host"));
//        worker.setPort(properties.getProperty("port"));
        worker.connect();
        return this;
    }

    @Override
    public Response process(Task task) {
       if(worker == null || !worker.isConnected())
           throw new RuntimeException("Telnet worker not initialized!");
        return task.process(worker.execute(task.getCommand()));
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
