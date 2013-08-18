package com.damintsev.client.devices;

import java.io.Serializable;

/**
 * User: Damintsev Andrey
 * Date: 12.08.13
 * Time: 23:56
 */
public class TestResponse implements Serializable {

    private boolean result;
    private String resultText;

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    public String getResultText() {
        return resultText;
    }

    public void setResultText(String resultText) {
        this.resultText = resultText;
    }
}
