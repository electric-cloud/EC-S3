

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

    def userName
    def password

    def jobStepId = '$[/myJobStep/jobStepId]'

    ElectricCommander() {

        client.ignoreSSLIssues()

        def resp = PerformHTTPRequest(RequestMethod.GET, '/rest/v1.0/jobsSteps/' + jobStepId + '/credentials/$[config]', [])

        assert resp != null : "Could not retrieve Credentials from the commander. Request failed"
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

    private PerformHTTPRequest(RequestMethod request, String url, Object jsonData) {
        def response
        def requestHeaders = ['Cookie': "sessionId=" + sessionId, 'Accept': 'application/json']
        try {
            switch (request) {
                case RequestMethod.GET:
                    response = client.get(path: url, headers: requestHeaders, requestContentType: JSON)
                    break
                case RequestMethod.POST:
                    response = client.post(path: url, headers: requestHeaders, body: jsonData, requestContentType: JSON)
                    break
                case RequestMethod.PUT:
                    break
            }
        } catch (groovyx.net.http.HttpResponseException ex) {
            ex.printStackTrace()
            return null
        } catch (java.net.ConnectException ex) {
            ex.printStackTrace()
            return null
        }
        return response
    }





}