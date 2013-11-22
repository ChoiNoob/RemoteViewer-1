package com.damintsev.common.visitor;

import com.damintsev.client.v3.uiitems.*;
import com.damintsev.common.beans.Label;
import com.damintsev.common.beans.Station;
import com.damintsev.common.beans.Task;

/**
 * User: adamintsev Date: 15.10.13 Time: 16:55
 */
//todo перенести в интерфейс!
public class UIVisitor implements Visitor<UIItem> {

    public UIItem visit(Label label) {
        return new UILabel(label);
    }

    public UIItem visit(Station station) {
        return new UIStation(station);
    }

    public UIItem visit(Task task) {
        return new UITask(task);
    }
}
