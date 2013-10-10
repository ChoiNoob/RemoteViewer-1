package com.damintsev.client.v3.uiitems;

import com.damintsev.client.devices.UIItem;
import com.damintsev.client.v3.items.task.Task;

/**
 * User: Damintsev Andrey
 * Date: 09.10.13
 * Time: 22:34
 */
public class UITask extends UIItem {

    private Task task;

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }

//    @Override
//    public Widget asWidget() {
//        return null;  //To change body of implemented methods use File | Settings | File Templates.
//    }
}
