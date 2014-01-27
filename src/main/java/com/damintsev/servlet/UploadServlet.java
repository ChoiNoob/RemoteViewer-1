package com.damintsev.servlet;

import com.damintsev.server.buisness.image.ImageManager;
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

    @Autowired
    private ImageManager imageManager;

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    @ResponseBody
    public HttpEntity<String> processFile(@RequestParam Long imageId,  @RequestParam MultipartFile file)  {
        System.out.println("imageId=" + imageId);
//        file.getBytes();
        System.out.println("size=" + file.toString());
        System.err.println("FUCK!");
        return new HttpEntity<>("FUCK") ;
    }
}
