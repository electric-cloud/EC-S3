package ecplugins.s3;

import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.BucketWebsiteConfiguration;
import com.amazonaws.services.s3.transfer.TransferManager;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.validation.constraints.AssertFalse;
import java.util.Properties;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


public class WebSiteHostingTest {

    private static Properties props;
    private static String bucketName;

    @BeforeClass
    public static void setup() throws Exception {

        props = TestUtils.getProperties();
        bucketName = "ec-s3-plugin-test-bucket" + TestUtils.randInt();
        TestUtils.deleteConfiguration();
        TestUtils.createConfiguration();
    }

    @Test
    public void WebSiteHostingTest() throws Exception {
        long jobTimeoutMillis = 5 * 60 * 1000;
        S3Util util = new S3Util();
        String fileToUpload = S3Util.createFile();

        if(!util.CheckIsBucketAvailable(bucketName))
            util.CreateBucket(bucketName);

        S3Util.UploadObject(bucketName,"index.html");
        S3Util.UploadObject(bucketName,"error.html");

        JSONObject jo = new JSONObject();

        jo.put("projectName", "EC-S3-" + StringConstants.PLUGIN_VERSION);
        jo.put("procedureName", "WebsiteHosting");

        JSONArray actualParameterArray = new JSONArray();
        actualParameterArray.put(new JSONObject()
                .put("value", bucketName)
                .put("actualParameterName", "bucketName"));

        actualParameterArray.put(new JSONObject()
                .put("actualParameterName", "config")
                .put("value", "S3Cfg"));

        actualParameterArray.put(new JSONObject()
                .put("actualParameterName", "website_hosting")
                .put("value", "1"));

        actualParameterArray.put(new JSONObject()
                .put("actualParameterName", "indexDoc")
                .put("value", "index.html"));

        actualParameterArray.put(new JSONObject()
                .put("actualParameterName", "errorDoc")
                .put("value", "error.html"));

        jo.put("actualParameter", actualParameterArray);

        String jobId = TestUtils.callRunProcedure(jo);

        String response = TestUtils.waitForJob(jobId, jobTimeoutMillis);

        // Check job status
        assertEquals("Job completed with errors", "success", response);

        BasicAWSCredentials credentials = new BasicAWSCredentials(props.getProperty(StringConstants.ACCESS_ID), props.getProperty(StringConstants.SECRET_ACCESS_ID));

        // Create TransferManager
        TransferManager tx = new TransferManager(credentials);

        // Get S3 Client
        AmazonS3 s3 = tx.getAmazonS3Client();

        BucketWebsiteConfiguration bucketWebsiteConfiguration = s3.getBucketWebsiteConfiguration(bucketName);
        assertEquals("Error document does not match", "error.html" , bucketWebsiteConfiguration.getErrorDocument());
        assertEquals("Index document does not match", "index.html", bucketWebsiteConfiguration.getIndexDocumentSuffix());

        //assertTrue("The key is not found",  util.isValidFile(bucketName, "template.xml"));
    }

    @AfterClass
    public static void  cleanup() throws Exception {

        S3Util.DeleteBucket(bucketName);
    }
}