package com.damintsev.client.service;

import com.damintsev.client.old.devices.Item;
import com.damintsev.common.uientity.Label;
import com.damintsev.common.uientity.Station;
import com.damintsev.common.uientity.Task;
import com.damintsev.common.uientity.TaskState;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.sencha.gxt.data.shared.loader.PagingLoadResult;

import java.util.List;

public interface DatabaseServiceAsync {

    void loadTask(Long id, AsyncCallback<Task> async);

    void loadUIItems(AsyncCallback<List<Item>> async);

    void loadStation(Long id, AsyncCallback<Station> async);

    //todo
    void loadTaskStates(AsyncCallback<List<TaskState>> async);

    void deleteStation(Station station, AsyncCallback<Void> async);

    void saveItemPosition(List<Item> items, AsyncCallback<Void> async);

    void deleteTask(Task task, AsyncCallback<Void> asyncCallback);

    void getStationList(AsyncCallback<PagingLoadResult<Station>> async);

    void deleteLabel(Label label, AsyncCallback<Void> asyncCallback);

    void loadLabel(Long id, AsyncCallback<Label> async);

    void saveItem(Item item, AsyncCallback<Item> async);

    void saveImage(String type, AsyncCallback<Void> async);
}
