package com.damintsev.servlet;

import com.damintsev.server.buisness.image.ImageManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

/**
 * User: adamintsev
 * Date: 27.01.14
 * //todo написать комментарии
 */
@Controller
@RequestMapping
public class UploadServlet {

    Logger logger = Logger.getLogger(UploadServlet.class);

    @Autowired
    private ImageManager imageManager;

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    @ResponseBody
    public HttpEntity<String> processFile(@RequestParam Long imageId,  @RequestParam MultipartFile file)  {
        logger.debug("imageId=" + imageId);
        try {
            imageManager.setTemporaryImage(imageId, file.getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new HttpEntity<>("good") ;
    }
}
