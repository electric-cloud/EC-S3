package ecplugins.s3;

import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import com.amazonaws.services.s3.transfer.TransferManager;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.List;
import java.util.Properties;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


public class UploadFolderTest {

    private static Properties props;
    private static String  bucketName;

    @BeforeClass
    public static void setup() throws Exception {

        props = TestUtils.getProperties();
        bucketName = "ec-s3-plugin-test-bucket" + TestUtils.randInt();
        TestUtils.deleteConfiguration();
        TestUtils.createConfiguration();
    }

    @Test
    public void UploadFolderTest() throws Exception {
        long jobTimeoutMillis = 5 * 60 * 1000;
        S3Util util = new S3Util();

        String folderToUpload = S3Util.createFolder();

        if(!util.CheckIsBucketAvailable(bucketName))
            util.CreateBucket(bucketName);

        JSONObject jo = new JSONObject();

        jo.put("projectName", "EC-S3-" + StringConstants.PLUGIN_VERSION);
        jo.put("procedureName", "UploadFolder");

        JSONArray actualParameterArray = new JSONArray();
        actualParameterArray.put(new JSONObject()
                .put("value", bucketName)
                .put("actualParameterName", "bucketName"));

        actualParameterArray.put(new JSONObject()
                .put("actualParameterName", "config")
                .put("value", "S3Cfg"));

        actualParameterArray.put(new JSONObject()
                .put("actualParameterName", "key")
                .put("value", "ec-s3-plugin-test-folder"));

        actualParameterArray.put(new JSONObject()
                .put("actualParameterName", "folderToUpload")
                .put("value", folderToUpload));

        actualParameterArray.put(new JSONObject()
                .put("actualParameterName", "access_public")
                .put("value", "1"));

        jo.put("actualParameter", actualParameterArray);

        String jobId = TestUtils.callRunProcedure(jo);

        String response = TestUtils.waitForJob(jobId, jobTimeoutMillis);

        // Check job status
        assertEquals("Job completed with errors", "success", response);


        Properties props = TestUtils.getProperties();

        BasicAWSCredentials credentials = new BasicAWSCredentials(props.getProperty(StringConstants.ACCESS_ID), props.getProperty(StringConstants.SECRET_ACCESS_ID));

        // Create TransferManager
        TransferManager tx = new TransferManager(credentials);

        // Get S3 Client
        AmazonS3 s3 = tx.getAmazonS3Client();

        // There must be only one folder with name ec-s3-plugin-test-folder in the bucket
        ObjectListing objectListing = s3.listObjects(bucketName);
        List<S3ObjectSummary> summaries = objectListing.getObjectSummaries();

        assertEquals("Number of objects in bucket does not match", summaries.size(), 10);

        for(S3ObjectSummary summary: summaries){
            assertEquals("Bucket name does not match"  ,bucketName, summary.getBucketName());
        }


    }

    @AfterClass
    public static void  cleanup() throws Exception {
        S3Util.deleteTestFolder();
        S3Util.DeleteBucket(bucketName);
    }
}