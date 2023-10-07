package com.example.springboots3demousingcodewhisperer.config;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AWSConfig {
    //create method to get AmazonS3 client object using region and it should return AmazonS3 object
    @Bean
    public AmazonS3 amazonS3(){
        return AmazonS3ClientBuilder
                .standard()
                .withRegion("ap-south-1")
                .build();
    }
}
