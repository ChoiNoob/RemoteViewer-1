package com.damintsev.servlet;

import com.damintsev.server.buisness.image.ImageManager;
import com.damintsev.server.entity.Image;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.IOException;

/**
 * User: adamintsev Date: 16.10.13 Time: 17:46
 */
public class ImageServlet extends HttpServlet {

    private final static String REQUEST_PARAM_IMAGE_ID = "imageId";

    @Autowired
    private ImageManager imageManager;

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("received request=" + request.getParameter("type"));        //todo configure logger
        Long imageId = Long.parseLong(request.getParameter(REQUEST_PARAM_IMAGE_ID));
        Image image = imageManager.getImage(imageId);
        response.reset();
        response.setContentType(image.getType());
        response.setContentLength(image.getContent().length);
        BufferedOutputStream out = new BufferedOutputStream(response.getOutputStream());
        out.write(image.getContent());
        out.flush();
    }
}
