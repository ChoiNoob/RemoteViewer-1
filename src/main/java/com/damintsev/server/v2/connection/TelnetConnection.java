package com.damintsev.server.v2.connection;

import com.damintsev.client.devices.Response;
import com.damintsev.client.devices.Station;
import com.damintsev.server.telnet.TelnetWorker;

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
    public void create(Properties properties) {
        worker = new TelnetWorker();
        worker.setLogin(properties.getProperty("login"));
        worker.setPassword(properties.getProperty("password"));
        worker.setHost(properties.getProperty("host"));
        worker.setPort(properties.getProperty("port"));
        worker.connect();
    }

    @Override
    public Response process(String command) {
       if(worker == null || !worker.isConnected())
           throw new RuntimeException("Telnet worker not initialized!");
        return worker.execute(command);
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
