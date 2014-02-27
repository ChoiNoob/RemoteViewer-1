package com.damintsev.server.buisness.image;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;

/**
 * User: Damintsev Andrey
 * Date: 16.10.12
 * Time: 23:24
 * /
 */
//@Scope(value = "")
@Component
public class ImageUtils {

    private static final Logger log = Logger.getLogger(ImageUtils.class.getName());

    public BufferedImage resizeImage(byte[] fullImageContent, int width, int height) {
        BufferedImage bufferedImage = createBufferedImage(fullImageContent);
        return resizeImage(bufferedImage, width, height);
    }

    public BufferedImage resizeImage(BufferedImage fullImageContent, int width, int height) {
        Image image;
        try {
            image = fullImageContent;
        } catch (Exception ex) { //todo remove
            log.error("Cannot retrieve []byte stream from received image", ex);
            ex.printStackTrace();
            return null;
        }
        int thumbWidth = width;
        int thumbHeight = height;
        double thumbRatio = (double) thumbWidth / (double) thumbHeight;
        int imageWidth = image.getWidth(null);
        int imageHeight = image.getHeight(null);
        double imageRatio = (double) imageWidth / (double) imageHeight;
        if (thumbRatio < imageRatio) {
            thumbHeight = (int) (thumbWidth / imageRatio);
        } else {
            thumbWidth = (int) (thumbHeight * imageRatio);
        }
        if (imageWidth < thumbWidth && imageHeight < thumbHeight) {
            thumbWidth = imageWidth;
            thumbHeight = imageHeight;
        } else if (imageWidth < thumbWidth) {
            thumbWidth = imageWidth;
        } else if (imageHeight < thumbHeight) {
            thumbHeight = imageHeight;
        }
        BufferedImage thumbImage = new BufferedImage(thumbWidth, thumbHeight, BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics2D = thumbImage.createGraphics();
        graphics2D.setBackground(Color.WHITE);
        graphics2D.setPaint(Color.WHITE);
        graphics2D.fillRect(0, 0, thumbWidth, thumbHeight);
        graphics2D.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        graphics2D.drawImage(image, 0, 0, thumbWidth, thumbHeight, null);
        graphics2D.dispose();
            return thumbImage;
    }

    public BufferedImage createBufferedImage(byte[] content) {
        BufferedImage bufferedImage;
        try (InputStream is = new ByteArrayInputStream(content)) {
            bufferedImage = ImageIO.read(is);
            return bufferedImage;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public com.damintsev.common.uientity.Image createImage(byte[] content, int width, int height) {
        com.damintsev.common.uientity.Image image = new com.damintsev.common.uientity.Image();
        BufferedImage bufferedImage = resizeImage(content, width, height);
        image.setWidth(bufferedImage.getWidth());
        image.setHeight(bufferedImage.getHeight());
        try(ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            ImageIO.write(bufferedImage, "png", out);//todo parametrize
            image.setContent(out.toByteArray());
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return image;
    }

    public com.damintsev.common.uientity.Image createImage(File file, int width, int height) {
        byte []content = null;
        try {
            content = FileUtils.readFileToByteArray(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return createImage(content, width, height);
    }
}

