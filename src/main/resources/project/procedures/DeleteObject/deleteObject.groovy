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
def key ='$[key]'.trim()

//validations
if (bucketName.length() == 0) {
    println("Error : Bucket name is empty")
    return
}

if (key.length() == 0) {
    println("Error : Key is empty")
    return
}

try {
    def credentials = new BasicAWSCredentials(commander.userName, commander.password)

    // Create TransferManager
    def tx = new TransferManager(credentials);

    // Get S3 Client
    AmazonS3 s3 = tx.getAmazonS3Client();
    s3.setEndpoint(commander.serviceUrl)

    //Check the owner of the account just to verify if the access keys are valid
    def owner = s3.getS3AccountOwner()

    //check if the bucket is present and the user has rights
    if (!doesBucketExist(s3,bucketName)) {
        println("Error : Bucket " + bucketName + " not present")
        return
    }
    if( !isObjectPresent(s3,bucketName,key)) {
        println("Error : Object " + key + " not present or its not an object or you do not have permissions to delete it.")
        return
    }
    println "Deleting object : " + key

    //Delete the object now
    s3.deleteObject(bucketName, key);

    if( !isObjectPresent(s3,bucketName,key)) {
        println "Object " + key + " deleted successfully"
    } else {
        println "Error : Error while deleting object " + key + "."
    }


} catch (InterruptedException e) {
    e.printStackTrace();
} catch (AmazonServiceException ase) {

    handleServiceException(ase)

} catch (AmazonClientException ace) {

    handleClientException(ace)

}

