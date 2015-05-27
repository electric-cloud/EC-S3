$[/myProject/procedure_helpers/preamble]

ElectricCommander commander = new ElectricCommander();

def bucketName = '$[bucketName]'
def fileToUpload = commander.getCommanderProperty('fileToUpload')
fileToUpload = fileToUpload.replace('\\','/')
def key ='$[key]'
def access_public = '$[access_public]'

//get credentials from commander
def credentials = new BasicAWSCredentials(commander.userName, commander.password)

// Create TransferManager
def tx = new TransferManager(credentials);

// Get S3 Client
AmazonS3 s3 = tx.getAmazonS3Client();
TransferManager tf = new TransferManager(s3);

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
        println(objectUpload.getProgress().getPercentTransferred() + "%");
    }

    tf.shutdownNow()

} catch (InterruptedException e) {
    e.printStackTrace();
} catch (AmazonServiceException ase) {

    handleServiceException(ase)

} catch (AmazonClientException ace) {

    handleClientException(ace)

} catch(IOException ioex) {
    println("Error : " + ioex.getMessage())
}

println "Uploaded " + key + " successfully"
