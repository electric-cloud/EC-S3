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
import java.nio.file.Files
import java.nio.file.FileSystems
import groovy.io.FileType

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
def folderToUpload = commander.getCommanderProperty('folderToUpload')
folderToUpload = folderToUpload.replace('\\','/').trim()
def key ='$[key]'.trim()
def access_public = '$[access_public]'
def propResult = '$[propResult]'.trim()

//validations
if (bucketName.length() == 0) {
    println("Error : Bucket name is empty")
    return
}

if (folderToUpload.length() == 0) {
    println("Error : File to upload is empty")
    return
}

while(folderToUpload.endsWith("/")) {
    folderToUpload = folderToUpload.substring(0, folderToUpload.length() - 1);
}

def file = new File(folderToUpload)
if(!file.exists()){
    println "Error : Folder " + folderToUpload +" does not exists"
    return
}

if( !Files.isReadable(FileSystems.getDefault().getPath(file.getAbsolutePath())) ){
    println "Error : Can not open " + folderToUpload
    return
}

if( !file.isDirectory() ) {
    println "Error : " +  folderToUpload + " is not a directory."
    return
}

if(propResult.length() == 0) {
    propResult = "/myJob/S3Output"
}

while(propResult.endsWith("/")) {
    propResult = propResult.substring(0, propResult.length() - 1)
}

try {
    //get credentials from commander
    def credentials = new BasicAWSCredentials(commander.userName, commander.password)

    // Create TransferManager
    def tx = new TransferManager(credentials)

    // Get S3 Client
    AmazonS3 s3 = tx.getAmazonS3Client();
    println "service_url in uploadFolder ${commander?.serviceUrl}"
    s3.endpoint = commander?.serviceUrl?:"https://s3.amazonaws.com"
    TransferManager tf = new TransferManager(s3)

    //Check the owner of the account just to verify if the access keys are valid
    def owner = s3.getS3AccountOwner()

    //check if the bucket is present and the user has rights
    if (!doesBucketExist(s3,bucketName)) {
        println("Error : Bucket " + bucketName + " not present")
        return
    }

    def list = []

    file.eachFileRecurse(FileType.FILES, {fileName ->
        if(key) {
            list << key + "/" + fileName.getPath().substring(folderToUpload.toString().length() + 1).replace('\\', '/')
        } else {
            list << fileName.getPath().substring(folderToUpload.toString().length() + 1).replace('\\', '/')
        }
    })

    println "Uploading " + folderToUpload + " to " + bucketName

    //Upload the folder
    MultipleFileUpload objectUpload = tf.uploadDirectory(bucketName,
            key, file, true)

    while (!objectUpload.isDone()) {
        Thread.sleep(1000);
        if(!Double.isNaN(objectUpload.getProgress().getPercentTransferred())) {
            println(objectUpload.getProgress().getPercentTransferred() + "%")
        }
    }

    if(access_public == '1') {
        //Change the ACL of all the uploaded objects to Public Read

        String prefix = ""
        String delimiter = "/"

        if ((prefix.trim().length() != 0) && (!prefix.endsWith(delimiter))) {
            prefix += delimiter
        }

        ListObjectsRequest listObjectsRequest


        listObjectsRequest = new ListObjectsRequest()
                .withBucketName(bucketName).withPrefix(prefix)

        ObjectListing listing = s3.listObjects(listObjectsRequest)

        List<S3ObjectSummary> summaries = listing.getObjectSummaries()

        while (listing.isTruncated()) {
            listing = s3.listNextBatchOfObjects (listing)
            summaries.addAll (listing.getObjectSummaries())
        }

        for (S3ObjectSummary summary: summaries) {
            if(list.contains(summary.getKey())) {
                println "Changing the ACL to PublicRead for : " + summary.getKey()
                s3.setObjectAcl(bucketName, summary.getKey(), CannedAccessControlList.PublicRead)
            }
        }
    }

    //set the properties
    list.each {
        item ->
            def url = "https://" + bucketName + ".s3.amazonaws.com/" + item
            System.out.println(item + "  ==>  [" + url + "]")
            commander.setProperty(propResult + "/" + item, url)
    }

    tf.shutdownNow()

    println "Uploaded " + folderToUpload + " successfully"

} catch (InterruptedException e) {
    e.printStackTrace();
} catch (AmazonServiceException ase) {

    handleServiceException(ase)

} catch (AmazonClientException ace) {

    handleClientException(ace)

} catch(IOException ioex) {
    println("Error : " + ioex.getMessage())
}
