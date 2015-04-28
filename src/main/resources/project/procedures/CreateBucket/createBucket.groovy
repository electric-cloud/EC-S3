$[/myProject/procedure_helpers/preamble]

//get credentials from commander
ElectricCommander commander = new ElectricCommander();

println 'userName is: ' + commander.userName

def bucketName = '$[bucketName]'

println 'bucket is: ' + bucketName


// Create bucket logic here

def credentials = new BasicAWSCredentials(commander.userName, commander.password)

// Create TransferManager
def tx = new TransferManager(credentials);

// Get S3 Client
AmazonS3 s3 = tx.getAmazonS3Client();

println("Creating " + bucketName);
try {
    s3.createBucket(bucketName);

} catch (AmazonServiceException ase) {
    if (ase.statusCode.equals(409)) {
        println("bucket already present")
    }
    handleServiceException(ase)

} catch (AmazonClientException ace) {
    handleClientException(ace)

}

commander.SetProperty("BucketName", bucketName)

println "Done"
