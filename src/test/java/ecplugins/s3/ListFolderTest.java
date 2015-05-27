package ecplugins.s3;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Properties;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


public class ListFolderTest {

    private static Properties props;

    @BeforeClass
    public static void setup() throws Exception {

        props = TestUtils.getProperties();

        TestUtils.deleteConfiguration();
        TestUtils.createConfiguration();
    }

    @Test
    public void ListFolder() throws Exception {
        long jobTimeoutMillis = 5 * 60 * 1000;
        S3Util util = new S3Util();

        String bucketName = "ec-s3-plugin-" + TestUtils.randInt();
        if(!util.CheckIsBucketAvailable(bucketName)) {
            System.out.println("Creating bucket : " + bucketName);
            util.CreateBucket(bucketName);
        } else {
            System.out.println("Bucket : " + bucketName + " already present");
        }

        util.UploadObject(bucketName, "license_test.xml");

        //Create the formal parameters
        JSONObject jo = new JSONObject();

        jo.put("projectName", "EC-S3-" + StringConstants.PLUGIN_VERSION);
        jo.put("procedureName", "ListFolder");

        JSONArray actualParameterArray = new JSONArray();
        actualParameterArray.put(new JSONObject()
                .put("actualParameterName", "bucketName")
                .put("value", bucketName));

        actualParameterArray.put(new JSONObject()
                .put("actualParameterName", "config")
                .put("value", "S3Cfg"));

        actualParameterArray.put(new JSONObject()
                .put("actualParameterName", "folderName")
                .put("value", ""));

        actualParameterArray.put(new JSONObject()
                .put("actualParameterName", "include_sub_folder")
                .put("value", "1"));

        jo.put("actualParameter", actualParameterArray);

        String jobId = TestUtils.callRunProcedure(jo);

        String response = TestUtils.waitForJob(jobId, jobTimeoutMillis);

        String res = TestUtils.getJobStatus(jobId);
        String info = TestUtils.getSubstring(res, "^Listed\\s(.+)\\sobjects");

        //int bucketNos = util.ListBuckets();

        int pluginObjecttNos = Integer.parseInt(info);

        assertEquals("Number of objects does not match", 1, pluginObjecttNos);
        // Check job status
        assertEquals("Job completed with errors", "success", response);

    }

    @AfterClass
    public static void cleanup(){

        S3Util.deleteTestData();
    }
}