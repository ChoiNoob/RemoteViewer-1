package com.damintsev.server.buisness.temporary.impl;

import com.damintsev.server.buisness.temporary.TemporaryFileManager;
import com.damintsev.server.entity.UploadedFile;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * @author Damintsev Andrey
 *         25.02.14.
 */
//@Scope(value = "bundle")
@Component
public class TemporaryFileManagerImpl implements TemporaryFileManager {

    private final static Logger logger = Logger.getLogger(TemporaryFileManagerImpl.class);

    private final static long ONE_WEEK = 1000 * 60 * 60 * 24 * 7;
    private Map<String, File> fileMap = new HashMap<>();
    private Timer timer;

    @PostConstruct
    public void init() {
        logger.debug("Starting cleanup scheduler");
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                logger.info("Starting cleaning old resources");
                for(Map.Entry<String, File> entry : fileMap.entrySet()) {
                    File file = entry.getValue();
                    logger.debug(String.format("Find file with lastModified='%1$s'" ,new Date(file.lastModified())));
                    if((new Date().getTime() - ONE_WEEK) > file.lastModified()) {
                        logger.info(String.format("Removing old file with name='$1%s'", file.getName()));
                        fileMap.remove(entry.getKey());
                    }
                }
            }
        }, 1000, ONE_WEEK);
    }

    @PreDestroy
    public void destroy() {
        logger.info("Shutting down the timer");
        timer.cancel();
    }

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
    public File getTemporaryFile(String fileId) {
        return fileMap.get(fileId);
    }

    private File createTempFile(String name, String suffix) throws IOException {
            File file = File.createTempFile(name, suffix);
            file.deleteOnExit();
            return file;
    }
}
