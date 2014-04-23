package com.damintsev.gwt.client.source.uientity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Author damintsev
 * 4/16/2014
 */
@Entity
@Table(name = "uipositions")
public class UiPosition {

    @Id
    @Column(name = "ref_id")
    private Long referenceId;

    @Column(name = "ref_type")
    private String type;

    @Column
    private int x;

    @Column
    private int y;

    public Long getReferenceId() {
        return referenceId;
    }

    public void setReferenceId(Long referenceId) {
        this.referenceId = referenceId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}
