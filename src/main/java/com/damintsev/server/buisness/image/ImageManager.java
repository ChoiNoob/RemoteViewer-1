package com.damintsev.server.buisness.image;

import com.damintsev.server.dao.DataBase;
import com.damintsev.server.entity.Image;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

/**
 * User: adamintsev
 * Date: 21.01.14
 * //todo comments
 */
@Component
public class ImageManager {

    @Autowired
    private DataBase dataBase;

    private Map<Long, Image> images;

    @PostConstruct
    public void init() {
        images = new HashMap<>();
    }

    public Image getImage(Long imageId) {
        if(images.containsKey(imageId))
            return images.get(imageId);

        Image image = dataBase.getImage(imageId);
        images.put(image.getId(), image);
        return image;
    }
}
