package com.csye6220.Elearning.Util;




import software.amazon.awssdk.awscore.exception.AwsServiceException;
import software.amazon.awssdk.core.ResponseBytes;
import software.amazon.awssdk.core.exception.SdkClientException;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.core.waiters.WaiterOverrideConfiguration;
import software.amazon.awssdk.core.waiters.WaiterResponse;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;
import software.amazon.awssdk.services.s3.waiters.S3Waiter;


import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.Duration;


public class S3Util {
    private static final String BUCKET = "springboot-s3002";
    private static final Region REGION = Region.US_EAST_2; // Replace with your desired region


    public static void uploadFile(String fileName, InputStream inputStream)
            throws S3Exception, AwsServiceException, SdkClientException, IOException {
        try (S3Client client = S3Client.builder().region(REGION).build()) {


            PutObjectRequest request = PutObjectRequest.builder()
                    .bucket(BUCKET)
                    .key(fileName)


                    .build();


            client.putObject(request,
                    RequestBody.fromInputStream(inputStream, inputStream.available()));


            S3Waiter waiter = client.waiter();
            HeadObjectRequest waitRequest = HeadObjectRequest.builder()
                    .bucket(BUCKET)
                    .key(fileName)
                    .build();


            WaiterResponse<HeadObjectResponse> waitResponse = waitForObjectToExist(waiter, waitRequest);


            waitResponse.matched().response().ifPresent(x -> {
                // run custom code that should be executed after the upload file exists
                System.out.println("Object exists!");
            });
        }
    }


    private static WaiterResponse<HeadObjectResponse> waitForObjectToExist(S3Waiter waiter, HeadObjectRequest waitRequest) {
        try {
            return waiter.waitUntilObjectExists(
                    waitRequest,
                    WaiterOverrideConfiguration.builder()


                            .build()
            );
        } catch (Exception e) {
            System.out.println("Waiter failed: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }


    public static void downloadFile(String fileName, String destinationPath) throws S3Exception, AwsServiceException, SdkClientException, IOException{


        try (S3Client client = S3Client.builder().region(REGION).build()) {


            GetObjectRequest request = GetObjectRequest.builder()
                    .bucket(BUCKET)
                    .key(fileName)
                    .build();


            ResponseBytes<GetObjectResponse> objectBytes = client.getObjectAsBytes(request);


            try (InputStream objectStream = objectBytes.asInputStream();
                 FileOutputStream fileOutputStream = new FileOutputStream(destinationPath)) {


                byte[] buffer = new byte[4096];
                int bytesRead;
                while ((bytesRead = objectStream.read(buffer)) != -1) {
                    fileOutputStream.write(buffer, 0, bytesRead);
                }
            }
        }


    }
}

