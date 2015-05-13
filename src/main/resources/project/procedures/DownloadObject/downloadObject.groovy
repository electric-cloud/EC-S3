$[/myProject/procedure_helpers/preamble]

//get credentials from commander
ElectricCommander commander = new ElectricCommander();

def bucketName = '$[bucketName]'.trim()
def downloadLocation = '$[downloadLocation]'.trim()
def key ='$[key]'.trim()
// Create bucket logic here

def credentials = new BasicAWSCredentials(commander.userName, commander.password)

// Create TransferManager
def tx = new TransferManager(credentials);

// Get S3 Client
AmazonS3 s3 = tx.getAmazonS3Client();
TransferManager tf = new TransferManager(s3);

println "Downloading " + key + " to " + downloadLocation

if (bucketName.length() == 0) {
    println("Error : Bucket name is empty");
    return
}

if (downloadLocation.length() == 0) {
    println("Error : Download location is empty");
    return
}

if (key.length() == 0) {
    println("Error : Key is empty");
    return
}

try {
    if (!s3.doesBucketExist(bucketName)) {
        println("Error : Bucket " + bucketName + " not present");
        return
    }

    Download download = tf.download(new GetObjectRequest(bucketName, key), new File(downloadLocation + "/" + key));

    while (!download.isDone()) {
    	Thread.sleep(1000);
        println(download.getProgress().getPercentTransferred() + "%");
    }

    tf.shutdownNow()

} catch (InterruptedException e) {
    e.printStackTrace();
} catch (AmazonServiceException ase) {

    handleServiceException(ase)

} catch (AmazonClientException ace) {

    handleClientException(ace)

}

println "Downloaded " + key + " successfully"
