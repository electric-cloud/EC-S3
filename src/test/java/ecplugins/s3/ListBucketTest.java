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
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Properties;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by anand on 08-05-2015.
 */
public class ListBucketTest {

    private static Properties props;

    @BeforeClass
    public static void setup() throws Exception {

        props = TestUtils.getProperties();

        TestUtils.deleteConfiguration();
        TestUtils.createConfiguration();
    }

    @Test
    public void ListAllBuckets() throws Exception {
        long jobTimeoutMillis = 5 * 60 * 1000;
        JSONObject jo = new JSONObject();
        S3Util util = new S3Util();
        jo.put("projectName", "EC-S3-" + StringConstants.PLUGIN_VERSION);
        jo.put("procedureName", "ListBucket");

        JSONArray actualParameterArray = new JSONArray();

        actualParameterArray.put(new JSONObject()
                .put("actualParameterName", "config")
                .put("value", "S3Cfg"));

        jo.put("actualParameter", actualParameterArray);

        String jobId = TestUtils.callRunProcedure(jo);

        String response = TestUtils.waitForJob(jobId, jobTimeoutMillis);

        // Check job status
        assertEquals("Job completed with errors", "success", response);

        String res = TestUtils.getJobStatus(jobId);
        String info = TestUtils.getSubstring(res, "^Listed\\s(.+)\\sbuckets");

        int bucketNos = util.ListBuckets();

        int pluginBucketNos = Integer.parseInt(info);

        assertEquals("Number of buckets does not match", bucketNos,pluginBucketNos);

    }

}
