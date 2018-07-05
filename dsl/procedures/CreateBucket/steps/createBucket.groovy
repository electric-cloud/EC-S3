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
    return
}

try {

    def credentials = new BasicAWSCredentials(commander.userName, commander.password)

    // Create TransferManager
    def tx = new TransferManager(credentials);

    // Get S3 Client
    AmazonS3 s3 = tx.getAmazonS3Client();

    //Check the owner of the account just to verify if the access keys are valid
    def owner = s3.getS3AccountOwner()

    //check if the bucket is present
    if (doesBucketExist(s3,bucketName)) {
        println("Error : Bucket " + bucketName + " already present")
        return
    }

    // Create bucket logic here
    println("Creating bucket " + bucketName)

    //Create the bucket
    s3.createBucket(bucketName)

    //Verify again if the bucket is created
    if (s3.doesBucketExist(bucketName)) {
        println("Bucket " + bucketName + " created successfully")
        commander.setSummary("Bucket $bucketName created successfully")
    }

} catch (AmazonServiceException ase) {
    if (ase.statusCode.equals(409)) {
		println("Error : Bucket " + bucketName + " already present")
    }
    handleServiceException(ase)

} catch (AmazonClientException ace) {
    handleClientException(ace)

}


