package ecplugins.s3;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.File;
import java.util.Properties;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;


public class DownloadFolderTest {

    private static Properties props;
    private static String bucketName;
    private static String downloadLocation;

    @BeforeClass
    public static void setup() throws Exception {

        S3Util util = new S3Util();

        props = TestUtils.getProperties();
        bucketName = "ec-s3-plugin-" + TestUtils.randInt();

        if(!util.CheckIsBucketAvailable(bucketName)) {
            System.out.println("Creating bucket : " + bucketName);
            util.CreateBucket(bucketName);
        } else {
            System.out.println("Bucket : " + bucketName + " already present");
        }

        S3Util.UploadFolder(bucketName,"ec-s3-plugin-folder");

        String workingDir = System.getProperty("user.dir");
        downloadLocation = workingDir + File.separator + "ec-s3-plugin-folder";

        TestUtils.deleteConfiguration();
        TestUtils.createConfiguration();
    }

    @Test
    public void DownloadFolder() throws Exception {
        long jobTimeoutMillis = 5 * 60 * 1000;

        //Create the formal parameters
        JSONObject jo = new JSONObject();

        jo.put("projectName", "EC-S3-" + StringConstants.PLUGIN_VERSION);
        jo.put("procedureName", "DownloadFolder");

        JSONArray actualParameterArray = new JSONArray();
        actualParameterArray.put(new JSONObject()
                .put("actualParameterName", "config")
                .put("value", "S3Cfg"));

        actualParameterArray.put(new JSONObject()
                .put("actualParameterName", "bucketName")
                .put("value", bucketName));

        actualParameterArray.put(new JSONObject()
                .put("actualParameterName", "key")
                .put("value", "ec-s3-plugin-folder"));

        actualParameterArray.put(new JSONObject()
                .put("actualParameterName", "downloadLocation")
                .put("value", downloadLocation));

        jo.put("actualParameter", actualParameterArray);

        String jobId = TestUtils.callRunProcedure(jo);

        String response = TestUtils.waitForJob(jobId, jobTimeoutMillis);

        // Check job status
        assertEquals("Job completed with errors", "success", response);
        File downloadDirectory = new File(downloadLocation + File.separator + "ec-s3-plugin-folder");
        assertTrue(downloadDirectory.exists());
        assertTrue(downloadDirectory.isDirectory());
        assertEquals("Not all the files got downloaded", 10, downloadDirectory.listFiles().length);

    }

    @AfterClass
    public static void cleanup() throws Exception {
        S3Util.DeleteBucket(bucketName);
        S3Util.deleteTestFolder();
        S3Util.deleteFolder(new File(downloadLocation));
    }
}