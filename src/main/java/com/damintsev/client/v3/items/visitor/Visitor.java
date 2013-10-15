package com.damintsev.client.v3.items.visitor;

import com.damintsev.client.v3.items.Label;
import com.damintsev.client.v3.items.Station;
import com.damintsev.client.v3.items.task.Task;

/**
 * User: adamintsev Date: 15.10.13 Time: 16:55
 */
public interface Visitor<T> {

    T visit(Label label);

    T visit(Station station);

    T visit(Task task);
}
