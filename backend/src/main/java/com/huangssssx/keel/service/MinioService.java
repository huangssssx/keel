package com.huangssssx.keel.service;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import io.minio.*;
import io.minio.http.Method;
import io.minio.messages.Item;

@Service
public class MinioService {
    @Autowired
    private MinioClient minioClient;

    @Value("${minio.bucket.name}")
    private String bucketName;

    public String uploadFile(String fileName, InputStream inputStream) {
        try {
            boolean found = minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());
            if (!found) {
                minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
            }

            minioClient.putObject(
                PutObjectArgs.builder()
                    .bucket(bucketName)
                    .object(fileName)
                    .stream(inputStream, -1, 10485760)
                    .contentType("application/octet-stream")
                    .build()
            );
            
            // 返回文件的URL
            return minioClient.getPresignedObjectUrl(
                GetPresignedObjectUrlArgs.builder()
                    .bucket(bucketName)
                    .object(fileName)
                    .method(Method.GET)
                    .build()
            );
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Minio 操作失败：" + e.getMessage());
        }
    }
    public List<String> listFiles() {
        try {
            List<String> fileNames = new ArrayList<String>();
            Iterable<Result<Item>> results = minioClient.listObjects(ListObjectsArgs.builder().bucket(bucketName).build());
            for (Result<Item> result : results) {
                fileNames.add(result.get().objectName());
            }
            return fileNames;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Minio 列举文件失败：" + e.getMessage());
        }
    }
}
