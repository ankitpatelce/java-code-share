package com.lcwd.todo.controllers;

import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Arrays;
import java.util.logging.FileHandler;

@RestController
@RequestMapping("/file")
public class FileController {

    Logger logger= LoggerFactory.getLogger(FileHandler.class);

    @RequestMapping("/singleFileUpload")
    public String uploadSingelFile(@RequestParam("image")MultipartFile file){
        logger.info("Name : {} ",file.getName());
        logger.info("Orignal File Name : {} ",file.getOriginalFilename());
        logger.info("Size : {} ",file.getSize());
        logger.info("ContentType : {} ",file.getContentType());
        //file.getInputStream();

//        InputStream inputStream = file.getInputStream();
//        FileOutputStream fileOutputStream =new FileOutputStream("data.png");
//        byte data[]=new byte[inputStream.available()];
//        fileOutputStream.write(data);

        return  "test file" ;

    }

    @PostMapping("/multipleFileUpload")
    public String uploadMultipleFile(@RequestParam("files") MultipartFile[] files){

        Arrays.stream(files).forEach(file ->{
            logger.info("file name {}",file.getOriginalFilename());
            logger.info("file Name {}",file.getContentType());
            System.out.println("++++++++++++++++++++++++++++++++++++");
            //Call Service to upload files : and pass file object.
        });
        return "All files are uploaded" ;
    }

    // Serve image files in response
    @GetMapping("/serve-image")
    public void serveImageHandler(HttpServletResponse response){
        try {
            InputStream fileInputStream = new FileInputStream("image/Logo.png");
            response.setContentType(MediaType.IMAGE_JPEG_VALUE);
            StreamUtils.copy(fileInputStream,response.getOutputStream());

        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
