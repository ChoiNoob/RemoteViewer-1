package com.damintsev.gwt.client.source.uientity;

import com.google.gwt.user.client.rpc.IsSerializable;

import javax.persistence.*;

/**
 * User: adamintsev
 * Date: 20.01.14
 * //todo Перенести в энтити на  север, когда откажемся от ГВТ
 */
@Entity
@Table(name = "images")
public class Image implements IsSerializable {

    @Id
    private Long id;

    @Lob
    @Column(name = "DATA")
    private byte[] content;

//    @Column(name = "TYPE")
//    @Enumerated(EnumType.STRING)
//    private ObjectType type;

    @Column(name = "height")
    private Integer height;

    @Column(name = "width")
    private Integer width;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }

//    public ObjectType getType() {
//        return type;
//    }

//    public void setType(ObjectType type) {
//        this.type = type;
//    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public int getSize() {
        return content == null ? 0 : content.length;
    }
}
