package com.damintsev.server.v2.v3.connections;

import com.damintsev.common.uientity.Station;
import com.damintsev.common.uientity.Task;
import com.damintsev.server.v2.v3.exceptions.ConnectionException;
import com.damintsev.server.v2.v3.exceptions.ExecutingTaskException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * User: Damintsev Andrey
 * Date: 14.10.13
 * Time: 21:55
 */
public class PingConnection extends Connection {

    private Process process;
    private InputStream is = null;

    @Override
    public Connection init(Station station) throws ConnectionException {
        return this;
    }

    @Override
    public String execute(Task task) throws ExecutingTaskException {
        try {
            process = Runtime.getRuntime().exec(task.getCommand());
            is = process.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
            return sb.toString();
        } catch (IOException e) {
            throw new ExecutingTaskException(e);
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();  //todo нужно что-то делать ?!
                }
            }
        }
    }

    @Override
    public void destroy() {
        if (process!=null)
            process.destroy();
        if (is != null) {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();  //todo нужно что-то делать ?!
            }
        }
    }

    @Override
    public boolean isConnected() {
        return true;
    }
}
