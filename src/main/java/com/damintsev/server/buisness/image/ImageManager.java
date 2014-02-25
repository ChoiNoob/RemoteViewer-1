package com.damintsev.server.buisness.image;

import com.damintsev.server.buisness.temporary.TemporaryFileManager;
import com.damintsev.server.dao.ImageDao;
import com.damintsev.server.entity.Image;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import java.io.File;
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
    private static final int MAX_ALLOWED_SIZE = 500;//px

    @Autowired
    private ImageDao dataBase;

    @Autowired
    private TemporaryFileManager temporaryFileManager;

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

    public Image getTemporaryImage(String imageId) {
        File file = temporaryFileManager.getUploadedFile(imageId);
        Image image = imageUtils.createImage(file, MAX_ALLOWED_SIZE, MAX_ALLOWED_SIZE);
        logger.debug(
                String.format("Created temporary image with id='$1%s, width='$2%s', height='$3%s'",
                        image.getId(),
                        image.getHeight(),
                        image.getWidth()));
        return image;
    }
}
