package com.damintsev.client.v3.items.visitor;

import com.damintsev.client.v3.items.Station;

/**
 * User: adamintsev
 * Date: 15.10.13
 * Time: 16:49
 */
public interface CommonVisitor {

    /**
     *
     *
     * @param visitor
     * @return
     */
    <T> T accept(Visitor visitor);

}
