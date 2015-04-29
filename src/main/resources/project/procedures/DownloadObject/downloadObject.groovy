$[/myProject/procedure_helpers/preamble]

//get credentials from commander
ElectricCommander commander = new ElectricCommander();

def bucketName = '$[bucketName]'
def downloadLocation = '$[downloadLocation]'
def key ='$[Key]'
// Create bucket logic here

def credentials = new BasicAWSCredentials(commander.userName, commander.password)

// Create TransferManager
def tx = new TransferManager(credentials);

// Get S3 Client
AmazonS3 s3 = tx.getAmazonS3Client();
TransferManager tf = new TransferManager(s3);

println "Downloading " + key + " to " + downloadLocation

try {
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

commander.setProperty("downloadLocation", downloadLocation)
commander.setProperty("Key", key)

tf = null
s3 = null
commander = null

println "Done"
