$[/myProject/procedure_helpers/preamble]
ElectricCommander commander;
//get credentials from commander
try {
    commander = new ElectricCommander()
}catch(Exception e){
    println(e.getMessage())
    return
}

def bucketName = '$[bucketName]'.trim()
def downloadLocation = commander.getCommanderProperty('downloadLocation')
downloadLocation = downloadLocation.replace('\\','/').trim()
def key ='$[key]'.trim()

//validations
if (bucketName.length() == 0) {
    println("Error : Bucket name is empty")
    return
}

if (downloadLocation.length() == 0) {
    println("Error : Download location is empty")
    return
}

if(!isFilenameValid(downloadLocation)){
    println("Error : Download location is invalid.")
    return
}

try {
    def credentials = new BasicAWSCredentials(commander.userName, commander.password)

    // Create TransferManager
    def tx = new TransferManager(credentials);

    // Get S3 Client
    AmazonS3 s3 = tx.getAmazonS3Client();
    TransferManager tf = new TransferManager(s3);

    //Check the owner of the account just to verify if the access keys are valid
    def owner = s3.getS3AccountOwner()

    //check if the bucket is present and the user has rights
    if (!doesBucketExist(s3,bucketName)) {
        println("Error : Bucket " + bucketName + " not present")
        return
    }

    println "Downloading " + key + " to " + downloadLocation

    //Now download the contents
    file = new File(downloadLocation)
    MultipleFileDownload download = tf.downloadDirectory(bucketName, key, file)

    while (!download.isDone()) {
    	Thread.sleep(1000);
        if(!Double.isNaN(download.getProgress().getPercentTransferred())) {
            println(download.getProgress().getPercentTransferred() + "%")
        }
    }

    tf.shutdownNow()

    println "Downloaded " + key + " successfully"

} catch (InterruptedException e) {
    e.printStackTrace();
} catch (AmazonServiceException ase) {

    handleServiceException(ase)

} catch (AmazonClientException ace) {

    handleClientException(ace)

}
