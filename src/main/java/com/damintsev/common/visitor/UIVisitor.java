package com.damintsev.common.visitor;

import com.damintsev.client.v3.uiitems.*;
import com.damintsev.common.uientity.Label;
import com.damintsev.common.uientity.Station;
import com.damintsev.common.uientity.Task;

/**
 * User: adamintsev Date: 15.10.13 Time: 16:55
 */
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
