package com.damintsev.client.service;

import com.damintsev.client.old.devices.Item;
import com.damintsev.common.uientity.*;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.sencha.gxt.data.shared.loader.PagingLoadResult;

import java.util.List;

/**
 * User: Damintsev Andrey
 * Date: 06.10.13
 * Time: 11:23
 */
@RemoteServiceRelativePath("DatabaseServerService")
public interface DatabaseService extends RemoteService {

    Item saveItem(Item item);

    Task loadTask(Long id);

    void deleteTask(Task task);

    Station loadStation(Long id);

    void deleteStation(Station station);

    PagingLoadResult<Station> getStationList();

    void saveItemPosition(List<Item> items);

    List<Item> loadUIItems();

    //todo
    List<TaskState> loadTaskStates();


    void deleteLabel(Label label);

    Label loadLabel(Long id);

    void saveImage(String type);

    Image loadTemporaryImage(String imageId);

    Long saveTemporaryImage(String temporaryImageId, Long targetImageId, Image image);
}
