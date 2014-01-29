package com.damintsev.common.uientity;

import com.google.gwt.user.client.rpc.IsSerializable;

import java.io.Serializable;

/**
 * User: Damintsev Andrey
 * Date: 07.10.13
 * Time: 23:16
 */
public class TaskState implements IsSerializable, Serializable {

    private String id;
    private ExecuteState state;
    private Response response;
    private String message;

    public TaskState() {
       state = ExecuteState.INIT;
    }

    public TaskState(String id) {
        this(id, ExecuteState.INIT);
    }

    public TaskState(String id, ExecuteState state) {
        this(id, state, null);
    }

    public TaskState(String id, ExecuteState state, String message) {
        this.state = state;
        this.message = message;
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Response getResponse() {
        return response;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ExecuteState getState() {
        return state;
    }

    public void setState(ExecuteState state) {
        this.state = state;
    }
}
