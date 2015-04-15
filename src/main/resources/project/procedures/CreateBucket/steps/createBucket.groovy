package project.procedures.CreateBucket.steps

import com.amazonaws.auth.BasicAWSCredentials

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ListObjectsRequest;
import com.amazonaws.services.s3.model.ObjectListing;

import com.amazonaws.services.s3.model.S3ObjectSummary
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


def bucketName = '$[bucketName]'

// Create bucket logic here
