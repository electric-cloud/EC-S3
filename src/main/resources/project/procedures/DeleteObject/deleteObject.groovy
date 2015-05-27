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
def key ='$[key]'.trim()
// Create bucket logic here

def credentials = new BasicAWSCredentials(commander.userName, commander.password)

// Create TransferManager
def tx = new TransferManager(credentials);

// Get S3 Client
AmazonS3 s3 = tx.getAmazonS3Client();

println "Deleting object : " + key

if (bucketName.length() == 0) {
    println("Error : Bucket name is empty");
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

    s3.deleteObject(bucketName, key);

    println "Object " + key + " deleted successfully"

} catch (InterruptedException e) {
    e.printStackTrace();
} catch (AmazonServiceException ase) {

    handleServiceException(ase)

} catch (AmazonClientException ace) {

    handleClientException(ace)

}

