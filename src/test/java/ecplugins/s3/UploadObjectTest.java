package ecplugins.s3;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Properties;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


public class UploadObjectTest {

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
    public void UploadOneObject() throws Exception {
        long jobTimeoutMillis = 5 * 60 * 1000;
        S3Util util = new S3Util();
        String fileToUpload = S3Util.createFile();

        if(!util.CheckIsBucketAvailable(bucketName))
            util.CreateBucket(bucketName);

        JSONObject jo = new JSONObject();

        jo.put("projectName", "EC-S3-" + StringConstants.PLUGIN_VERSION);
        jo.put("procedureName", "UploadObject");

        JSONArray actualParameterArray = new JSONArray();
        actualParameterArray.put(new JSONObject()
                .put("value", bucketName)
                .put("actualParameterName", "bucketName"));

        actualParameterArray.put(new JSONObject()
                .put("actualParameterName", "config")
                .put("value", "S3Cfg"));

        actualParameterArray.put(new JSONObject()
                .put("actualParameterName", "fileToUpload")
                .put("value", fileToUpload));

        actualParameterArray.put(new JSONObject()
                .put("actualParameterName", "key")
                .put("value", "template.xml"));


        jo.put("actualParameter", actualParameterArray);

        String jobId = TestUtils.callRunProcedure(jo);

        String response = TestUtils.waitForJob(jobId, jobTimeoutMillis);

        // Check job status
        assertEquals("Job completed with errors", "success", response);

        assertTrue("The key is not found",  util.isValidFile(bucketName, "template.xml"));
    }

    @AfterClass
    public static void  cleanup() throws Exception {

        S3Util.deleteTestData();
        S3Util.DeleteBucket(bucketName);
    }
}