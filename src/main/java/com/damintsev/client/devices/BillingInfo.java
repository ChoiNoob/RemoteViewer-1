package com.damintsev.client.devices;

import java.io.Serializable;

/**
 * Created by adamintsev
 * Date: 14.08.13 15:15
 */
public class BillingInfo implements Serializable {

    private Long id;
    private String streamName;
    private String value2;
    private String value;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStreamName() {
        return streamName;
    }

    public void setStreamName(String streamName) {
        this.streamName = streamName;
    }

    public String getValue2() {
        return value2;
    }

    public String getValue() {
        return value;
    }

    public void setValue2(String value2) {
        this.value2 = value2;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
