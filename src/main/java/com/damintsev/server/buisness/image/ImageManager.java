package com.damintsev.server.buisness.image;

import com.damintsev.server.entity.UploadedFile;
import com.damintsev.server.exceptions.CustomException;
import com.damintsev.server.exceptions.NotFoundException;
import com.damintsev.common.uientity.Image;
import com.damintsev.server.buisness.temporary.TemporaryFileManager;
import com.damintsev.server.dao.ImageDao;
import net.sf.jmimemagic.Magic;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
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
    private static final int MAX_ALLOWED_SIZE = 500;//px

    @Autowired
    private ImageDao imageDao;

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

        Image image = imageDao.getImage(imageId);
        if(image == null)
            throw new NotFoundException("Image with id=" + imageId + " not found");

        images.put(image.getId(), image);
        return image;
    }

    public Image getTemporaryImage(String imageId) {
        File file = temporaryFileManager.getTemporaryFile(imageId);

        if(file == null)
            throw new NotFoundException("Image with id=" + imageId + " not found");

        Image image = imageUtils.createImage(file, MAX_ALLOWED_SIZE, MAX_ALLOWED_SIZE);
        logger.debug(
                String.format("Created temporary image with id='$1%s, width='$2%s', height='$3%s'",
                        image.getId(),
                        image.getHeight(),
                        image.getWidth()));
        return image;
    }

    public Long saveTemporaryImage(String temporaryImageId, Long targetImageId, Image image) {
        File file = temporaryFileManager.getTemporaryFile(temporaryImageId);
        Image residedImage = imageUtils.createImage(file, image.getWidth(), image.getHeight());
        residedImage.setId(targetImageId);
        images.put(targetImageId, residedImage);
        imageDao.saveImage(residedImage);
        return targetImageId;
    }

    public UploadedFile saveTempImage(MultipartFile file) {
            if(!imageUtils.isImage(file)) {
                throw new CustomException("Wrong MimeType", HttpStatus.UNSUPPORTED_MEDIA_TYPE);
            }
        return temporaryFileManager.saveTempImage(file);
    }
}
