package com.damintsev.servlet;

import com.damintsev.server.buisness.image.ImageManager;
import com.damintsev.server.entity.Image;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletException;
import java.io.IOException;

/**
 * User: adamintsev
 * Date: 16.10.13
 * Time: 17:46
 */
@Component
@RequestMapping
public class ImageServlet {

    Logger logger = Logger.getLogger(UploadServlet.class);

    @Autowired
    private ImageManager imageManager;

    @RequestMapping(value = "/image", method = RequestMethod.GET)
    @ResponseBody
    public HttpEntity<byte[]> doGet(@RequestParam Long imageId) {
        logger.debug("Received request=" + imageId);
        Image image = imageManager.getImage(imageId);
        HttpHeaders header = new HttpHeaders();
        header.setContentType(MediaType.IMAGE_JPEG);
        return new HttpEntity<>(image.getContent(), header);
    }
}
