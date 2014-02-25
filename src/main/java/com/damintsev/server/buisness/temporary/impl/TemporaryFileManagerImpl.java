package com.damintsev.server.buisness.temporary.impl;

import com.damintsev.server.buisness.temporary.TemporaryFileManager;
import com.damintsev.server.entity.UploadedFile;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * @author Damintsev Andrey
 *         25.02.14.
 */
//@Scope(value = "bundle")
@Component
public class TemporaryFileManagerImpl implements TemporaryFileManager {

    private final static Logger logger = Logger.getLogger(TemporaryFileManagerImpl.class);

    private Map<String, File> fileMap = new HashMap<>();

    @Override
    public UploadedFile saveTempImage(MultipartFile multipartFile) {
        String fileId = Long.toString(Math.abs(new Random().nextLong()));
        try {
            File file = createTempFile(fileId, "img");
            multipartFile.transferTo(file);
            UploadedFile uploadedFile = new UploadedFile();
            uploadedFile.setId(fileId);
            uploadedFile.setName(multipartFile.getName());
            fileMap.put(fileId, file);
            return uploadedFile;
        } catch (IOException e) {
            logger.debug(e.getMessage(), e);
            //todo new my exception!
            throw new RuntimeException(e);
        }
    }

    @Override
    public File getUploadedFile(String fileId) {
        return fileMap.get(fileId);
    }

    private File createTempFile(String name, String suffix) throws IOException {
            File file = File.createTempFile(name, suffix);
            file.deleteOnExit();
            return file;
    }
}
