package com.damintsev.servlet;

import com.damintsev.server.db.DB;
import com.damintsev.server.db.ImageHandler;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageOutputStream;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.*;

/**
 * User: adamintsev Date: 16.10.13 Time: 17:46
 */
public class DisplayImage  extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.err.println("FUCK="+request.getParameter("type"));
//        if(ImageHandler.getInstance().getImage() == null) {
//            //todo как-то красиво обыграть чтоли!
//            response.setContentType("text/html");
//            response.getOutputStream().println("<html><head><title>Person Photo</title></head>");
//            response.getOutputStream().println("<body><h1>No photo found for </h1></body></html>");
//            return;
//        }
        byte []image;
        if("tmp".equals(request.getParameter("type"))){
            System.out.println("writing tmp");
            BufferedImage tmpImage = ImageHandler.getInstance().getImage();
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            ImageIO.write(tmpImage, "png", os);
            image = os.toByteArray();
        } else {
            System.out.println("writing from DB=" + request.getParameter("type"));
          image =  DB.getInstance().loadImages(request.getParameter("type"));
        }
        response.reset();
        response.setContentType("image/jpg");
        response.setContentLength(image.length);
        BufferedOutputStream out = new BufferedOutputStream(response.getOutputStream());
        out.write(image);
        out.flush();
    }
}
