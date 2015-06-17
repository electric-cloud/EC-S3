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
def propResult = '$[propResult]'.trim()

//validations
if(!isFilenameValid(downloadLocation)){
    println("Error : Can not write to " + downloadLocation + ".")
    return
}

if (bucketName.length() == 0) {
    println("Error : Bucket name is empty")
    return
}

if (downloadLocation.length() == 0) {
    println("Error : Download location is empty")
    return
}

if (key.length() == 0) {
    println("Error : Key is empty")
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
    def tx = new TransferManager(credentials)

    // Get S3 Client
    AmazonS3 s3 = tx.getAmazonS3Client();
    TransferManager tf = new TransferManager(s3)

    //Check the owner of the account just to verify if the access keys are valid
    def owner = s3.getS3AccountOwner()

    println "Downloading " + key + " to " + downloadLocation

    //check if the bucket is present and the user has rights
    if (!doesBucketExist(s3,bucketName)) {
        println("Error : Bucket " + bucketName + " not present")
        return
    }

    if( !isObjectPresent(s3,bucketName,key)) {
        println("Error : Object " + key + " not present or its not an object or you do not have permissions to download it.")
        return
    }

    //Download the object
    Download download = tf.download(new GetObjectRequest(bucketName, key), new File(downloadLocation + "/" + key))

    while (!download.isDone()) {
    	Thread.sleep(1000);
        if(!Double.isNaN(download.getProgress().getPercentTransferred())) {
            println(download.getProgress().getPercentTransferred() + "%")
        }
    }

    def downloadedFile
    if(downloadLocation.toString().endsWith("/"))
        downloadedFile = downloadLocation + key
    else
        downloadedFile = downloadLocation + "/" + key

    System.out.println(key + "  ==>  [" + downloadedFile + "]")
    commander.setProperty(propResult + "/" + key, downloadedFile)

    tf.shutdownNow()

    println "Downloaded " + key + " successfully"

} catch (InterruptedException e) {
    e.printStackTrace();
} catch (AmazonServiceException ase) {

    handleServiceException(ase)

} catch (AmazonClientException ace) {

    handleClientException(ace)

}
