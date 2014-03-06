package com.damintsev.servlet;

import com.damintsev.common.uientity.Image;
import com.damintsev.server.buisness.image.ImageManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * User: adamintsev
 * Date: 16.10.13
 * Time: 17:46
 */
@Controller
@RequestMapping(value = "image")
public class ImageServlet {

    private static final Logger logger = LoggerFactory.getLogger(ImageServlet.class);

    @Autowired
    private ImageManager imageManager;

    @RequestMapping(value = "", method = RequestMethod.GET)
    @ResponseBody
    public HttpEntity<byte[]> doGet(@RequestParam Long imageId) {
        logger.debug("Received request at tmp link '/image with imageId={}", imageId);
        Image image = imageManager.getImage(imageId);
        return makeHttp(image.getContent());
    }

    @RequestMapping(value = "temporary", method = RequestMethod.GET)
    @ResponseBody
    public HttpEntity<byte[]> getTmp(@RequestParam String imageId) {
        logger.debug("Received request at tmp link '/image/temporary' with params imageId={}", imageId);
        Image image = imageManager.getTemporaryImage(imageId);
        return makeHttp(image.getContent());
    }

    private HttpEntity<byte[]> makeHttp(byte []content) {
        HttpHeaders header = new HttpHeaders();
        header.setContentType(MediaType.IMAGE_JPEG);
        return new HttpEntity<>(content, header);
    }
}
