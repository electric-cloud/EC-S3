package com.cloudbees.pdk.hen.tests

import com.amazonaws.AmazonServiceException
import com.amazonaws.auth.BasicAWSCredentials
import com.amazonaws.services.s3.AmazonS3
import com.amazonaws.services.s3.model.ListVersionsRequest
import com.amazonaws.services.s3.model.ObjectListing
import com.amazonaws.services.s3.model.S3ObjectSummary
import com.amazonaws.services.s3.model.S3VersionSummary
import com.amazonaws.services.s3.model.VersionListing
import com.amazonaws.services.s3.transfer.TransferManager
import com.cloudbees.pdk.hen.S3
import com.electriccloud.spec.PluginSpockTestSupport
import spock.lang.Shared
import static com.cloudbees.pdk.hen.Utils.env

class PluginTestHelper extends PluginSpockTestSupport {
    @Shared
    def plugin = S3.create()
    @Shared
    def configName = plugin.getConfigName()
    @Shared
    def pluginWithoutConfig = S3.createWithoutConfig()
    @Shared
    def awsAccessKeyId = env("AWS_ACCESS_KEY_ID")
    @Shared
    def awsSecretAccessKey = env("AWS_SECRET_ACCESS_KEY")
    @Shared
    def serviceUrl = env("SERVICE_URL")
    @Shared
    static final String DEFAULT = "default"
    @Shared
    static final def EMPTY = ""
    static def generator = { String alphabet, int n ->
        new Random().with {
            (1..n).collect { alphabet[ nextInt( alphabet.length() ) ] }.join()
        }
    }

    def deleteBucket(String bucketName) {
        BasicAWSCredentials basicAWSCredentials = new BasicAWSCredentials(awsAccessKeyId, awsSecretAccessKey)
        TransferManager tm = new TransferManager(basicAWSCredentials)
        final AmazonS3 s3 = tm.getAmazonS3Client()

        try {
            ObjectListing objectListing = s3.listObjects(bucketName)
            while (true) {
                for (Iterator<?> iterator = objectListing.getObjectSummaries().iterator(); iterator.hasNext(); ) {
                    S3ObjectSummary summary = (S3ObjectSummary) iterator.next()
                    s3.deleteObject(bucketName, summary.getKey())
                }

                if (objectListing.isTruncated()) {
                    objectListing = s3.listNextBatchOfObjects(objectListing)
                } else {
                    break
                }
            }

            VersionListing versionListing = s3.listVersions(new ListVersionsRequest().withBucketName(bucketName))
            while (true) {
                for (Iterator<?> iterator = versionListing.getVersionSummaries().iterator(); iterator.hasNext(); ) {
                    S3VersionSummary vs = (S3VersionSummary) iterator.next()
                    s3.deleteVersion(bucketName, vs.getKey(), vs.getVersionId())
                }

                if (versionListing.isTruncated()) {
                    versionListing = s3.listNextBatchOfVersions(versionListing)
                } else {
                    break
                }
            }

            s3.deleteBucket(bucketName)
        } catch (AmazonServiceException e) {
            println(e.getErrorMessage())
            return false
        }
        return true
    }

}
