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

    public static void DeleteBucket(String bucketName) throws Exception {

        Properties props = TestUtils.getProperties();

        BasicAWSCredentials credentials = new BasicAWSCredentials(props.getProperty(StringConstants.ACCESS_ID), props.getProperty(StringConstants.SECRET_ACCESS_ID));

        // Create TransferManager
        TransferManager tx = new TransferManager(credentials);

        // Get S3 Client
        AmazonS3 s3 = tx.getAmazonS3Client();

        if (s3.doesBucketExist(bucketName)) {
            s3.deleteBucket(bucketName);
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


    public static String createFile() throws IOException {
        File file = null;
        String OS = System.getProperty("os.name").toLowerCase();

        if((OS.indexOf("win") >= 0)) //if its windows system
        {
            new File("C:\\EC-S3AutomatedTestTemp").mkdir();
            file = new File("C:\\EC-S3AutomatedTestTemp\\test.txt");
        } else {
            // /var/tmp dir already exists.
            file = new File("/var/tmp/test.txt");
        }

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
}

