package com.damintsev.servlet;

import com.damintsev.common.uientity.Image;
import com.damintsev.server.buisness.image.ImageManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
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
    public ResponseEntity<byte[]> doGet(@RequestParam Long imageId) {
        logger.debug("Received request at link '/image with imageId={}", imageId);
        try{
        Image image = imageManager.getImage(imageId);
            return new ResponseEntity<>(image.getContent(), HttpStatus.OK);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = "temporary", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<byte[]> getTmp(@RequestParam String imageId) {
        logger.debug("Received request at link '/image/temporary' with params imageId={}", imageId);
        Image image = imageManager.getTemporaryImage(imageId);
        return makeHttp(image.getContent());
    }

    private ResponseEntity<byte[]> makeHttp(byte []content) {
        return new ResponseEntity<>(content, HttpStatus.OK);
    }
}
