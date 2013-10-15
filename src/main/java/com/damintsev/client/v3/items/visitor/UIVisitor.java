package com.damintsev.client.v3.items.visitor;

import com.damintsev.client.devices.UIItem;
import com.damintsev.client.v3.items.Label;
import com.damintsev.client.v3.items.Station;
import com.damintsev.client.v3.items.task.Task;

/**
 * User: adamintsev Date: 15.10.13 Time: 16:55
 */
//todo перенести в интерфейс!
public class UIVisitor implements Visitor<UIItem> {

    public UIItem visit(Label label) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public UIItem visit(Station station) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public UIItem visit(Task task) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
