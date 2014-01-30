package com.damintsev.server.buisness.image;

import com.damintsev.server.dao.DataBase;
import com.damintsev.server.entity.Image;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * User: adamintsev
 * Date: 21.01.14
 * //todo comments
 */
@Component
public class ImageManager {

    private static Logger logger = Logger.getLogger(ImageManager.class);
    private static final int MAX_ALLOWED_SIZE = 500; //px

    @Autowired
    private DataBase dataBase;

    @Autowired
    private ServletContext context;

    @Autowired
    private ImageUtils imageUtils;

    private Map<Long, Image> images;

    @PostConstruct
    public void init() {
        images = new HashMap<>();
    }

    public Image getImage(Long imageId) {
        if(images.containsKey(imageId))
            return images.get(imageId);

        Image image = dataBase.getImage(imageId);
        if(image != null) images.put(image.getId(), image);
        return image;
    }

    public Image setTemporaryImage(byte[] content) {
        logger.info("Saving temporary image");
            Image image = imageUtils.createImage(content, MAX_ALLOWED_SIZE, MAX_ALLOWED_SIZE);
            image.setId((long)new Random().nextInt());
            context.setAttribute(Long.toString(image.getId()), image);
            return image;
    }

    public Image getTemporaryImage(Long imageId) {
        return (Image) context.getAttribute(Long.toString(imageId));
    }
}
