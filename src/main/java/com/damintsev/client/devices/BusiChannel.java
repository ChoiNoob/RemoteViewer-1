package com.damintsev.client.devices;

/**
 * User: Damintsev Andrey
 * Date: 15.08.13
 * Time: 19:15
 */
public class BusiChannel {

    private Long id;
    private Long busu;
    private Long free;
    private String type;

    public Long getBusu() {
        return busu;
    }

    public void setBusu(Long busu) {
        this.busu = busu;
    }

    public Long getFree() {
        return free;
    }

    public void setFree(Long free) {
        this.free = free;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
