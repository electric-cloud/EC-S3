package project.procedures.CreateBucket.steps

import com.amazonaws.AmazonClientException
import com.amazonaws.AmazonServiceException
import com.amazonaws.auth.BasicAWSCredentials

import com.amazonaws.services.s3.AmazonS3
import com.amazonaws.services.s3.transfer.TransferManager
import groovyx.net.http.RESTClient;

@Grab('com.amazonaws:aws-java-sdk-s3:1.9.30')
@Grab(group = 'org.codehaus.groovy.modules.http-builder', module = 'http-builder', version = '0.7.1')



def commanderServer = 'https://' + System.getenv('COMMANDER_SERVER')
def commanderPort = System.getenv("COMMANDER_HTTPS_PORT")

def sessionId = System.getenv('COMMANDER_SESSIONID')

def client = new RESTClient(commanderServer + ":" + commanderPort)

client.ignoreSSLIssues()

def jobStepId = '$[/myJobStep/jobStepId]'

def resp = client.get(path: '/rest/v1.0/jobsSteps/' + jobStepId + '/credentials/$[config]', headers: ['Cookie': "sessionId=" + sessionId, 'Accept': 'application/json'])

assert resp.status == 200

def userName = resp.getData().credential.userName
def password = resp.getData().credential.password

println 'userName is: ' + userName

def bucketName = '$[bucketName]'

println 'bucket is: ' + bucketName

// Create bucket logic here

def credentials = new BasicAWSCredentials(userName,password)

// Create TransferManager
def tx = new TransferManager(credentials);

// Get S3 Client
AmazonS3 s3 = tx.getAmazonS3Client();

println("Creating " + bucketName);
try {
    s3.createBucket(bucketName);

} catch (AmazonServiceException ase) {
    if (ase.statusCode.equals(409)) {
        println("bucket already present")
    }
    handleServiceException(ase)

} catch (AmazonClientException ace) {
    handleClientException(ace)

}

println "Done"
