package com.fitness.courses.http.s3.service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.springframework.stereotype.Service;

import com.amazonaws.SdkClientException;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.amazonaws.services.s3.model.ObjectMetadata;

@Service
public class UploadServiceImpl implements UploadService
{
    @Override
    public List<String> uploadPhoto() {
        byte[] imageInByte;
        try(InputStream inputStream = this.getClass().getClassLoader()
                .getResourceAsStream("static/firstImage.png"))
        {
            imageInByte = inputStream.readAllBytes();
        }
        catch (IOException e)
        {
            e.printStackTrace();
            throw new RuntimeException();
        }
        List<byte[]> photos = new ArrayList<>(List.of(imageInByte));
        List<String> urls = new ArrayList<>();

        final String bucketName = "bucketnamevshivtsev";
        final String accessKeyId = "YCAJE53dI_zMKBXq-_daxe0wQ";
        final String secretAccessKey = "YCNn5ZyamBMVAKY_BGoUFF0zRjcSI9BvobpqWylW";
        final AmazonS3 s3Client;

        try {
            // Создание клиента AmazonS3 с подключением к Object Storage
            s3Client = AmazonS3ClientBuilder.standard()
                    .withEndpointConfiguration(
                            new com.amazonaws.client.builder.AwsClientBuilder.EndpointConfiguration(
                                    "https://storage.yandexcloud.net",
                                    "ru-central1"
                            )
                    )
                    .withCredentials(new AWSStaticCredentialsProvider(new BasicAWSCredentials(accessKeyId, secretAccessKey)))
                    .build();
        } catch (SdkClientException e) {
            throw new SdkClientException(e.getMessage());
        }

        try {
            // Создание пула потоков
            ExecutorService executorService = Executors.newFixedThreadPool(photos.size());
            List<Future<String>> futures = new ArrayList<>();

            for (byte[] photoBytes : photos) {
                Future<String> future = executorService.submit(() -> {
                    String fileName = generateUniqueName();
                    ObjectMetadata metadata = new ObjectMetadata();
                    metadata.setContentLength(photoBytes.length);

                    // Загрузка файла в Yandex Object Storage
                    ByteArrayInputStream inputStream = new ByteArrayInputStream(photoBytes);
                    s3Client.putObject(bucketName, fileName, inputStream, metadata);

                    // Получение ссылки на загруженный файл
                    String url = s3Client.getUrl(bucketName, fileName).toExternalForm();

                    System.out.println(url);
                    return url;
                });

                futures.add(future);
            }

            // Ожидание завершения задач
            for (Future<String> future : futures) {
                try {
                    String url = future.get();
                    urls.add(url);
                } catch (InterruptedException | ExecutionException e) {
                    throw new RuntimeException(e);
                }
            }

            executorService.shutdown();
        } catch (AmazonS3Exception e) {
            throw new AmazonS3Exception(e.getMessage());
        }
        return urls;
    }

    private String generateUniqueName() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString();
    }
}
