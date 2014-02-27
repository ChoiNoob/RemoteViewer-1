package com.damintsev.common.uientity;

import com.damintsev.client.old.devices.Item;
import com.damintsev.common.visitor.Visitor;

import javax.persistence.*;

/**
 * User: Damintsev Andrey
 * Date: 14.10.13
 * Time: 23:18
 */
@Entity
@Table(name = "labels")
public class Label extends Item {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "hasImage")
    private Boolean hasImage;

    @Column(name = "imageId")
    private Long imageId;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public TaskType getType() {
        return TaskType.LABEL;

    }

    @Override
    public String getStringId() {
        return "l" + getId();
    }

    @Override
    public Station getStation() {
        return null;
    }

    @Override
    public String getParentId() {
        return null;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public <T> T accept(Visitor<T> visitor) {
        return visitor.visit(this);
    }

    public void setImageId(Long imageId) {
        this.imageId = imageId;
    }

    @Override
    public Long getImageId() {
        if(imageId == null || imageId == 0) imageId = DefaultImages.LABEL.getValue();
        return imageId;
    }

    public Boolean getHasImage() {
        return hasImage;
    }

    public void setHasImage(Boolean hasImage) {
        this.hasImage = hasImage;
    }
}
