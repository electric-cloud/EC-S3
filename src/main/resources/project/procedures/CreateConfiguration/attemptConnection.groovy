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
    commander = new ElectricCommander(false);
}catch(Exception e){
    println(e.getMessage());
    return
}

try {
    println("Checking if the access keys are valid")

    def credentials = new BasicAWSCredentials(commander.userName, commander.password)

    // Create TransferManager
    def tx = new TransferManager(credentials)

    // Get S3 Client
    AmazonS3 s3 = tx.getAmazonS3Client()
    println "service_url in attemptConnection ${commander?.serviceUrl}"
    s3.endpoint = commander?.serviceUrl?:"https://s3.amazonaws.com"
    //Check the owner of the account just to verify if the access keys are valid
    def owner = s3.getS3AccountOwner()
    println("Id: "+owner.id)
    List<Bucket> buckets = s3.listBuckets()
    println("{S3} buckets are:")
    for (Bucket b : buckets) {
        println("* " + b.getName())
    }

} catch (AmazonServiceException ase) {
    handleServiceException(ase)

} catch (AmazonClientException ace) {
    handleClientException(ace)
}
