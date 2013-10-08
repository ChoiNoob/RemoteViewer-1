package com.damintsev.server.v2.process;

import com.damintsev.client.devices.Station;
import com.damintsev.server.v2.v3.task.Task;

import java.util.ArrayList;
import java.util.List;

/**
 * User: Damintsev Andrey
 * Date: 06.10.13
 * Time: 17:02
 */
public class MainProcessing {

    List<Task> tasks = new ArrayList<Task>();
    List<Processing> processingList = new ArrayList<Processing>();

    public MainProcessing(){
        //load tasks
        List<Station> stations = new ArrayList<Station>();
        for(Task task : tasks) {

        }
        processingList.add(new Processing(stations.get(0), tasks));

    }
}
