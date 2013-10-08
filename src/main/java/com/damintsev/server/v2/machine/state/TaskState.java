package com.damintsev.server.v2.machine.state;

import com.damintsev.client.devices.Response;

import java.io.Serializable;

/**
 * User: Damintsev Andrey
 * Date: 07.10.13
 * Time: 23:16
 */
public class TaskState implements Serializable{

    private Long id;
    private ExecuteState state;
    private Response response;
    private String message;

    public TaskState(ExecuteState state, String message) {
         this.state = state;
        this.message = message;
    }
    public TaskState(ExecuteState state) {
         this.state = state;
    }
        public TaskState() {
        state = ExecuteState.INIT;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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
