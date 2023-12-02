package com.fitness.courses.configuration;

import java.io.InputStream;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fitness.courses.http.objectStorage.model.entity.FileExtensionEnum;
import com.fitness.courses.http.objectStorage.model.entity.LocalStorageFileEntity;
import com.fitness.courses.http.objectStorage.service.LocalStorageFileService;

@Configuration
public class ExampleConfiguration
{
    @Bean
    public CommandLineRunner testS3BucketLoader(LocalStorageFileService localStorageFileService)
    {
        return args ->
        {
            final String bucketName = "bucketnamevshivtsev";
            InputStream inputStream = this.getClass().getClassLoader()
                    .getResourceAsStream("static/firstImage.png");
            LocalStorageFileEntity fileEntity = localStorageFileService
                    .addFile(bucketName, FileExtensionEnum.PNG, inputStream);
            inputStream.close();
            System.out.println(fileEntity.getUrl());
        };
    }
}
