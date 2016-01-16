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

import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthScope;

import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCredentialsProvider;

import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.Assert.assertEquals;


public class TestUtils {

    private static final long jobStatusPollIntervalMillis = 15000;
    private static Properties props;
    private static boolean isConfigDeletedSuccessfully = false;
    private static boolean isConfigCreatedSuccessfully = false;

    public static Properties getProperties() throws Exception {

        if (props == null) {
            props = new Properties();
            InputStream is = null;
            is = new FileInputStream("ecplugin.properties");
            props.load(is);
            is.close();
        }

        return props;
    }

    /**
     * callRunProcedure
     *
     * @param jo
     * @return the jobId of the job launched by runProcedure
     */
    public static String callRunProcedure(JSONObject jo) throws Exception {

        String encoding = new String(
                org.apache.commons.codec.binary.Base64
                        .encodeBase64(org.apache.commons.codec.binary.StringUtils.getBytesUtf8(props
                                .getProperty(StringConstants.COMMANDER_USER)
                                + ":"
                                + props.getProperty(StringConstants.COMMANDER_PASSWORD))));

        HttpClient httpClient = new DefaultHttpClient();

        JSONObject result;

        try {


            HttpPost httpPostRequest =
                    new HttpPost("http://" +
                    props
                            .getProperty(StringConstants.COMMANDER_SERVER)
                    + ":8000/rest/v1.0/jobs?request=runProcedure");


            StringEntity input = new StringEntity(jo.toString());

            input.setContentType("application/json");
            httpPostRequest.setEntity(input);
            httpPostRequest.setHeader("Authorization", "Basic " + encoding);
            HttpResponse httpResponse = httpClient.execute(httpPostRequest);

            result = new JSONObject(EntityUtils.toString(httpResponse.getEntity()));
            return result.getString("jobId");

        } finally {
            httpClient.getConnectionManager().shutdown();
        }

    }

    /**
     * waitForJob: Waits for job to be completed and reports outcome
     *
     * @param jobId
     * @return outcome of job
     */
    static String waitForJob(String jobId, long jobTimeOutMillis)
            throws Exception {

        long timeTaken = 0;

        String url = "http://" +   props
                .getProperty(StringConstants.COMMANDER_SERVER)
                + ":8000/rest/v1.0/jobs/" + jobId + "?request=getJobStatus";
        JSONObject jsonObject = performHTTPGet(url);

        while (!jsonObject.getString("status").equalsIgnoreCase("completed")) {
            Thread.sleep(jobStatusPollIntervalMillis);
            jsonObject = performHTTPGet(url);
            timeTaken += jobStatusPollIntervalMillis;
            if (timeTaken > jobTimeOutMillis) {
                throw new Exception("Job did not completed within time.");
            }
        }
        return jsonObject.getString("outcome");
    }

    /**
     * waitForJob: Waits for job to be completed and reports outcome
     *
     * @param jobId
     * @return outcome of job
     */
    static String getJobStatus(String jobId) throws IOException, JSONException {

        HttpClient httpClient = new DefaultHttpClient();
        String output = "";
        String encoding = new String(
                org.apache.commons.codec.binary.Base64
                        .encodeBase64(org.apache.commons.codec.binary.StringUtils.getBytesUtf8(props
                                .getProperty(StringConstants.COMMANDER_USER)
                                + ":"
                                + props.getProperty(StringConstants.COMMANDER_PASSWORD))));
        HttpGet httpGetRequest = new HttpGet("http://"
                +    props.getProperty(StringConstants.COMMANDER_SERVER) + ":8000/rest/v1.0/jobs/"
                + jobId + "?request=getJobDetails");
        httpGetRequest.setHeader("Authorization", "Basic " + encoding);

        try {

            HttpResponse httpResponse = httpClient.execute(httpGetRequest);
            if (httpResponse.getStatusLine().getStatusCode() >= 400) {
                throw new RuntimeException("HTTP GET failed with "
                        + httpResponse.getStatusLine().getStatusCode() + "-"
                        + httpResponse.getStatusLine().getReasonPhrase());
            }

            output = new JSONObject(EntityUtils.toString(httpResponse
                    .getEntity())).getJSONObject("job").getJSONArray("jobStep")
                    .getJSONObject(0).getJSONObject("propertySheet")
                    .getJSONArray("property").getJSONObject(1)
                    .getString("value");

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            httpClient.getConnectionManager().shutdown();
        }

        return output;
    }

    static String getSubstring(String string,String regex){

        String substring = null;


        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(string);
        if (matcher.find())
        {
            substring = matcher.group(1);
        }
        return substring;
    }

