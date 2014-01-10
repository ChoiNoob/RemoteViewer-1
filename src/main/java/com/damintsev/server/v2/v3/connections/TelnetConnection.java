package com.damintsev.server.v2.v3.connections;

import com.damintsev.common.beans.Station;
import com.damintsev.common.beans.Task;
import com.damintsev.server.v2.v3.connections.telnet.TelnetWorker;
import com.damintsev.server.v2.v3.exceptions.ConnectionException;
import com.damintsev.server.v2.v3.exceptions.ExecutingTaskException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * User: Damintsev Andrey
 * Date: 06.10.13
 * Time: 16:28
 */
public class TelnetConnection extends Connection {

    private static final Logger logger = LoggerFactory.getLogger(TelnetConnection.class);
    private TelnetWorker worker;
    private Station station;

    @Override
    public Connection init(Station station) throws ConnectionException {
        worker = new TelnetWorker();
        worker.setLogin(station.getLogin());
        worker.setPassword(station.getPassword());
        worker.setHost(station.getHost());
        worker.setPort(station.getPort());
        logger.info("Trying to connect host=" + station.getHost() + " login=" + station.getLogin());
        worker.connect();
        return this;
    }

    @Override
    public String execute(Task task) throws ExecutingTaskException, ConnectionException {
       if(worker == null || !worker.isConnected())
           throw new ConnectionException("Telnet worker not initialized!");
        return worker.execute(task.getCommand()).getResultText(); //todo convert to string!
    }

    @Override
    public void destroy() {
        worker.disconnect();
    }

    @Override
    public boolean isConnected() {
        return worker != null && worker.isConnected();
    }
}
