package com.damintsev.client.service;

import com.google.gwt.core.client.GWT;

/**
 * Created by adamintsev
 * Date: 08.08.13 15:30
 */
public class Service {
    public static MyClientServiceAsync instance = GWT.create(MyClientService.class);
}
