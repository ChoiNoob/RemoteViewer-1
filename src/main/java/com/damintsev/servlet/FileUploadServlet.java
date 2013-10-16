package com.damintsev.servlet;

/**
 * User: Damintsev Andrey
 * Date: 15.10.13
 * Time: 23:12
 */
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

        try{
            FileItemIterator iter = upload.getItemIterator(request);

            while (iter.hasNext()) {
                FileItemStream item = iter.next();

                String name = item.getFieldName();
                InputStream stream = item.openStream();
                System.out.println("FFFFF=" + name);

                FileOutputStream storeFile = new FileOutputStream("C:\\temp\\test.png");
//                stream.
//                storeFile.
//                        // saves the file on disk
//                        item.write(storeFile);

                // Process the input stream
                ByteArrayOutputStream out = new ByteArrayOutputStream();
                int len;
                byte[] buffer = new byte[8192];
                while ((len = stream.read(buffer, 0, buffer.length)) != -1) {
                    storeFile.write(buffer, 0, len);
                }

                int maxFileSize = 10*(1024*1024); //10 megs max
                if (out.size() > maxFileSize) {
                    throw new RuntimeException("File is > than " + maxFileSize);
                }
            }
        }
        catch(Exception e){
            throw new RuntimeException(e);
        }

//        // checks if the request actually contains upload file
//        if (!ServletFileUpload.isMultipartContent(request)) {
//            // if not, we stop here
//            PrintWriter writer = response.getWriter();
//            writer.println("Error: Form must has enctype=multipart/form-data.");
//            writer.flush();
//            return;
//        }
//
//        // configures upload settings
//        DiskFileItemFactory factory = new DiskFileItemFactory();
//        // sets memory threshold - beyond which files are stored in disk
//        factory.setSizeThreshold(MEMORY_THRESHOLD);
//        // sets temporary location to store files
//        factory.setRepository(new File(System.getProperty("java.io.tmpdir")));
//
//        ServletFileUpload upload = new ServletFileUpload(factory);
//
//        // sets maximum size of upload file
//        upload.setFileSizeMax(MAX_FILE_SIZE);
//
//        // sets maximum size of request (include file + form data)
//        upload.setSizeMax(MAX_REQUEST_SIZE);
//
//        // constructs the directory path to store upload file
//        // this path is relative to application's directory
//        String uploadPath = getServletContext().getRealPath("")
//                + File.separator + UPLOAD_DIRECTORY;
//
//        // creates the directory if it does not exist
//        File uploadDir = new File(uploadPath);
//        if (!uploadDir.exists()) {
//            uploadDir.mkdir();
//        }
//
//        try {
//            // parses the request's content to extract file data
//            @SuppressWarnings("unchecked")
//            List<FileItem> formItems = upload.parseRequest(request);
//
//            if (formItems != null && formItems.size() > 0) {
//                // iterates over form's fields
//                for (FileItem item : formItems) {
//                    // processes only fields that are not form fields
//                    if (!item.isFormField()) {
//                        String fileName = new File(item.getName()).getName();
//                        String filePath = uploadPath + File.separator + fileName;
//                        File storeFile = new File(filePath);
//
//                        // saves the file on disk
//                        item.write(storeFile);
//                        request.setAttribute("message",
//                                "Upload has been done successfully!");
//                    }
//                }
//            }
//        } catch (Exception ex) {
//            request.setAttribute("message",
//                    "There was an error: " + ex.getMessage());
//        }
//        // redirects client to message page
//        getServletContext().getRequestDispatcher("/message.jsp").forward(
//                request, response);
    }

    private boolean ensureFilesDir(String path){
        File dir = new File(path);
        boolean status = dir.exists();
        if(!status){
            status = dir.mkdir();
        }
        return status;
    }

}