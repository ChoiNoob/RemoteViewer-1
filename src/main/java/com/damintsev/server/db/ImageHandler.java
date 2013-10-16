package com.damintsev.server.db;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;

/**
 * User: adamintsev Date: 16.10.13 Time: 16:27
 */
public class ImageHandler {

    private static ImageHandler instance;
    private BufferedImage image;

    public static ImageHandler getInstance() {
        if(instance == null) instance = new ImageHandler();
        return instance;
    }

    private ImageHandler() {

    }

    public void setImageData(BufferedImage image){
        this.image = image;
    }

    public BufferedImage getImage() {
        return image;
    }
}
