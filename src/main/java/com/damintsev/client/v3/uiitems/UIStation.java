package com.damintsev.client.v3.uiitems;

import com.damintsev.client.devices.Station;
import com.damintsev.client.devices.UIItem;
import com.google.gwt.user.client.ui.Widget;

/**
 * User: Damintsev Andrey
 * Date: 10.10.13
 * Time: 0:10
 */
public class UIStation extends UIItem {
    private Station station;

//    @Override
//    public Widget asWidget() {
//        return null;  //To change body of implemented methods use File | Settings | File Templates.
//    }

    public void setStation(Station station) {
        this.station = station;
    }

    public Station getStation() {
        return station;
    }
}
