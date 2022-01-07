package com.example.downloader.controller;

import com.example.downloader.utils.MediaTypeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

@RestController
@RequestMapping("/api")
public class DownloadController {
    private final ServletContext servletContext;

    @Autowired
    public DownloadController(ServletContext servletContext){
        this.servletContext = servletContext;
    }

    private static final String PATH = "files/";

    @GetMapping("/download/{fileName}")
    public void download(@PathVariable String fileName, HttpServletResponse response) throws IOException {
        MediaType mediaType = MediaTypeUtils.getMediaTypeForFileName(servletContext, fileName);
        File file = new File(PATH + fileName);

        response.setContentType(mediaType.getType());
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + file.getName());
        response.setContentLength((int)file.length());

        BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));
        BufferedOutputStream bos = new BufferedOutputStream(response.getOutputStream());

        byte[] buffer = new byte[1024];
        int bytesRead = 0;

        while((bytesRead = bis.read(buffer))!=-1){
            bos.write(buffer, 0, bytesRead);
        }

        bos.flush();
        bis.close();

    }


}
