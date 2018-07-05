package com.electriccloud.plugin.spec

import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider
import software.amazon.awssdk.regions.Region
import software.amazon.awssdk.services.s3.S3Client
import software.amazon.awssdk.services.s3.model.*
import spock.lang.Specification

class S3Helper {
    @Lazy(soft=true)
    String regionName = {return System.getenv('AWS_REGION_NAME')}()

    @Lazy
    S3Client s3Client = {
//        Builds credentials provider using environment variables
        DefaultCredentialsProvider provider = DefaultCredentialsProvider
            .builder()
            .build()

        S3Client client = S3Client
                .builder()
                .region(Region.of(regionName))
                .credentialsProvider(provider)
                .build()

        return client
    }()


    def deleteBucket(bucketName) {
        DeleteBucketRequest request = DeleteBucketRequest
            .builder()
            .bucket(bucketName)
            .build()
        DeleteBucketResponse response = s3Client.deleteBucket(request)
        println response
    }


    def cleanBucket(bucketName) {
        ListObjectsV2Request listRequest = ListObjectsV2Request.builder().bucket(bucketName).build()
        ListObjectsV2Response listResponse = s3Client.listObjectsV2(listRequest)
        listResponse.contents().each {
            println s3Client.deleteObject(DeleteObjectRequest.builder().bucket(bucketName).key(it.key()).build())
        }
    }

    def createBucket(bucketName) {
        CreateBucketRequest request = CreateBucketRequest.builder().bucket(bucketName).build()
        CreateBucketResponse response = s3Client.createBucket(request)
        println response
    }

}
