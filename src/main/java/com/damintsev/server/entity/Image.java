package com.damintsev.server.entity;

/**
 * User: adamintsev
 * Date: 20.01.14
 * //todo написать комментарии
 */
public class Image {

    private Long id;
    private byte[] content;
    private String type;
    private int height;
    private int width;

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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getSize() {
        return content == null ? 0 : content.length;
    }
}
