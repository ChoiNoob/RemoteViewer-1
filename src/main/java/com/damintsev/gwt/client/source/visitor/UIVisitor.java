package com.damintsev.gwt.client.source.visitor;

import com.damintsev.gwt.client.source.uiitems.*;
import com.damintsev.gwt.client.source.uientity.Label;
import com.damintsev.gwt.client.source.uientity.Station;
import com.damintsev.gwt.client.source.uientity.Task;

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
