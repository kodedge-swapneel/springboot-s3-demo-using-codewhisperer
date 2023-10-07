package com.example.springboots3demousingcodewhisperer.repository;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.S3Object;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

@Repository
public class S3Repository {
    @Autowired
    AmazonS3 amazonS3;

    //create method to create s3 bucket
    //if bucket already exist then it will return bucket object
    //if bucket does not exist then it will create bucket and return bucket object

    public Bucket createBucket(String bucketName) {
       if(!amazonS3.doesBucketExistV2(bucketName)){
          return amazonS3.createBucket(bucketName);
       } else {
           //get bucket object if already exist then get bucket
           return amazonS3.listBuckets().stream()
                   .filter(bucket -> bucket.getName().equals(bucketName))
                   .findFirst().get();
       }
    }

    //create method to upload file in s3 bucket
    public void uploadFile(String bucketName, MultipartFile file) throws IOException {
        File fileObj = new File(file.getOriginalFilename());
        FileOutputStream fos = new FileOutputStream(fileObj);
        fos.write(file.getBytes());
        fos.close();

        amazonS3.putObject(bucketName, fileObj.getName(), fileObj);
    }

    //create method to download file from s3 bucket
    public S3Object downloadFile(String bucketName, String fileName) {
        return amazonS3.getObject(bucketName, fileName);
    }

}
