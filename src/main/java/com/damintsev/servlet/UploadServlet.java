package com.damintsev.servlet;

import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.net.ResponseCache;

/**
 * User: adamintsev
 * Date: 27.01.14
 * //todo написать комментарии
 */
@Controller
@RequestMapping
public class UploadServlet {

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    @ResponseBody
    public HttpEntity<String> processFile(@RequestParam(value = "file") MultipartFile file, @RequestParam Long imageId) {
        System.out.println("FUCK!");
        System.out.println("size=" + file.getSize());
        System.err.println("FUCK!");

        return new HttpEntity<>("FUCK") ;
    }

    static class MyFile {
        MultipartFile file;

        public MultipartFile getFile() {
            return file;
        }

        public void setFile(MultipartFile file) {
            this.file = file;
        }
    }
}
