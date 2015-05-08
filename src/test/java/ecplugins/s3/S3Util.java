package ecplugins.s3;


import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.*;
import com.amazonaws.services.s3.transfer.*;
import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;

import java.util.Properties;
import java.util.*;
;

/**
 * Created by anand on 07-05-2015.
 */
public class S3Util {

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
}

