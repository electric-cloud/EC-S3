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
    commander = new ElectricCommander();
}catch(Exception e){
    println(e.getMessage());
    return
}

def bucketName = '$[bucketName]'.trim()
def website_hosting = '$[website_hosting]'
def indexDoc = '$[indexDoc]'.trim()
def errorDoc = '$[errorDoc]'.trim()
def propResult = '$[propResult]'.trim()

//validations
if (bucketName.length() == 0) {
    println("Error : Bucket name is empty");
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
    def tx = new TransferManager(credentials);

    // Get S3 Client
    AmazonS3 s3 = tx.getAmazonS3Client();

    //Check the owner of the account just to verify if the access keys are valid
    def owner = s3.getS3AccountOwner()

    //check if the bucket is present and the user has rights
    if (!doesBucketExist(s3,bucketName)) {
        println("Error : Bucket " + bucketName + " not present")
        return
    }

    println("Changing website hosting for bucket " + bucketName)

    if(website_hosting == '1') {
        //Enable website hosting
        if(errorDoc.length() ==0) {
            s3.setBucketWebsiteConfiguration(bucketName, new BucketWebsiteConfiguration(indexDoc))
        } else {
            s3.setBucketWebsiteConfiguration(bucketName,
                    new BucketWebsiteConfiguration(indexDoc, errorDoc))
        }

        def url
        switch(Region.fromValue(s3.getBucketLocation(new GetBucketLocationRequest(bucketName)))) {
            case Region.US_Standard:
                url = "http://" + bucketName + ".s3-website-us-east-1.amazonaws.com"
                break
            case Region.US_West:
                url = "http://" + bucketName + ".s3-website-us-west-1.amazonaws.com"
                break
            case Region.US_West_2:
                url = "http://" + bucketName + ".s3-website-us-west-2.amazonaws.com"
                break
            case Region.US_GovCloud:
                url = "http://" + bucketName + ".s3-website-us-gov-west-1.amazonaws.com"
                break
            case Region.EU_Ireland:
                url = "http://" + bucketName + ".s3-website-eu-west-1.amazonaws.com"
                break
            case Region.US_Frankfurt:
                url = "http://" + bucketName + ".s3-website-eu-central-1.amazonaws.com"
                break
            case Region.AP_Singapore:
                url = "http://" + bucketName + ".s3-website-ap-southeast-1.amazonaws.com"
                break
            case Region.AP_Sydney:
                url = "http://" + bucketName + ".s3-website-ap-southeast-2.amazonaws.com"
                break
            case Region.AP_Tokyo:
                url = "http://" + bucketName + ".s3-website-ap-northeast-1.amazonaws.com"
                break
            case Region.SA_SaoPaulo:
                url = "http://" + bucketName + ".s3-website-sa-east-1.amazonaws.com"
                break
            case Region.CN_Beijing:
                url = "http://" + bucketName + ".s3-website-cn-north-1.amazonaws.com"
                break
        }

        println(bucketName + "  ==>  [" + url + "]")
        commander.setProperty(propResult + "/website_" + bucketName, url)
    } else {
        // Delete website hosting.
        s3.deleteBucketWebsiteConfiguration(bucketName)
    }

    println("Website on " + bucketName + " changed successfully")

} catch (AmazonServiceException ase) {
    handleServiceException(ase)

} catch (AmazonClientException ace) {
    handleClientException(ace)

}


