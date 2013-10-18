package com.damintsev.common.visitor;

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
    <T> T accept(Visitor<T> visitor);

}
