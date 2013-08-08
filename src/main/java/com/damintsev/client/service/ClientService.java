package com.damintsev.client.service;

import com.damintsev.client.dao.Item;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import java.util.List;


/**
 * User: Damintsev Andrey
 * Date: 08.08.13
 * Time: 0:19
 */
@RemoteServiceRelativePath("service")
public interface ClientService extends RemoteService {
    String getMessage();
    Boolean saveItems(List<Item> items);
    List<Item> loadItems();
}

