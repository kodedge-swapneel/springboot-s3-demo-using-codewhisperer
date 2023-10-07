package com.example.springboots3demousingcodewhisperer.controller;

import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.S3Object;
import com.example.springboots3demousingcodewhisperer.repository.S3Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
public class S3Controller {

    @Autowired
    S3Repository s3Repository;

    //create method having get request to create bucket using  s3Repository
    //accept bucket name as Request Parameter and return Bucket object
    @GetMapping("/createBucket")
    public Bucket createBucket(@RequestParam("bucketName")  String bucketName){
        return s3Repository.createBucket(bucketName);
    }

    //create method having post request to upload file in s3 bucket
    //accept bucket name and file as Request Parameter
    @PostMapping("/uploadFile")
    public void uploadFile(@RequestParam("bucketName") String bucketName,
                           @RequestParam("file") MultipartFile file) throws IOException {
        s3Repository.uploadFile(bucketName, file);  //call uploadFile method from s3Repository class
    }


    @GetMapping("/downloadFile")
    public HttpEntity<byte[]> downloadFile(@RequestParam("bucketName") String bucketName,
                                           @RequestParam("fileName") String fileName) throws IOException {
        S3Object file = s3Repository.downloadFile(bucketName, fileName);
        String contentType = file.getObjectMetadata().getContentType();
        var bytes = file.getObjectContent().readAllBytes();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.valueOf(contentType));
        headers.setContentLength(bytes.length);

        headers.add("Content-Disposition", "attachment; filename=" + fileName);
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");

        return new HttpEntity<>(bytes, headers);
    }






}
