package com.damintsev.client.v3.uiitems;

import com.google.gwt.user.client.ui.Image;

import java.util.HashMap;
import java.util.Map;

/**
 * User: Damintsev Andrey
 * Date: 13.10.13
 * Time: 12:25     //todo Переделать на ХТТП СЕРВЛЕТ!!!
 */
public class ImageHandler {

    private ImageHandler instance;

    public ImageHandler getInstance() {
        if(instance==null)instance = new ImageHandler();
        return instance;
    }

    Map<String, String> images;

    private ImageHandler() {
        images = new HashMap<String, String>();

    }

    public Image getImage(String type) {
//        if (images.get(type) != null)
            return new Image(images.get(type));

    }
}
