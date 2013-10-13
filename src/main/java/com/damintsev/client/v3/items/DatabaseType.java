package com.damintsev.client.v3.items;

/**
 * User: Damintsev Andrey
 * Date: 09.10.13
 * Time: 23:53
 */
public enum  DatabaseType {
    LABEL("Label"), STATION("Station"), TASK("Task");

    private DatabaseType(String label) {
        this.label = label;
    }

    private String label;




}
