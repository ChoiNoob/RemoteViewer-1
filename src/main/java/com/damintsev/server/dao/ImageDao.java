package com.damintsev.server.dao;

import com.damintsev.server.entity.Image;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

/**
 * User: adamintsev
 * Date: 21.01.14
 * //todo написать комментарии
 */
public interface ImageDao {

    void saveImage(Image image);

    Image getImage(Long id);
}
