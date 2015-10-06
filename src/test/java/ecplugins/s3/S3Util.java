/*
*
* Copyright 2015 Electric Cloud, Inc.
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
*     http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/

package ecplugins.s3;


import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.*;
import com.amazonaws.services.s3.transfer.*;
import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;

import java.io.File;
import java.io.IOException;
import java.util.Properties;
import java.util.*;
;

/**
 * Created by anand on 07-05-2015.
 */
public class S3Util {

    private static String testFileLocation;
    private static String testFolderLocation;

    /**
     * This procedure deletes the bucket along with its contents
     * @param bucketName
     * @return
     * @throws Exception
     */
    public static boolean DeleteBucket(String bucketName) throws Exception {

        Properties props = TestUtils.getProperties();

        BasicAWSCredentials credentials = new BasicAWSCredentials(props.getProperty(StringConstants.ACCESS_ID), props.getProperty(StringConstants.SECRET_ACCESS_ID));

        // Create TransferManager
        TransferManager tx = new TransferManager(credentials);

        // Get S3 Client
        AmazonS3 s3 = tx.getAmazonS3Client();

        if (s3.doesBucketExist(bucketName)) {
            // Multi-object delete by specifying only keys (no version ID).
            DeleteObjectsRequest multiObjectDeleteRequest = new DeleteObjectsRequest(
                    bucketName).withQuiet(false);

            //get keys
            List<String> keys = new ArrayList<String>();
            ObjectListing objectListing = s3.listObjects(new ListObjectsRequest()
                    .withBucketName(bucketName));
            for (S3ObjectSummary objectSummary : objectListing.getObjectSummaries()) {
                keys.add(objectSummary.getKey());
            }

            // Create request that include only object key names.
            List<DeleteObjectsRequest.KeyVersion> justKeys = new ArrayList<DeleteObjectsRequest.KeyVersion>();
            for (String key : keys) {
                justKeys.add(new DeleteObjectsRequest.KeyVersion(key));
            }

            if (justKeys.size() == 0) {
                return false;
            }

            multiObjectDeleteRequest.setKeys(justKeys);
            // Execute DeleteObjects - Amazon S3 add delete marker for each object
            // deletion. The objects no disappear from your bucket (verify).
            DeleteObjectsResult delObjRes = null;

            delObjRes = s3.deleteObjects(multiObjectDeleteRequest);

            s3.deleteBucket(bucketName);
            return true;
        } else {
            System.out.println("Error: Bucket with name " + bucketName + " does not exists.");
            return  false;
        }
    }

    public static void CreateBucket(String bucketName) throws Exception {

        Properties props = TestUtils.getProperties();

        BasicAWSCredentials credentials = new BasicAWSCredentials(props.getProperty(StringConstants.ACCESS_ID), props.getProperty(StringConstants.SECRET_ACCESS_ID));

        // Create TransferManager
        TransferManager tx = new TransferManager(credentials);

        // Get S3 Client
        AmazonS3 s3 = tx.getAmazonS3Client();

        s3.createBucket(bucketName);

    }

    public static boolean CheckIsBucketAvailable(String bucketName) throws Exception {

        Properties props = TestUtils.getProperties();

        BasicAWSCredentials credentials = new BasicAWSCredentials(props.getProperty(StringConstants.ACCESS_ID), props.getProperty(StringConstants.SECRET_ACCESS_ID));

        // Create TransferManager
        TransferManager tx = new TransferManager(credentials);

        // Get S3 Client
        AmazonS3 s3 = tx.getAmazonS3Client();

        return s3.doesBucketExist(bucketName);
    }

    public static int ListBuckets() throws Exception {
        Properties props = TestUtils.getProperties();

        BasicAWSCredentials credentials = new BasicAWSCredentials(props.getProperty(StringConstants.ACCESS_ID), props.getProperty(StringConstants.SECRET_ACCESS_ID));

        // Create TransferManager
        TransferManager tx = new TransferManager(credentials);

        // Get S3 Client
        AmazonS3 s3 = tx.getAmazonS3Client();

        List<Bucket> buckets = s3.listBuckets();

        return buckets.size();
    }

