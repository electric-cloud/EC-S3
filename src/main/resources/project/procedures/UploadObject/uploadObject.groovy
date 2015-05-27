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
def fileToUpload = commander.getCommanderProperty('fileToUpload').trim()
fileToUpload = fileToUpload.replace('\\','/').trim()
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

if (key.length() == 0) {
    println("Error : Key is empty");
    return
}

try {
    def file = new File(fileToUpload)
    if(!file.exists()){
       println "Error : File " + fileToUpload +" does not exists"
       return
    }
    
    println "Uploading " + key + " to " + bucketName

    PutObjectRequest por
    if(access_public == '1') {
        por = new PutObjectRequest(bucketName, key, file).withCannedAcl(CannedAccessControlList.PublicRead)
    } else {
        por = new PutObjectRequest(bucketName, key, file).withCannedAcl(CannedAccessControlList.Private)
    }

    Upload objectUpload = tf.upload(por)

    while (!objectUpload.isDone()) {
    	Thread.sleep(1000);
        if(!Double.isNaN(objectUpload.getProgress().getPercentTransferred())) {
            println(objectUpload.getProgress().getPercentTransferred() + "%");
        }
    }

    tf.shutdownNow()

    println "Uploaded " + key + " successfully"

} catch (InterruptedException e) {
    e.printStackTrace();
} catch (AmazonServiceException ase) {

    handleServiceException(ase)

} catch (AmazonClientException ace) {

    handleClientException(ace)

} catch(IOException ioex) {
    println("Error : " + ioex.getMessage())
}
