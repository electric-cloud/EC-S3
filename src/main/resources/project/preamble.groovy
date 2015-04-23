import com.amazonaws.AmazonClientException
import com.amazonaws.AmazonServiceException
import com.amazonaws.auth.BasicAWSCredentials

import com.amazonaws.services.s3.AmazonS3
import com.amazonaws.services.s3.transfer.TransferManager
import groovyx.net.http.RESTClient;

@Grab('com.amazonaws:aws-java-sdk-s3:1.9.30')
@Grab(group = 'org.codehaus.groovy.modules.http-builder', module = 'http-builder', version = '0.7.1')



public class S3Credentials {
    def commanderServer = 'https://' + System.getenv('COMMANDER_SERVER')
    def commanderPort = System.getenv("COMMANDER_HTTPS_PORT")

    def sessionId = System.getenv('COMMANDER_SESSIONID')

    def client = new RESTClient(commanderServer + ":" + commanderPort);

    def userName
    def password

    def jobStepId = '$[/myJobStep/jobStepId]'

    S3Credentials() {
        println 'Inside Constructor'
        client.ignoreSSLIssues()
        def resp = client.get(path: '/rest/v1.0/jobsSteps/' + jobStepId + '/credentials/$[config]', headers: ['Cookie': "sessionId=" + sessionId, 'Accept': 'application/json'])
        assert resp.status == 200

        userName = resp.getData().credential.userName
        password = resp.getData().credential.password

    }

}