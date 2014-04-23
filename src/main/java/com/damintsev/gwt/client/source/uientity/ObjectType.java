package com.damintsev.gwt.client.source.uientity;

/**
 * @author Damintsev Andrey
 *         27.02.14.
 */
public enum ObjectType {
    LABEL("LABEL"), STATION("STATION"), TASK("TASK");

    private ObjectType(String label) {
        this.label = label;
    }

    private String label;
}
