package com.damintsev.client.service;

import com.google.gwt.core.client.GWT;

/**
 * Created by adamintsev
 * Date: 10.10.13 18:54
 */
public class Service2 {
    public static DatabaseServiceAsync database = GWT.create(DatabaseService.class);
}
