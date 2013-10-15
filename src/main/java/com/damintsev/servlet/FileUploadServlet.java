package com.damintsev.servlet;

/**
 * User: Damintsev Andrey
 * Date: 15.10.13
 * Time: 23:12
 */
import java.io.File;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
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

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("xyuxyuxyuxyxuyxuyxu");
        if(ServletFileUpload.isMultipartContent(req)){
            try {
                FileItemFactory fileItemFactory = new DiskFileItemFactory();
                ServletFileUpload uploadHandlr = new ServletFileUpload(fileItemFactory);
                List<FileItem> uploadItems = uploadHandlr.parseRequest(req);

//                String filePath = "";
//                String fileSeparator = System.getProperty("file.separator");
//                String basePath = System.getProperty("user.home");
//                String filesDir = basePath + fileSeparator + "SSA_Files";

                for (FileItem fileItem : uploadItems) {
                    if(!fileItem.isFormField()){
                        System.out.println("file lalalalalalalal");
//                        if(ensureFilesDir(filesDir)){
//                            filePath = filesDir + fileSeparator + fileItem.getName();
//                            File file = new File(filePath);
//                            fileItem.write(file);
//
//                            logger.error(filePath);
//                            logger.error(fileItem.getName() + "." + fileItem.getContentType() + " [" + fileItem.getSize() + "]");
//                        }
                        //todo write to DB
                    }
                }
            } catch (FileUploadException ex) {
                logger.error(ex.getMessage());
            } catch (Exception ex){
                logger.error(ex.getMessage());
            }
        }
        super.doPost(req, resp);
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