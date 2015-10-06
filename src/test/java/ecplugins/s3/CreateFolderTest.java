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

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Properties;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


public class CreateFolderTest {

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
    public void CreateOneFolder() throws Exception {
        long jobTimeoutMillis = 5 * 60 * 1000;
        S3Util util = new S3Util();


        if(!util.CheckIsBucketAvailable(bucketName))
            util.CreateBucket(bucketName);

        String folderName = "folder-test-" + TestUtils.randInt();

        //Create the formal parameters
        JSONObject jo = new JSONObject();

        jo.put("projectName", "EC-S3-" + StringConstants.PLUGIN_VERSION);
        jo.put("procedureName", "CreateFolder");

        JSONArray actualParameterArray = new JSONArray();
        actualParameterArray.put(new JSONObject()
                .put("value", bucketName)
                .put("actualParameterName", "bucketName"));

        actualParameterArray.put(new JSONObject()
                .put("actualParameterName", "config")
                .put("value", "S3Cfg"));

        actualParameterArray.put(new JSONObject()
                .put("actualParameterName", "folderName")
                .put("value", folderName));

        jo.put("actualParameter", actualParameterArray);

        String jobId = TestUtils.callRunProcedure(jo);

        String response = TestUtils.waitForJob(jobId, jobTimeoutMillis);

        // Check job status
        assertEquals("Job completed with errors", "success", response);

        //Check if the folder is actually available in S3
        assertTrue("The key is not found",  util.isValidFile(bucketName, folderName + "/"));
    }
    @AfterClass
    public static void cleanup() throws Exception {
        S3Util.DeleteBucket(bucketName);
    }

}