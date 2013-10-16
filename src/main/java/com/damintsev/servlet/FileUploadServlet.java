package com.damintsev.servlet;

/**
 * User: Damintsev Andrey
 * Date: 15.10.13
 * Time: 23:12
 */
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.damintsev.server.db.ImageHandler;
import com.sun.corba.se.impl.orbutil.ORBUtility;
import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import org.apache.commons.fileupload.*;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author chalu
 */
public class FileUploadServlet extends HttpServlet {

    private final Logger logger = LoggerFactory.getLogger(FileUploadServlet.class);

    public FileUploadServlet(){
        super();
    }

    // location to store file uploaded
    private static final String UPLOAD_DIRECTORY = "upload";

    // upload settings
    private static final int MEMORY_THRESHOLD   = 1024 * 1024 * 3;  // 3MB
    private static final int MAX_FILE_SIZE      = 1024 * 1024 * 40; // 40MB
    private static final int MAX_REQUEST_SIZE   = 1024 * 1024 * 50; // 50MB

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("queried=" + request.getParameter("type"));
        ServletFileUpload upload = new ServletFileUpload();
        InputStream stream = null;
        try{
            FileItemIterator iter = upload.getItemIterator(request);

            while (iter.hasNext()) {
                FileItemStream item = iter.next();

                String name = item.getFieldName();

                stream = item.openStream();
                System.out.println("FFFFF=" + name);

                // Process the input stream
                BufferedImage image = ImageIO.read(stream);
                ImageHandler.getInstance().setImageData(resizeImage(image));
//              /  ByteArrayOutputStream out = new ByteArrayOutputStream();
//                int len;
//                byte[] buffer = new byte[8192];
//                while ((len = stream.read(buffer, 0, buffer.length)) != -1) {
//                    out.write(buffer, 0, len);
//                }
//
//                int maxFileSize = 10*(1024*1024); //10 megs max
//                if (out.size() > maxFileSize) {
//                    throw new RuntimeException("File is > than " + maxFileSize);
//                }
            }
        }
        catch(Exception e){
            throw new RuntimeException(e);
        }  finally {
            if (stream != null) {
                stream.close();
            }
        }
    }

    private static BufferedImage resizeImage(BufferedImage originalImage){
        BufferedImage resizedImage = new BufferedImage(150, 150, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = resizedImage.createGraphics();
        g.drawImage(originalImage, 0, 0, 150, 150, null);
        g.dispose();

        return resizedImage;
    }

}