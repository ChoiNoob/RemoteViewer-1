package com.damintsev.server.dao;

import com.damintsev.gwt.client.source.uientity.Image;

/**
 * User: adamintsev
 * Date: 21.01.14
 * //todo написать комментарии
 */
public interface ImageDao {

    void saveImage(Image image);

    Image getImage(Long id);
}
