package com.fitness.courses.configuration.s3;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;

@Configuration
public class YandexS3Config
{
    @Value("${yandex.cloud.s3.accessKeyId}")
    private String accessKeyId;

    @Value("${yandex.cloud.s3.secretAccessKey}")
    private String secretAccessKey;

    @Value("${yandex.cloud.s3.serviceEndpoint}")
    private String serviceEndpoint;

    @Value("${yandex.cloud.s3.signingRegion}")
    private String signingRegion;

    // TODO в будущем скрыть в YandexBucketProvider
    @Value("${yandex.cloud.s3.mainBucketName}")
    private String mainBucketName;

    public String getMainBucketName()
    {
        return mainBucketName;
    }

    @Bean
    public AmazonS3 yandexS3Client()
    {
        final AWSCredentials credentials = new BasicAWSCredentials(accessKeyId, secretAccessKey);
        return AmazonS3ClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .withEndpointConfiguration(
                        new AmazonS3ClientBuilder.EndpointConfiguration(serviceEndpoint, signingRegion)
                )
                .build();
    }
}
