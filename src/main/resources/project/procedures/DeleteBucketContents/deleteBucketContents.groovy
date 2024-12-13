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
import com.amazonaws.services.s3.model.ListObjectsRequest

$[/myProject/procedure_helpers/preamble]

ElectricCommander commander;
//get credentials from commander
try {
    commander = new ElectricCommander()
}catch(Exception e){
    println(e.getMessage());
    return
}

def bucketName = '$[bucketName]'.trim()

//validations


if (bucketName.length() == 0) {
    println("Error : Bucket name is empty")
    commander.setProperty("/myJob/summary", "Error : Bucket name is empty")
    return
}


try {
    def credentials = new BasicAWSCredentials(commander.userName, commander.password)

    // Create TransferManager
    def tx = new TransferManager(credentials)

    // Get S3 Client
    AmazonS3 s3 = tx.getAmazonS3Client()
    s3.endpoint = commander?.serviceUrl?:"https://s3.amazonaws.com"
    if (!doesBucketExist(s3, bucketName)) {
        println("Error : Bucket " + bucketName + " not present")
        commander.setProperty("/myJob/summary", "Error : Bucket " + bucketName + " not present")
        return
    }

    //Check the owner of the account just to verify if the access keys are valid
    def owner = s3.getS3AccountOwner()

    // Multi-object delete by specifying only keys (no version ID).
    DeleteObjectsRequest multiObjectDeleteRequest = new DeleteObjectsRequest(
            bucketName).withQuiet(false)

    //get keys
    List<DeleteObjectsRequest.KeyVersion> keys = new ArrayList<DeleteObjectsRequest.KeyVersion>()
    println("Listing objects of bucket " + bucketName)

    ObjectListing objectListing = s3.listObjects(new ListObjectsRequest()
            .withBucketName(bucketName))
    for (S3ObjectSummary objectSummary : objectListing.getObjectSummaries()) {
        keys.add(objectSummary.getKey())
    }

    // Create request that include only object key names.
    List<DeleteObjectsRequest.KeyVersion> justKeys = new ArrayList<DeleteObjectsRequest.KeyVersion>()
    for (String key : keys) {
        justKeys.add(new DeleteObjectsRequest.KeyVersion(key))
    }

    if(justKeys.size() == 0) {
        println("Bucket : " + bucketName + " is empty")
        commander.setProperty("/myJob/summary", "Bucket : " + bucketName + " is empty")
        return
    }

    multiObjectDeleteRequest.setKeys(justKeys)
    // Execute DeleteObjects - Amazon S3 add delete marker for each object
    // deletion. The objects no disappear from your bucket (verify).
    DeleteObjectsResult delObjRes = null

    delObjRes = s3.deleteObjects(multiObjectDeleteRequest);
    println("Successfully deleted " + delObjRes.getDeletedObjects().size() + " items")

} catch (MultiObjectDeleteException mode) {
    commander.setProperty("/myJob/summary", mode.toString())
    printDeleteResults(mode)
} catch (AmazonServiceException ase) {
    commander.setProperty("/myJob/summary", ase.toString())
    handleServiceException(ase)
} catch (AmazonClientException ace) {
    commander.setProperty("/myJob/summary", ace.toString())
    handleClientException(ace)

}
