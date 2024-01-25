package com.fitness.courses.configuration.s3.bucket;

import java.io.InputStream;
import java.net.URL;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.DeleteObjectsRequest;
import com.amazonaws.services.s3.model.DeleteObjectsRequest.KeyVersion;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.ListObjectsV2Request;
import com.amazonaws.services.s3.model.ListObjectsV2Result;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.amazonaws.services.s3.model.S3Object;

@Component
public class YandexBucketProvider
{
    private final AmazonS3 yandexS3;

    @Autowired
    public YandexBucketProvider(AmazonS3 yandexS3)
    {
        this.yandexS3 = yandexS3;
    }

    public boolean doesBucketExist(String bucketName)
    {
         return yandexS3.doesBucketExistV2(bucketName);
    }

    public void createBucket(String bucketName)
    {
        yandexS3.createBucket(bucketName);
    }

    public void deleteBucket(String bucketName)
    {
        yandexS3.deleteBucket(bucketName);
    }

    public PutObjectResult putObject(String bucketName, String key, InputStream fileInputStream,
            ObjectMetadata objectMetadata)
    {
        return yandexS3.putObject(bucketName, key, fileInputStream, objectMetadata);
    }

    public PutObjectResult updateObject(String bucketName, String key, InputStream fileInputStream,
            ObjectMetadata objectMetadata)
    {
        return this.putObject(bucketName, key, fileInputStream, objectMetadata);
    }

    public S3Object getObject(String bucketName, String objectKey)
    {
        GetObjectRequest objectRequest = new GetObjectRequest(bucketName, objectKey);
        return yandexS3.getObject(objectRequest);
    }

    public void deleteObject(String bucketName, String objectKey)
    {
        DeleteObjectRequest deleteObjectRequest = new DeleteObjectRequest(bucketName, objectKey);
        yandexS3.deleteObject(deleteObjectRequest);
    }

    public void deleteObjects(String bucketName, List<String> keys)
    {

        List<KeyVersion> keysToDelete = keys.stream().map(KeyVersion::new).toList();
        DeleteObjectsRequest deleteObjectRequest = new DeleteObjectsRequest(bucketName);
        deleteObjectRequest.setKeys(keysToDelete);

        yandexS3.deleteObjects(deleteObjectRequest);
    }

    public List<Bucket> listBuckets()
    {
        return yandexS3.listBuckets();
    }

    public ListObjectsV2Result listObjectsInBucket(String bucketName)
    {
        ListObjectsV2Request listObjectsV2Request = new ListObjectsV2Request()
                .withBucketName(bucketName);
        return yandexS3.listObjectsV2(listObjectsV2Request);
    }

/*    public void listAllObjectsInBucketPaginated(String bucketName, int pageSize)
    {
        ListObjectsV2Request listObjectsV2Request = new ListObjectsV2Request()
                .withBucketName(bucketName)
                .withMaxKeys(pageSize); // Set the maxKeys parameter to control the page size

        ListObjectsV2Result listObjectsV2Iterable = yandexS3.listObjectsV2(listObjectsV2Request);
        long totalObjects = 0;

        for (ListObjectsV2Response page : listObjectsV2Iterable)
        {
            long retrievedPageSize = page.contents().stream()
                    .peek(System.out::println)
                    .reduce(0, (subtotal, element) -> subtotal + 1, Integer::sum);
            totalObjects += retrievedPageSize;
            System.out.println("Page size: " + retrievedPageSize);
        }
        System.out.println("Total objects in the bucket: " + totalObjects);
    }*/

    public void putObjects(String bucketName, String key, List<InputStream> inputStreams, ObjectMetadata metadata)
    {
        inputStreams.forEach(inputStream ->
                yandexS3.putObject(
                        new PutObjectRequest(bucketName, key, inputStream, metadata)
                )
        );
    }

    public URL getUrl(String bucketName, String key)
    {
        return yandexS3.getUrl(bucketName, key);
    }
}
