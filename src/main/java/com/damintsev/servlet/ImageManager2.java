package com.damintsev.servlet;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.PixelGrabber;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * User: Damintsev Andrey
 * Date: 16.10.12
 * Time: 23:24
 * /
 //todo переписать
 */
public class ImageManager2 {
//    private static final Logger log = Logger.getLogger(ImageManager2.class.getName());
            /*
    public static byte[] resizeImage(byte[] fullImageContent, int width, int height) {
//        String mimeType = getMiMeType(fullImageContent);
//        checkImageType(mimeType);
//        if (mimeType.equals("image/gif")) {
//            return resizeGif(fullImageContent, width, height);
//        } else {
            return resizeImageJpeg(fullImageContent, width, height/*, mimeType);
//        }
//    }
*/

    public static BufferedImage resizeImageJpeg(BufferedImage fullImageContent, int width, int height/*, String mimeType*/) {
        Image image;
        String imageType;
//        try {
            image = fullImageContent;//ImageIO.read(new ByteArrayInputStream(fullImageContent));
//            imageType = getImageType(mimeType);
//        } catch (IOException ex) {
//            log.error("Cannot retrieve []byte stream from received image", ex);
//            ex.printStackTrace();
//            return null;
//        }
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
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try {
            ImageIO.write(thumbImage, "png", out);
            return thumbImage;
        } catch (IOException e) {
            return null;
        }
    }

//    private static byte[] resizeGif(byte[] fullImageContent, int newWidth, int newHeight) {
//        GifDecoder decoder = new GifDecoder();
//        decoder.read(new ByteArrayInputStream(fullImageContent));
//        AnimatedGifEncoder encoder = new AnimatedGifEncoder();
//        ByteArrayOutputStream out = new ByteArrayOutputStream();
//
//        encoder.setRepeat(0);
//
//        //set gif quality
//        encoder.setQuality(1500);
//
//        //set color for tranparence
//        //   encoder.setTransparent(Color.BLACK);
//        encoder.start(out);
//
//        //read frames with encoder and push scaled frames oin encoder
//        for (int i = 0; i < decoder.getFrameCount(); i++) {
//            BufferedImage bufferedImage = decoder.getFrame(i);
//
//            //scale image at max dimension value MAX_IMAGE_DIMENSION both on height
//            //and width, edit this part of cod as you need
//            int width = bufferedImage.getWidth();
//            int height = bufferedImage.getHeight();
//            float scaleFactor = 1;
//            int MAX_IMAGE_DIMENSION = (newHeight > newWidth) ? newHeight : newWidth;
//
//            if (width > MAX_IMAGE_DIMENSION || height > MAX_IMAGE_DIMENSION) {
//                if (width > height) {
//                    scaleFactor = (float) MAX_IMAGE_DIMENSION / width;
//                } else {
//                    scaleFactor = (float) MAX_IMAGE_DIMENSION / height;
//                }
//            }
//
//            //final values for height and width
//            width = (int) (width * scaleFactor);
//            height = (int) (height * scaleFactor);
//            if (scaleFactor != 1) {
//                Image image = bufferedImage.getScaledInstance(width, height,
//                        Image.SCALE_AREA_AVERAGING);
//                bufferedImage = toBufferedImage(image);
//            }
//            encoder.addFrame(bufferedImage);
//            //change delay frame for those that have 0
//            if (decoder.getDelay(i) == 0) {
//                encoder.setDelay(100);
//            } else {
//                encoder.setDelay(decoder.getDelay(i));
//            }
//        }
//        //stop and close
//        encoder.finish();
//        return out.toByteArray();
//    }
                          /*
    public static String getMiMeType(byte[] fullImageContent) {
        Tika detector = new Tika();
        String mimeType = detector.detect(fullImageContent);
        if (mimeType == null) {
            log.warn("Image mimeType cannot be recognize");
            return null;
        }
        log.info("Image mimeType is: " + mimeType);
        return mimeType;
    }

    private static String getImageType(String mimeType) {
        if (mimeType == null || mimeType.split("/").length < 2) {
            log.warn("Type format cannot be recognize");
            return null;
        } else {
            log.info("Image format is: " + mimeType);
            return mimeType.split("/")[1];
        }
    }              */

    private static boolean checkImageType(String mimeType) {
        for (String knownTypes : ImageIO.getReaderMIMETypes()) {
            if (mimeType.equals(knownTypes))
                return true;
        }
        throw new RuntimeException();
    }

    private static BufferedImage toBufferedImage(Image image) {
        if (image instanceof BufferedImage) {
            return (BufferedImage) image;
        }

        // This code ensures that all the pixels in the image are loaded
        image = new ImageIcon(image).getImage();

        // Determine if the image has transparent pixels; for this method's
        // implementation, see e661 Determining If an Image Has Transparent Pixels
        boolean hasAlpha = hasAlpha(image);

        // Create a buffered image with a format that's compatible with the screen
        BufferedImage bimage = null;
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        try {
            // Determine the type of transparency of the new buffered image
            int transparency = Transparency.OPAQUE;
            if (hasAlpha) {
                transparency = Transparency.TRANSLUCENT;
            }

            // Create the buffered image
            GraphicsDevice gs = ge.getDefaultScreenDevice();
            GraphicsConfiguration gc = gs.getDefaultConfiguration();
            bimage = gc.createCompatibleImage(
                    image.getWidth(null), image.getHeight(null), transparency);
        } catch (HeadlessException e) {
            // The system does not have a screen
        }

        if (bimage == null) {
            // Create a buffered image using the default color model
            int type = BufferedImage.TYPE_INT_RGB;
            if (hasAlpha) {
                type = BufferedImage.TYPE_INT_ARGB;
            }
            bimage = new BufferedImage(image.getWidth(null), image.getHeight(null), type);
        }

        // Copy image to buffered image
        Graphics2D g = bimage.createGraphics();
        g.setRenderingHints(new RenderingHints(RenderingHints.KEY_INTERPOLATION,
                RenderingHints.VALUE_INTERPOLATION_BILINEAR));

        // Paint the image onto the buffered image
        g.drawImage(image, 0, 0, null);
        g.dispose();

        return bimage;
    }

    private static boolean hasAlpha(Image image) {
        // If buffered image, the color model is readily available
        if (image instanceof BufferedImage) {
            BufferedImage bimage = (BufferedImage) image;
            return bimage.getColorModel().hasAlpha();
        }

        // Use a pixel grabber to retrieve the image's color model;
        // grabbing a single pixel is usually sufficient
        PixelGrabber pg = new PixelGrabber(image, 0, 0, 1, 1, false);
        try {
            pg.grabPixels();
        } catch (InterruptedException e) {
        }

        // Get the image's color model
        ColorModel cm = pg.getColorModel();
        return cm.hasAlpha();
    }
}

