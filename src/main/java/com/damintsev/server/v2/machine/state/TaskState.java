package com.damintsev.server.v2.machine.state;

import com.damintsev.client.devices.Response;

/**
 * User: Damintsev Andrey
 * Date: 07.10.13
 * Time: 23:16
 */
public class TaskState {

    private String id;
    private State state;
    private Response response;

    public TaskState() {
        state = State.INIT;
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

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }
}
