package com.damintsev.servlet;

import com.damintsev.server.buisness.image.ImageManager;
import com.damintsev.server.entity.Image;
import com.damintsev.server.entity.UploadedFile;
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
@RequestMapping(value = "/upload")
public class UploadServlet {

    private final static Logger logger = Logger.getLogger(UploadServlet.class);

    @Autowired
    private ImageManager imageManager;

    @RequestMapping(value = "image", method = RequestMethod.POST)
    public @ResponseBody UploadedFile processFile(@RequestParam MultipartFile file)  {
        logger.debug("Received request at url \"upload/image\"");
        UploadedFile tempImage = null;
        try {
            Image image = imageManager.setTemporaryImage(file.getBytes());
            tempImage = new UploadedFile();
            tempImage.setSize(image.getSize());
            tempImage.setUrl("image/session?imageId=" + image.getId());
            return tempImage;
        } catch (Exception e) {
            e.printStackTrace();
        }
//        return new HttpEntity<>("<script>window.parent.document.getElementById('tmpImage').src = 'image/session?imageId=" + imageId + "';" +
//                "</script>") ;
         return tempImage;//todo throw error!!!!
    }
}
