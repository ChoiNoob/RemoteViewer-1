package com.damintsev.gwt.client.source.visitor;

import com.damintsev.gwt.client.source.uientity.Label;
import com.damintsev.gwt.client.source.uientity.Station;
import com.damintsev.gwt.client.source.uientity.Task;

/**
 * User: adamintsev Date: 15.10.13 Time: 16:55
 */
public interface Visitor<T> {

    T visit(Label label);

    T visit(Station station);

    T visit(Task task);
}
