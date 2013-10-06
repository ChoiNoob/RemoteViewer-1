package com.damintsev.server.v2.process;

import com.damintsev.client.devices.CommonDevice;
import com.damintsev.client.devices.Station;
import com.damintsev.server.v2.Task.Task;
import com.damintsev.server.v2.connection.Connection;
import com.damintsev.server.v2.connection.TelnetConnection;

import java.util.List;

/**
 * User: Damintsev Andrey
 * Date: 06.10.13
 * Time: 19:26
 */
public class Processing extends Thread {

    private Connection station;
    private List<Task> deviceList;

    public Processing(Station station, List<Task> deviceList) {


        this.deviceList = deviceList;
    }

    @Override
    public void run() {

    }


}
