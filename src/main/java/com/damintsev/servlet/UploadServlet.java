package com.damintsev.servlet;

import com.damintsev.server.buisness.temporary.TemporaryFileManager;
import com.damintsev.server.entity.UploadedFile;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

/**
 * User: adamintsev
 * Date: 27.01.14
 */
@Controller
@RequestMapping(value = "upload")
public class UploadServlet {

    private final static Logger logger = Logger.getLogger(UploadServlet.class);

    @Autowired
    private TemporaryFileManager fileManager;

    @RequestMapping(value = "image", method = RequestMethod.POST)
    public @ResponseBody ResponseEntity processFile(@RequestParam MultipartFile file)  {
        logger.debug("Received request at url \"upload/image\"");
        try {
            return new ResponseEntity<>(fileManager.saveTempImage(file), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("fuck", HttpStatus.BAD_REQUEST);
        }
//         return null;//todo throw error!!!!
    }
}
