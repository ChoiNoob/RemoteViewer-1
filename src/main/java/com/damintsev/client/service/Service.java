package com.damintsev.client.service;

import com.google.gwt.core.client.GWT;

/**
 * Created by adamintsev
 * Date: 08.08.13 15:30
 */
public class Service {
    public static ClientServiceAsync instance = GWT.create(ClientService.class);
    public static DatabaseServiceAsync database = GWT.create(DatabaseService.class);
}
