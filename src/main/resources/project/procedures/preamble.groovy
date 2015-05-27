import com.amazonaws.AmazonClientException
import com.amazonaws.AmazonServiceException
import com.amazonaws.auth.BasicAWSCredentials
import com.amazonaws.services.s3.model.*
import com.amazonaws.services.s3.AmazonS3
import com.amazonaws.services.s3.transfer.*

import java.io.ByteArrayInputStream
import java.io.File
import java.io.InputStream

import groovyx.net.http.RESTClient;
import static groovyx.net.http.ContentType.JSON
import groovyx.net.http.HTTPBuilder
import groovyx.net.http.ContentType
import groovyx.net.http.Method
import groovyx.net.http.RESTClient

import groovy.json.JsonOutput
import groovy.json.JsonSlurper

@Grab('com.amazonaws:aws-java-sdk-s3:1.9.30')
@Grab(group = 'org.codehaus.groovy.modules.http-builder', module = 'http-builder', version = '0.7.1')

enum RequestMethod {
    GET, POST, PUT
}

public class ElectricCommander {
    def commanderServer = 'https://' + System.getenv('COMMANDER_SERVER')
    def commanderPort = System.getenv("COMMANDER_HTTPS_PORT")

    def sessionId = System.getenv('COMMANDER_SESSIONID')

    def client = new RESTClient(commanderServer + ":" + commanderPort);
    def sysJobId = System.getenv('COMMANDER_JOBID')
    def sysJobStepId = System.getenv('COMMANDER_JOBSTEPID')

    def userName
    def password

    def jobStepId = '$[/myJobStep/jobStepId]'

    ElectricCommander() {

        client.ignoreSSLIssues()

        def resp = PerformHTTPRequest(RequestMethod.GET, '/rest/v1.0/jobsSteps/' + jobStepId + '/credentials/$[config]', [])

        if( resp == null ) {
            throw new Exception("Error : Invalid configuration $[config].");
        }
        assert resp.status == 200 : "Commander did not respond with 200 for credentials"

        userName = resp.getData().credential.userName
        password = resp.getData().credential.password

    }

    public setProperty(String propName, String propValue) {

        sysJobId = System.getenv('COMMANDER_JOBID')
        def jsonData = [propertyName : propName, value : propValue, jobId : sysJobId]

        def resp = PerformHTTPRequest(RequestMethod.POST, '/rest/v1.0/properties', jsonData)
        assert resp != null : "Could not set property on the Commander. Request failed"

    }

    public getCommanderProperty(String propName) {

        sysJobStepId = System.getenv('COMMANDER_JOBSTEPID')
        def url = '/rest/v1.0/properties/' + propName
        def query =  ['jobStepId': "" + sysJobStepId]
        def resp = PerformHTTPRequest(RequestMethod.GET, url, query, [])

        assert resp != null : "Could not get property " + propName + " on the Commander. Request failed"
        assert resp.status == 200 : "Commander did not respond with 200 for retrieving property "

        return resp.getData().property.value
    }


    private PerformHTTPRequest(RequestMethod request, String url, Object jsonData) {

        PerformHTTPRequest(request,url,["":""],jsonData)
    }
    private PerformHTTPRequest(RequestMethod request, String url, def query, Object jsonData) {
        def response
        def requestHeaders = ['Cookie': "sessionId=" + sessionId, 'Accept': 'application/json']

        try {
            switch (request) {
                case RequestMethod.GET:
                    response = client.get(path: url, query: query, headers: requestHeaders, requestContentType: JSON)
                    break
                case RequestMethod.POST:
                    response = client.post(path: url, headers: requestHeaders, body: jsonData, requestContentType: JSON)
                    break
                case RequestMethod.PUT:
                    break
            }
        } catch (groovyx.net.http.HttpResponseException ex) {
            println(ex.getResponse().getData())
            return null
        } catch (java.net.ConnectException ex) {
            println(ex.getResponse().getData())
            return null
        }
        return response
    }

}

/*
 * Exception handling routines
 *
 */
static void printDeleteResults(MultiObjectDeleteException mode) {
    System.out.format("%s \n", mode.getMessage());
    System.out.format("No. of objects successfully deleted = %s\n", mode.getDeletedObjects().size());
    System.out.format("No. of objects failed to delete = %s\n", mode.getErrors().size());
    System.out.format("Printing error data...\n");
    for (MultiObjectDeleteException.DeleteError deleteError : mode.getErrors()){
        System.out.format("Object Key: %s\t%s\t%s\n",
                deleteError.getKey(), deleteError.getCode(), deleteError.getMessage());
    }
}

static handleServiceException(AmazonServiceException ase) {
    println("Caught an AmazonServiceException, which means your request made it "
            + "to Amazon S3, but was rejected with an error response for some reason.");
    println("Error Message:    " + ase.getMessage());
    println("HTTP Status Code: " + ase.getStatusCode());
    println("AWS Error Code:   " + ase.getErrorCode());
    println("Error Type:       " + ase.getErrorType());
    println("Request ID:       " + ase.getRequestId());
}

def isFilenameValid
isFilenameValid = { String file ->

    File f = new File(file + "testFile.txt")
    try {
        // Try to create an empty file at the provided location to check write permissions/valid path

        if(f.createNewFile()){
            // Can successfully create an empty file.
            f.delete()
            return true
        } else {
            // if testFile.txt already exists
            f.delete()
            if(f.createNewFile()){
                // provided location is writable
                f.delete()
                return true
            }
        }

        return true
    }
    catch (java.io.IOException e) {
        // Can not write to a file at specified location.Return location as invalid
        return false
    }
}

static handleClientException(AmazonClientException ace) {
    println("Caught an AmazonClientException, which means the client encountered "
            + "a serious internal problem while trying to communicate with S3, "
            + "such as not being able to access the network.");
    println("Error Message: " + ace.getMessage());
}

def doesBucketExist
doesBucketExist = { AmazonS3 s3, String bucket ->

    try {
        /*
        * If a bucket exists, but isn't owned by you, trying to list its
        * objects returns a 403 AccessDenied error response from Amazon S3.
        * If a bucket DOESN'T exist at all, trying to list its objects
        * returns a 404 NoSuchBucket error response from Amazon S3.
        *
        * Notice that we supply the bucket name in the request and specify
        * that we want 0 keys returned since we don't actually care about the data.
        */
        s3.listObjects(new ListObjectsRequest(bucket, null, null, null, 1))

        return true
    } catch (AmazonServiceException ase) {
        //Access denied, bucket exists but in some others account, not in our's.

        if(ase.getStatusCode() == 403 || ase.getStatusCode() == 404){

            return false
        }
    }
}
