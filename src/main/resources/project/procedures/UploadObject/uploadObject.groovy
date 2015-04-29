$[/myProject/procedure_helpers/preamble]

//get credentials from commander
ElectricCommander commander = new ElectricCommander();

def bucketName = '$[bucketName]'
def fileToUpload = '$[fileToUpload]'
def key ='$[key]'
// Create bucket logic here

def credentials = new BasicAWSCredentials(commander.userName, commander.password)

// Create TransferManager
def tx = new TransferManager(credentials);

// Get S3 Client
AmazonS3 s3 = tx.getAmazonS3Client();
TransferManager tf = new TransferManager(s3);

def file = new File(fileToUpload)
println "Uploading " + key + " to " + bucketName

try {
    PutObjectRequest por = new PutObjectRequest(bucketName, key, file).withCannedAcl(CannedAccessControlList.PublicRead)

    Upload objectUpload = tf.upload(por)
    while (!objectUpload.isDone()) {
    	Thread.sleep(1000);
        println(objectUpload.getProgress().getPercentTransferred() + "%");
    }

    tf.shutdownNow()

} catch (InterruptedException e) {
    e.printStackTrace();
} catch (AmazonServiceException ase) {

    handleServiceException(ase)

} catch (AmazonClientException ace) {

    handleClientException(ace)

}

commander.setProperty("BucketName", bucketName)
commander.setProperty("Key", key)

tf = null
s3 = null
commander = null

println "Done"
