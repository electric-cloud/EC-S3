$[/myProject/procedure_helpers/preamble]

//get credentials from commander
ElectricCommander commander = new ElectricCommander();

def bucketName = '$[bucketName]'

// Create bucket logic here

def credentials = new BasicAWSCredentials(commander.userName, commander.password)

// Create TransferManager
def tx = new TransferManager(credentials);

// Get S3 Client
AmazonS3 s3 = tx.getAmazonS3Client();

if (s3.doesBucketExist(bucketName)) {
	println("Error : Bucket " + bucketName + " already present");
	return
}

println("Creating bucket " + bucketName);

try {
    s3.createBucket(bucketName);

} catch (AmazonServiceException ase) {
    if (ase.statusCode.equals(409)) {
		println("Error : Bucket " + bucketName + " already present");
    }
    handleServiceException(ase)

} catch (AmazonClientException ace) {
    handleClientException(ace)

}

commander.setProperty("BucketName", bucketName)

if (s3.doesBucketExist(bucketName)) {
	println("Bucket " + bucketName + " created successfully");
}

