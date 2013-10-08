package com.damintsev.server.v2.machine.state;

import com.damintsev.client.devices.CommonDevice;
import com.damintsev.client.devices.Station;

import java.util.List;

/**
 * User: Damintsev Andrey
 * Date: 06.10.13
 * Time: 20:40
 */
public class InitState extends ExecuteState {

    @Override
    public int getNextState() {



        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public void setParameters(Station station, List<CommonDevice> devices) {

    }
}