    /**
     * Wrapper around a HTTP GET to a REST service
     *
     * @param url
     * @return JSONObject
     */
    static JSONObject performHTTPGet(String url) throws IOException, JSONException {

        HttpClient httpClient = new DefaultHttpClient();
        String encoding = new String(
                org.apache.commons.codec.binary.Base64
                        .encodeBase64(org.apache.commons.codec.binary.StringUtils.getBytesUtf8(props
                                .getProperty(StringConstants.COMMANDER_USER)
                                + ":"
                                + props.getProperty(StringConstants.COMMANDER_PASSWORD))));
        try {
            HttpGet httpGetRequest = new HttpGet(url);
            httpGetRequest.setHeader("Authorization", "Basic " + encoding);
            HttpResponse httpResponse = httpClient.execute(httpGetRequest);

            if (httpResponse.getStatusLine().getStatusCode() >= 400) {
                throw new RuntimeException("HTTP GET failed with " +
                        httpResponse.getStatusLine().getStatusCode() + "-" +
                        httpResponse.getStatusLine().getReasonPhrase());
            }
            return new JSONObject(EntityUtils.toString(httpResponse.getEntity()));

        } finally {
            httpClient.getConnectionManager().shutdown();
        }

    }

    /**
     * Create the S3 configuration used for this test suite
     */
    static void createConfiguration() throws Exception {

        long jobTimeoutMillis = 3 * 60 * 1000;
        if (isConfigCreatedSuccessfully == false) {

            String response = "";
            JSONObject parentJSONObject = new JSONObject();
            JSONArray actualParameterArray = new JSONArray();

            parentJSONObject.put("projectName", "EC-S3-" + StringConstants.PLUGIN_VERSION);
            parentJSONObject.put("procedureName", "CreateConfiguration");

            actualParameterArray.put(new JSONObject()
                    .put("value", "S3Cfg")
                    .put("actualParameterName", "config"));

            actualParameterArray.put(new JSONObject()
                    .put("actualParameterName", "service_url")
                    .put("value", props.getProperty(StringConstants.SERVICE_URL)));

            actualParameterArray.put(new JSONObject()
                    .put("actualParameterName", "credential")
                    .put("value", "web_credentials"));

            parentJSONObject.put("actualParameter", actualParameterArray);

            JSONArray credentialArray = new JSONArray();

            credentialArray.put(new JSONObject()
                    .put("credentialName", "web_credentials")
                    .put("userName", props.getProperty(StringConstants.ACCESS_ID))
                    .put("password", props.getProperty(StringConstants.SECRET_ACCESS_ID)));

            parentJSONObject.put("credential", credentialArray);


            actualParameterArray.put(new JSONObject()
                    .put("actualParameterName", "attempt")
                    .put("value", "1"));


            actualParameterArray.put(new JSONObject()
                    .put("actualParameterName", "debug")
                    .put("value", "1"));


            actualParameterArray.put(new JSONObject()
                    .put("actualParameterName", "desc")
                    .put("value", "Test Configuration"));


            actualParameterArray.put(new JSONObject()
                    .put("actualParameterName", "resource_pool")
                    .put("value", "default"));


            actualParameterArray.put(new JSONObject()
                    .put("actualParameterName", "workspace")
                    .put("value", "default"));

            String jobId = callRunProcedure(parentJSONObject);

            response = waitForJob(jobId, jobTimeoutMillis);

            // Check job status
            assertEquals("Job completed without errors", "success", response);

            isConfigCreatedSuccessfully = true;
        }
    }

    /**
     * Delete the S3 configuration used for this test suite (clear previous runs)
     */
    static void deleteConfiguration() throws Exception {

        long jobTimeoutMillis = 3 * 60 * 1000;
        if (isConfigDeletedSuccessfully == false) {

            String jobId = "";
            JSONObject param1 = new JSONObject();
            JSONObject jo = new JSONObject();
            jo.put("projectName", "EC-S3-" + StringConstants.PLUGIN_VERSION);
            jo.put("procedureName", "DeleteConfiguration");

            JSONArray actualParameterArray = new JSONArray();
            actualParameterArray.put(new JSONObject()
                    .put("value", "S3Cfg")
                    .put("actualParameterName", "config"));

            jo.put("actualParameter", actualParameterArray);

            JSONArray credentialArray = new JSONArray();

            credentialArray.put(new JSONObject()
                    .put("credentialName", "web_credentials")
                    .put("userName", props.getProperty(StringConstants.ACCESS_ID))
                    .put("password", props.getProperty(StringConstants.SECRET_ACCESS_ID)));

            jo.put("credential", credentialArray);

            jobId = callRunProcedure(jo);

            // Block on job completion
            waitForJob(jobId, jobTimeoutMillis);
            // Do not check job status. Delete will error if it does not exist
            // which is OK since that is the expected state.

            isConfigDeletedSuccessfully = true;
        }

    }

    public static int randInt() {
        int min = 100;
        int max = 10000;

        // NOTE: Usually this should be a field rather than a method
        // variable so that it is not re-seeded every call.
        Random rand = new Random();

        // nextInt is normally exclusive of the top value,
        // so add 1 to make it inclusive
        int randomNum = rand.nextInt((max - min) + 1) + min;

        return randomNum;
    }
}
