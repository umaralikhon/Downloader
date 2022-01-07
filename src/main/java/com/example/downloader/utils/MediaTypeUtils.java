package com.example.downloader.utils;

import org.springframework.http.MediaType;

import javax.servlet.ServletContext;

public class MediaTypeUtils {

    public static MediaType getMediaTypeForFileName(ServletContext servletContext, String fileName){
        String fileType = servletContext.getMimeType(fileName);
        try{
            MediaType mediaType = MediaType.parseMediaType(fileType);
            return mediaType;
        }catch (Exception ex){
            return MediaType.APPLICATION_OCTET_STREAM;
        }
    }
}
