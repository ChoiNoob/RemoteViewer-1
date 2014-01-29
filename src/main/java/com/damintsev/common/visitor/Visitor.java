package com.damintsev.common.visitor;

import com.damintsev.common.uientity.Label;
import com.damintsev.common.uientity.Station;
import com.damintsev.common.uientity.Task;

/**
 * User: adamintsev Date: 15.10.13 Time: 16:55
 */
public interface Visitor<T> {

    T visit(Label label);

    T visit(Station station);

    T visit(Task task);
}
