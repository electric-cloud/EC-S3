package ecplugins.s3;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Properties;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;


public class DeleteObjectTest {

    private static Properties props;

    @BeforeClass
    public static void setup() throws Exception {

        props = TestUtils.getProperties();

        TestUtils.deleteConfiguration();
        TestUtils.createConfiguration();
    }

    @Test
    public void DeleteObject() throws Exception {
        long jobTimeoutMillis = 5 * 60 * 1000;
        S3Util util = new S3Util();

        String bucketName = "ec-s3-plugin-" + TestUtils.randInt();
        if(!util.CheckIsBucketAvailable(bucketName)) {
            System.out.println("Creating bucket : " + bucketName);
            util.CreateBucket(bucketName);
        } else {
            System.out.println("Bucket : " + bucketName + " already present");
        }

        util.UploadObject(bucketName, "license_test.xml", "C:\\test\\project.xml");

        //Create the formal parameters
        JSONObject jo = new JSONObject();

        jo.put("projectName", "EC-S3-" + StringConstants.PLUGIN_VERSION);
        jo.put("procedureName", "DeleteObject");

        JSONArray actualParameterArray = new JSONArray();
        actualParameterArray.put(new JSONObject()
                .put("actualParameterName", "bucketName")
                .put("value", bucketName));

        actualParameterArray.put(new JSONObject()
                .put("actualParameterName", "config")
                .put("value", "S3Cfg"));

        actualParameterArray.put(new JSONObject()
                .put("actualParameterName", "key")
                .put("value", "license_test.xml"));

        jo.put("actualParameter", actualParameterArray);

        String jobId = TestUtils.callRunProcedure(jo);

        String response = TestUtils.waitForJob(jobId, jobTimeoutMillis);

        // Check job status
        assertEquals("Job completed with errors", "success", response);
        assertFalse("The key is still available", util.isValidFile(bucketName, "license_test.xml"));
    }


}