    public static void UploadObject(String bucketName, String key) throws AmazonClientException, AmazonServiceException, Exception {
        Properties props = TestUtils.getProperties();
        File file  =  new File(createFile());
        BasicAWSCredentials credentials = new BasicAWSCredentials(props.getProperty(StringConstants.ACCESS_ID), props.getProperty(StringConstants.SECRET_ACCESS_ID));

        // Create TransferManager
        TransferManager tx = new TransferManager(credentials);

        // Get S3 Client
        AmazonS3 s3 = tx.getAmazonS3Client();

        try {
            System.out.println("Uploading a new object to S3 from a file\n");

            s3.putObject(new PutObjectRequest(
                    bucketName, key, file));

        } catch (AmazonServiceException ase) {
            System.out.println("Caught an AmazonServiceException");
            System.out.println("Error Message:    " + ase.getMessage());
            System.out.println("HTTP Status Code: " + ase.getStatusCode());
            System.out.println("AWS Error Code:   " + ase.getErrorCode());
            System.out.println("Error Type:       " + ase.getErrorType());
            System.out.println("Request ID:       " + ase.getRequestId());
        } catch (AmazonClientException ace) {
            System.out.println("Caught an AmazonClientException, which  means the client encountered  an internal error while trying to such as not being able to access the network.");
            System.out.println("Error Message: " + ace.getMessage());
        }

    }

    public static boolean UploadFolder(String bucketName, String key) throws AmazonClientException, AmazonServiceException, Exception {
        Properties props = TestUtils.getProperties();
        File file  =  new File(createFolder());
        BasicAWSCredentials credentials = new BasicAWSCredentials(props.getProperty(StringConstants.ACCESS_ID), props.getProperty(StringConstants.SECRET_ACCESS_ID));

        // Create TransferManager
        TransferManager tx = new TransferManager(credentials);

        // Get S3 Client
        AmazonS3 s3 = tx.getAmazonS3Client();
        MultipleFileUpload objectUpload = tx.uploadDirectory(bucketName, key, file, true);

        while (!objectUpload.isDone()) {
            Thread.sleep(1000);
        }

        return true;
    }


    public static String createFile() throws IOException {
        File file = null;
        // Create a sample file at project root directory
        file = new File("test.txt");

        boolean result = file.createNewFile();
        if(result == false){
            System.out.println("Deleting already existing test.txt");
            file.delete();
            file.createNewFile();
        }

        testFileLocation = file.getAbsolutePath().replace('\\','/');
        return testFileLocation;
    }

    public static boolean isValidFile(String bucketName,
                                      String path) throws AmazonClientException, AmazonServiceException, Exception {
        boolean isValidFile = true;
        Properties props = TestUtils.getProperties();

        BasicAWSCredentials credentials = new BasicAWSCredentials(props.getProperty(StringConstants.ACCESS_ID), props.getProperty(StringConstants.SECRET_ACCESS_ID));

        // Create TransferManager
        TransferManager tx = new TransferManager(credentials);

        // Get S3 Client
        AmazonS3 s3 = tx.getAmazonS3Client();

        try {
            ObjectMetadata objectMetadata = s3.getObjectMetadata(bucketName, path);
        } catch (AmazonS3Exception s3e) {
            if (s3e.getStatusCode() == 404) {
                // i.e. 404: NoSuchKey - The specified key does not exist
                isValidFile = false;
            }
            else {
                throw s3e;    // rethrow all S3 exceptions other than 404
            }
        }

        return isValidFile;
    }

    public static void deleteTestData() {

        File fileToDelete =  new File(testFileLocation);
        fileToDelete.delete();
    }

    public static String createFolder() throws IOException {

        File folder,file = null;
        // Create a folder at project root directory.
        folder = new File("automatedTestData");

        if(folder.exists()) {
            folder.delete();
        }
        // Create an empty folder
        boolean result = folder.mkdir();

        // Create 10 files inside it.
        for(int i =0 ;i < 10; i++){
            file = new File("automatedTestData/testFile" + i);
            file.createNewFile();
        }

        testFolderLocation = folder.getAbsolutePath().replace('\\','/');
        return testFolderLocation;

    }
    public static boolean deleteTestFolder(){
        File directory = new File(testFolderLocation);
        return deleteFolder(directory);
    }
    public static boolean deleteFolder(File directory) {

        if(directory.exists()){
            File[] files = directory.listFiles();
            if(null!=files){
                for(int i=0; i<files.length; i++) {
                    if(files[i].isDirectory()) {
                        deleteFolder(files[i]);
                    }
                    else {
                        files[i].delete();
                    }
                }
            }
        }
        return(directory.delete());
    }

}

