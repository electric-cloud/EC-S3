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
def fileToUpload = '$[folderToUpload]'.trim()
def key ='$[key]'.trim()
def access_public = '$[access_public]'

//get credentials from commander
def credentials = new BasicAWSCredentials(commander.userName, commander.password)

// Create TransferManager
def tx = new TransferManager(credentials);

// Get S3 Client
AmazonS3 s3 = tx.getAmazonS3Client();
TransferManager tf = new TransferManager(s3);

if (bucketName.length() == 0) {
    println("Error : Bucket name is empty");
    return
}

if (fileToUpload.length() == 0) {
    println("Error : File to upload is empty");
    return
}

try {
    def file = new File(fileToUpload)
    if(!file.exists()){
       println "Error : Folder " + fileToUpload +" does not exists"
       return
    }
    
    println "Uploading " + fileToUpload + " to " + bucketName

	MultipleFileUpload objectUpload = tf.uploadDirectory(bucketName,
            key, file, true);
			
    while (!objectUpload.isDone()) {
    	Thread.sleep(1000);
        println(objectUpload.getProgress().getPercentTransferred() + "%");
    }

	if(access_public == '1') {
	
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
			println "Changing the ACL to PublicRead for : " + summary.getKey()
			s3.setObjectAcl(bucketName, summary.getKey(), CannedAccessControlList.PublicRead);
		}
	}
	
    tf.shutdownNow()

    println "Uploaded " + fileToUpload + " successfully"

} catch (InterruptedException e) {
    e.printStackTrace();
} catch (AmazonServiceException ase) {

    handleServiceException(ase)

} catch (AmazonClientException ace) {

    handleClientException(ace)

} catch(IOException ioex) {
    println("Error : " + ioex.getMessage())
}
