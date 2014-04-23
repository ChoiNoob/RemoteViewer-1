package com.damintsev.gwt.client.source.event;

import com.google.gwt.event.shared.GwtEvent;

/**
 * User: adamintsev
 * Date: 30.01.14
 * //todo написать комментарии
 */
public class FileUploadEvent extends GwtEvent<FileUploadHandler> {

    public static GwtEvent.Type<FileUploadHandler> TYPE = new GwtEvent.Type<FileUploadHandler>();

    private final String fileId;

    public FileUploadEvent(String fileId) {
        this.fileId = fileId;
    }

    @Override
    public Type<FileUploadHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(FileUploadHandler handler) {
        handler.onFileUpload(this);
    }

    public String getFileId() {
        return fileId;
    }

}
