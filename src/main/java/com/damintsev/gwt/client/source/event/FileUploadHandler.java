package com.damintsev.gwt.client.source.event;

import com.google.gwt.event.shared.EventHandler;

/**
 * User: adamintsev
 * Date: 30.01.14
 * //todo написать комментарии
 */
public interface FileUploadHandler extends EventHandler {

    public void onFileUpload(FileUploadEvent event);
}
