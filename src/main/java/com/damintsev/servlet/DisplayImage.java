package com.damintsev.servlet;

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

        if(ImageHandler.getInstance().getImage() == null) {
            //todo как-то красиво обыграть чтоли!
            response.setContentType("text/html");
            response.getOutputStream().println("<html><head><title>Person Photo</title></head>");
            response.getOutputStream().println("<body><h1>No photo found for </h1></body></html>");
            return;
        }
        BufferedImage image = ImageHandler.getInstance().getImage();

        ByteArrayOutputStream os = new ByteArrayOutputStream();
        ImageIO.write(image, "png", os);
        InputStream imageOut = new ByteArrayInputStream(os.toByteArray());
        byte []bb = os.toByteArray();


//        ImageOutputStream imageOut = ImageIO.(image);
        response.reset();
        response.setContentType("image/jpg");
//        int length =(int)imageOut.s();
        response.setContentLength(bb.length);
        BufferedOutputStream out = new BufferedOutputStream(response.getOutputStream());

//        int bufferSize = 1024;
//        byte[] buffer = new byte[bufferSize];

//        while ((length = imageOut.read(buffer)) != -1) {
//            System.out.println("writing " + length + " bytes");
//            out.write(bb, 0, length);
//        }
        out.write(bb);
        out.flush();
    }
}
