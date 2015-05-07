package ecplugins.s3;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.util.Properties;

import static org.junit.Assert.*;


public class TestUtils {

    private static Properties props;
    private static final long jobStatusPollIntervalMillis = 15000;
    private static boolean isConfigDeletedSuccessfully = false;
    private static boolean isConfigCreatedSuccessfully = false;

    public static Properties getProperties() throws Exception {

        if(props == null){
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

        HttpClient httpClient = new DefaultHttpClient();
        JSONObject result = null;

        try {
            HttpPost httpPostRequest = new HttpPost("http://" + props.getProperty(StringConstants.COMMANDER_USER)
                    + ":" + props.getProperty(StringConstants.COMMANDER_PASSWORD) + "@" + StringConstants.COMMANDER_SERVER
                    + ":8000/rest/v1.0/jobs?request=runProcedure");
            StringEntity input = new StringEntity(jo.toString());

            input.setContentType("application/json");
            httpPostRequest.setEntity(input);
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
     static String waitForJob(String jobId, long jobTimeOutMillis) throws Exception {

        long timeTaken = 0;

        String url = "http://" + props.getProperty(StringConstants.COMMANDER_USER) + ":" + props.getProperty(StringConstants.COMMANDER_PASSWORD) +
                "@" + StringConstants.COMMANDER_SERVER + ":8000/rest/v1.0/jobs/" +
                jobId + "?request=getJobStatus";
        JSONObject jsonObject = performHTTPGet(url);


        while (!jsonObject.getString("status").equalsIgnoreCase("completed")) {
            Thread.sleep(jobStatusPollIntervalMillis);
            jsonObject = performHTTPGet(url);
            timeTaken += jobStatusPollIntervalMillis;
            if(timeTaken > jobTimeOutMillis){
                throw new Exception("Job did not completed within time.");
            }
        }

        return jsonObject.getString("outcome");

    }

    /**
     * Wrapper around a HTTP GET to a REST service
     *
     * @param url
     * @return JSONObject
     */
     static JSONObject performHTTPGet(String url) throws IOException, JSONException {

        HttpClient httpClient = new DefaultHttpClient();
        try {
            HttpGet httpGetRequest = new HttpGet(url);

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
         if(isConfigCreatedSuccessfully == false) {

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

             response = waitForJob(jobId,jobTimeoutMillis);

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
             waitForJob(jobId,jobTimeoutMillis);
             // Do not check job status. Delete will error if it does not exist
             // which is OK since that is the expected state.

            isConfigDeletedSuccessfully = true;
         }

     }
}
