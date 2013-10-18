package com.damintsev.client.old.devices;

import com.damintsev.client.old.devices.enums.Status;

import java.io.Serializable;

/**
 * User: Damintsev Andrey
 * Date: 18.08.13
 * Time: 10:50
 */
public class Response implements Serializable {

    private Long id;
    private String resultText;
//    private String requestText;
    private boolean result;

    public Response() {

    }

    public Response(String resultText) {
        this.resultText = resultText;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

//    public String getRequestText() {
//        return requestText;
//    }
//
//    public void setRequestText(String requestText) {
//        this.requestText = requestText;
//    }

    public String getResultText() {
        return resultText;
    }

    public void setResultText(String resultText) {
        this.resultText = resultText;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    public boolean isResult() {
        return result;
    }
}
