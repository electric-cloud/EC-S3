package com.cloudbees.pdk.hen.tests

import com.amazonaws.AmazonClientException
import com.amazonaws.AmazonServiceException
import com.amazonaws.auth.AnonymousAWSCredentials
import com.amazonaws.auth.BasicAWSCredentials
import com.amazonaws.services.s3.AmazonS3
import com.amazonaws.services.s3.internal.Constants
import com.amazonaws.services.s3.model.HeadBucketRequest
import com.amazonaws.services.s3.model.ListObjectsV2Result
import com.amazonaws.services.s3.model.ListVersionsRequest
import com.amazonaws.services.s3.model.ObjectListing
import com.amazonaws.services.s3.model.S3ObjectSummary
import com.amazonaws.services.s3.model.S3VersionSummary
import com.amazonaws.services.s3.model.VersionListing
import com.amazonaws.services.s3.transfer.TransferManager
import com.cloudbees.pdk.hen.JobResponse
import com.cloudbees.pdk.hen.S3
import com.cloudbees.pdk.hen.ServerHandler
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
    @Shared
    static final TEMP_LOCATION = "/tmp";
    @Shared
    static final FILE_NAME = "specTest.txt"
    @Shared
    static final INDEX_FILE = "index.html"
    @Shared
    static final ERROR_FILE = "error.html"
    @Shared
    static final FOLDER_NAME = "specFolder"
    @Shared
    static ServerHandler serverHandler = ServerHandler.getInstance()

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

    def createBucket(String bucketName) {
        def bucket = pluginWithoutConfig.createBucket.flush()
                .config(configName)
                .bucketName(bucketName)
                .run()
        assert bucket.isSuccessful()
        assert isBucketExist(bucketName)
    }

    def createFile() {
        JobResponse createFileJob = serverHandler.runCommand("mkdir -p ${TEMP_LOCATION}/${FOLDER_NAME} && chmod -R 777 ${TEMP_LOCATION}/${FOLDER_NAME} && echo \"This is test file\" > ${TEMP_LOCATION}/${FOLDER_NAME}/${FILE_NAME} && echo \"This is test file\" > ${TEMP_LOCATION}/${FILE_NAME}", "bash", 'local')
        assert createFileJob.successful
    }
    def createFileForWebsiteHosting() {
        JobResponse createFileJob = serverHandler.runCommand("echo -e '<html>\n<html>\n\t<body>\n\t\t<h1>Welcome to CloudBees!!!</h1>\n\t</body>\n</html>' > ${TEMP_LOCATION}/${INDEX_FILE} && echo -e '<html>\n<html>\n\t<body>\n\t\t<h1>Oops somthing wrong!!!</h1>\n\t</body>\n</html>' > ${TEMP_LOCATION}/${ERROR_FILE}", "bash", 'local')
        assert createFileJob.successful
    }
    def removeFolder() {
        JobResponse createFileJob = serverHandler.runCommand("rm -rf specFolder", "bash", 'local')
        assert createFileJob.successful
    }

    def checkBucketContentIsEmpty(String bucketName) {
        final AmazonS3 s3 = this.client()

        try {
            ObjectListing objectListing = s3.listObjects(bucketName)
            return objectListing.objectSummaries.size() == 0
        }catch (AmazonServiceException e) {
            println(e.getErrorMessage())
            return false
        }
    }
    def isFolderExist(String bucketName, String key) {
        final AmazonS3 s3 = this.client()

        try {
            ListObjectsV2Result result = s3.listObjectsV2(bucketName, key)
            return result.getKeyCount() > 0
        }catch (AmazonServiceException e) {
            println(e.getErrorMessage())
            return false
        }
    }
    def isBucketExist(String bucketName) {
        final AmazonS3 s3 = this.client()
        HeadBucketRequest headBucketRequest = new HeadBucketRequest(bucketName)
        headBucketRequest.setRequestCredentials(new AnonymousAWSCredentials())
        try {
            s3.headBucket(headBucketRequest)
        } catch (AmazonServiceException e) {
            if (e.getStatusCode() == Constants.NO_SUCH_BUCKET_STATUS_CODE) {
                return false
            }
        }
        return true
    }
    def isObjectExist(String bucketName, String objectName) {
        final AmazonS3 s3 = this.client()
        try {
            return s3.doesObjectExist(bucketName, objectName)
        } catch (AmazonServiceException e) {
            e.printStackTrace()
            return false
        }
    }
    def getAllBuckets() {
        final AmazonS3 s3 = this.client()
        try {
            return s3.listBuckets().size()
        } catch (AmazonServiceException e) {
            return 0
        }
    }
    def getAllFolders(String bucketName, String prefix) {
        final AmazonS3 s3 = this.client()
        try {
            return s3.listObjects(bucketName, prefix).getObjectSummaries().size()
        } catch (AmazonServiceException e) {
            return 0
        }
    }
    def client() {
        BasicAWSCredentials basicAWSCredentials = new BasicAWSCredentials(awsAccessKeyId, awsSecretAccessKey)
        TransferManager tm = new TransferManager(basicAWSCredentials)
        return tm.getAmazonS3Client()
    }

    def checkFolderExistenceInResource(String directory){
        JobResponse directoryJob = serverHandler.runCommand("[ -d \"${directory}\" ] && echo \"Folder exists\" || exit 1", "bash", 'local')
        return directoryJob.successful
    }
    def checkFileExistenceInResource(String file){
        JobResponse fileJob = serverHandler.runCommand("[ -f \"${file}\" ] && echo \"File exists\" || exit 1", "bash", 'local')
        return fileJob.successful
    }

}
