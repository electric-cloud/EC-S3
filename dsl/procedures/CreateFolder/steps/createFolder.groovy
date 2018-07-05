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
    println(e.getMessage())
    return
}
def bucketName = '$[bucketName]'.trim()
def folderName = '$[folderName]'.trim()
def SUFFIX = "/"

//validations
if (bucketName.length() == 0) {
    println("Error : Bucket name is empty")
    return
}

if (folderName.length() == 0) {
    println("Error : Folder name is empty")
    return
}

try {
    def credentials = new BasicAWSCredentials(commander.userName, commander.password)

    // Create TransferManager
    def tx = new TransferManager(credentials)

    // Get S3 Client
    AmazonS3 s3 = tx.getAmazonS3Client()

    //Check the owner of the account just to verify if the access keys are valid
    def owner = s3.getS3AccountOwner()

    //check if the bucket is present and the user has rights
    if (!doesBucketExist(s3,bucketName)) {
        println("Error : Bucket " + bucketName + " not present");
        return
    }
    // create meta-data for your folder and set content-length to 0
    ObjectMetadata metadata = new ObjectMetadata()
    metadata.setContentLength(0)
    // create empty content
    InputStream emptyContent = new ByteArrayInputStream(new byte[0])
    // create a PutObjectRequest passing the folder name suffixed by /
    PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName,
            folderName + SUFFIX, emptyContent, metadata)

    println("Creating folder " + folderName)

    // send request to S3 to create folder
    s3.putObject(putObjectRequest)

    println("Folder " + folderName + " created successfully")

} catch (AmazonServiceException ase) {
    if (ase.statusCode.equals(409)) {
		println("Error : Folder " + folderName + " already present")
    }
    handleServiceException(ase)

} catch (AmazonClientException ace) {
    handleClientException(ace)
}


