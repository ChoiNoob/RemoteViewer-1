package com.damintsev.common.event;

import com.google.gwt.event.shared.GwtEvent;

/**
 * User: adamintsev
 * Date: 30.01.14
 * //todo написать комментарии
 */
public class FileUploadEvent extends GwtEvent<FileUploadHandler> {

    public static GwtEvent.Type<FileUploadHandler> TYPE = new GwtEvent.Type<FileUploadHandler>();

    private Integer fileId;
    private Integer width;
    private Integer height;

    public FileUploadEvent(Integer fileId, Integer width, Integer height) {
        this.fileId = fileId;
        this.width = width;
        this.height = height;
    }

    @Override
    public Type<FileUploadHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(FileUploadHandler handler) {
        handler.onFileUpload(this);
    }

    public Integer getFileId() {
        return fileId;
    }

    public Integer getWidth() {
        return width;
    }

    public Integer getHeight() {
        return height;
    }
}
