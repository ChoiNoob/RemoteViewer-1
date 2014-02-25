package com.damintsev.server.buisness.temporary;

import com.damintsev.server.entity.UploadedFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

/**
 * @author Damintsev Andrey
 *         25.02.14.
 */
public interface TemporaryFileManager {

    UploadedFile saveTempImage(MultipartFile multipartFile);

    File getUploadedFile(String fileId);
}
