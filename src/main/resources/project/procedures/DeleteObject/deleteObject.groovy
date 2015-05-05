$[/myProject/procedure_helpers/preamble]

//get credentials from commander
ElectricCommander commander = new ElectricCommander();

def bucketName = '$[bucketName]'
def key ='$[key]'
// Create bucket logic here

def credentials = new BasicAWSCredentials(commander.userName, commander.password)

// Create TransferManager
def tx = new TransferManager(credentials);

// Get S3 Client
AmazonS3 s3 = tx.getAmazonS3Client();

println "Deleting object : " + key

try {
    s3.deleteObject(bucketName, key);

} catch (InterruptedException e) {
    e.printStackTrace();
} catch (AmazonServiceException ase) {

    handleServiceException(ase)

} catch (AmazonClientException ace) {

    handleClientException(ace)

}

println "Object " + key + " deleted successfully"

