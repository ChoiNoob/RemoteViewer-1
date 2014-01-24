package com.damintsev.servlet;

import com.damintsev.server.buisness.image.ImageManager;
import com.damintsev.server.entity.Image;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
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
public class ImageServlet{

    @Autowired
    private ImageManager imageManager;

    @RequestMapping(value = "/image",method = RequestMethod.GET)
    @ResponseBody
    public HttpEntity<byte[]> doGet(@RequestParam Long imageId) throws ServletException, IOException {
        System.err.println("received request=" + imageId);        //todo configure logger
        try {
            Image image = imageManager.getImage(imageId);
            return new HttpEntity<>(image.getContent());
        }catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }
}
