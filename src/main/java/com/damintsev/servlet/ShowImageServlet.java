//package com.damintsev.servlet;
//
//import com.damintsev.server.db.DB;
//import com.damintsev.server.db.ImageHandler;
//import com.damintsev.server.entity.Image;
//
//import javax.imageio.ImageIO;
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServlet;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.awt.image.BufferedImage;
//import java.io.BufferedOutputStream;
//import java.io.ByteArrayOutputStream;
//import java.io.IOException;
//import java.util.HashMap;
//import java.util.Map;
//
///**
// * User: adamintsev Date: 16.10.13 Time: 17:46
// * * //todo переписать
// */
//public class ShowImageServlet extends HttpServlet {
//
//    private final static String REQUEST_PARAM_IMAGE_ID = "imageId";
//    private Map<Long, Image> images;
//
//    @Override
//    public void init() throws ServletException {
//        super.init();
//        images = new HashMap<Long, Image>();
//    }
//
//    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        System.err.println("received request=" + request.getParameter("type"));
////        if(ImageHandler.getInstance().getImage() == null) {
////            //todo как-то красиво обыграть чтоли!
////            response.setContentType("text/html");
////            response.getOutputStream().println("<html><head><title>Person Photo</title></head>");
////            response.getOutputStream().println("<body><h1>No photo found for </h1></body></html>");
////            return;
////        }
//        byte []image1 = new byte[0];
//        Image image = null;
//
//        if("tmp".equals(request.getParameter("type"))){
//            System.out.println("writing tmp");
//            BufferedImage tmpImage = ImageHandler.getInstance().getImage();
//            ByteArrayOutputStream os = new ByteArrayOutputStream();
//            ImageIO.write(tmpImage, "png", os);
//            image1 = os.toByteArray();
//        } else {
////           System.out.println("writing from DB=" + request.getParameter("type"));
////          image =  DB.getInstance().loadImages(request.getParameter("type"));
//            Long imageId = Long.parseLong(request.getParameter(REQUEST_PARAM_IMAGE_ID));
//            if(images.containsKey(imageId)) {
//              image = images.get(imageId);
//            } else {
////                image =  DB.getInstance().loadImages(imageId);
////                images.put(image.getId(), image);
//            }
//        }
//        response.reset();
////        response.setContentType(image.getType());
//        response.setContentLength(image.getContent().length);
//        BufferedOutputStream out = new BufferedOutputStream(response.getOutputStream());
//        out.write(image.getContent());
//        out.flush();
//    }
//}
