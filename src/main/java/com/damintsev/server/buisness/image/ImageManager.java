package com.damintsev.server.buisness.image;

import com.damintsev.server.dao.DataBase;
import com.damintsev.server.entity.Image;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * User: adamintsev
 * Date: 21.01.14
 * //todo comments
 */
@Component
public class ImageManager {

    private static Logger logger = Logger.getLogger(ImageManager.class);

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

    public void setTemporaryImage(Long imageId, byte[] content) {
        logger.info("Saving temportary image");
        BufferedImage bufferedImage = null;
        try (InputStream is = new ByteArrayInputStream(content) ) {
            bufferedImage = ImageIO.read(is);
            int width          = bufferedImage.getWidth();
            int height         = bufferedImage.getHeight();
            Image image = new Image();
            image.setWidth(width);
            image.setHeight(height);
            image.setContent(content);
            images.put(0L, image);
        }catch (IOException e) {
            e.printStackTrace();
        }
    }
}
