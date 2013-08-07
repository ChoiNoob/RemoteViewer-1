package com.damintsev.server.services;

import com.damintsev.client.uiframe.ClientService;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * User: Damintsev Andrey
 * Date: 08.08.13
 * Time: 0:22
 */
public class ServerService extends RemoteServiceServlet implements ClientService {

    public String getMessage() {
        System.out.println("calling service!");
        return "FUCK U!!!!!!";
    }
}
