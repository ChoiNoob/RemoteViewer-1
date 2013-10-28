package com.damintsev.common.beans;

import com.damintsev.client.old.devices.Response;

import java.io.Serializable;

/**
 * User: Damintsev Andrey
 * Date: 07.10.13
 * Time: 23:16
 */
public class TaskState implements Serializable {

    private String id;
    private ExecuteState state;
    private Response response;
    private String message;

    public TaskState() {
        state = ExecuteState.INIT;
    }

    public TaskState(ExecuteState state) {
        this.state = state;
    }

    public TaskState(ExecuteState state, String message) {
        this.state = state;
        this.message = message;
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

    public void setResponse(Response response) {
        this.response = response;
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
