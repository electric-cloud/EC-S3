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


public class ListFolderTest {

    private static Properties props;
    private static String bucketName;

    @BeforeClass
    public static void setup() throws Exception {

        props = TestUtils.getProperties();
        bucketName = "ec-s3-plugin-" + TestUtils.randInt();
        TestUtils.deleteConfiguration();
        TestUtils.createConfiguration();
    }

    @Test
    public void ListFolder() throws Exception {
        long jobTimeoutMillis = 5 * 60 * 1000;
        S3Util util = new S3Util();


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
    public static void cleanup() throws Exception {

        S3Util.deleteTestData();
        S3Util.DeleteBucket(bucketName);
    }
}