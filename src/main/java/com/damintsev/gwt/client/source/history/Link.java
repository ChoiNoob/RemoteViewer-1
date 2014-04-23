package com.damintsev.gwt.client.source.history;

/**
 * @author Damintsev Andrey
 *         22.04.2014.
 */
public enum Link {
    LOGIN("login"), EDIT("edit"), MONITORING("monitoring");

    private String url;

    Link(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return this.url;
    }
}
