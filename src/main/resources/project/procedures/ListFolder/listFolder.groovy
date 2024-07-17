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

import com.amazonaws.AmazonClientException
import com.amazonaws.AmazonServiceException
import com.amazonaws.auth.BasicAWSCredentials
import com.amazonaws.services.s3.AmazonS3
import com.amazonaws.services.s3.transfer.TransferManager

$[/myProject/procedure_helpers/preamble]

def bucketName = '$[bucketName]'.trim()
def prefix = '$[folderName]'.trim()
def include_sub_folder = '$[include_sub_folder]'
def propResult = '$[propResult]'.trim()
ElectricCommander commander;
//get credentials from commander
try {
    commander = new ElectricCommander()
}catch(Exception e){
    println(e.getMessage())
    return
}

def i=0

//validations
if (bucketName.length() == 0) {
    println("Error : Bucket name is empty")
    return
}

if(propResult.length() == 0) {
    propResult = "/myJob/S3Output"
}

while(propResult.endsWith("/")) {
    propResult = propResult.substring(0, propResult.length() - 1)
}

try {
    def credentials = new BasicAWSCredentials(commander.userName, commander.password)

    // Create TransferManager
    def tx = new TransferManager(credentials)

    // Get S3 Client
    AmazonS3 s3 = tx.getAmazonS3Client()
    s3.setEndpoint(commander.serviceUrl)

    //Check the owner of the account just to verify if the access keys are valid
    def owner = s3.getS3AccountOwner()

    //check if the bucket is present and the user has rights
    if (!doesBucketExist(s3,bucketName)) {
        println("Error : Bucket " + bucketName + " not present")
        return
    }

    String delimiter = "/"

    if ((prefix.trim().length() != 0) && (!prefix.endsWith(delimiter))) {
        prefix += delimiter
    }

	ListObjectsRequest listObjectsRequest

    //Format the request based on what we want. With or without subfolders
	if(include_sub_folder == '1') {
	    listObjectsRequest = new ListObjectsRequest()
	            .withBucketName(bucketName).withPrefix(prefix)
	} else {
	    listObjectsRequest = new ListObjectsRequest()
	            .withBucketName(bucketName).withPrefix(prefix)
	            .withDelimiter(delimiter)
	}

    println("Listing objects under folder")

    //Create the list now
    ObjectListing listing = s3.listObjects(listObjectsRequest)

    List<S3ObjectSummary> summaries = listing.getObjectSummaries()

    while (listing.isTruncated()) {
        listing = s3.listNextBatchOfObjects (listing)
        summaries.addAll (listing.getObjectSummaries())
    }

    for (S3ObjectSummary summary: summaries) {
        def url = "https://" + bucketName + ".s3.amazonaws.com/" + summary.getKey()
        System.out.println(summary.getKey() + "  ==>  [" + url + "]")
        commander.setProperty(propResult + "/" + commander.getPropertyName(summary.getKey()), url)
        i++
    }

    println("Listed " + i + " objects")

} catch (AmazonServiceException ase) {
    handleServiceException(ase)

} catch (AmazonClientException ace) {
    handleClientException(ace)
}
