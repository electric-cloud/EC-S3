import java.nio.file.Files
import java.nio.file.FileSystems

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
def fileToUpload = commander.getCommanderProperty('fileToUpload').trim()
fileToUpload = fileToUpload.replace('\\','/').trim()
def key ='$[key]'.trim()
def propResult = '$[propResult]'.trim()
def access_public = '$[access_public]'

//validations
if (bucketName.length() == 0) {
    println("Error : Bucket name is empty")
    return
}

if (fileToUpload.length() == 0) {
    println("Error : File to upload is empty")
    return
}

if (key.length() == 0) {
    println("Error : Key is empty")
    return
}

def file = new File(fileToUpload)
if(!file.exists()){
    println "Error : File " + fileToUpload +" does not exists"
    return
}

if( !Files.isReadable(FileSystems.getDefault().getPath(file.getAbsolutePath())) ){
    println "Error : Can not open " + fileToUpload
    return
}

if( !file.isFile() ) {
    println "Error : " +  fileToUpload + " is not a normal file."
    return
}

if(propResult.length() == 0) {
    propResult = "/myJob"
}

while(propResult.endsWith("/")) {
    propResult = propResult.substring(0, propResult.length() - 1)
}

try {
    //get credentials from commander
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
    
    println "Uploading " + key + " to " + bucketName

    //Create the request with Private or Public Read ACL
    PutObjectRequest por
    if(access_public == '1') {
        por = new PutObjectRequest(bucketName, key, file).withCannedAcl(CannedAccessControlList.PublicRead)
    } else {
        por = new PutObjectRequest(bucketName, key, file).withCannedAcl(CannedAccessControlList.Private)
    }

    //Upload the object
    Upload objectUpload = tf.upload(por)

    while (!objectUpload.isDone()) {
    	Thread.sleep(1000);
        if(!Double.isNaN(objectUpload.getProgress().getPercentTransferred())) {
            println(objectUpload.getProgress().getPercentTransferred() + "%")
        }
    }



    def url = "http://" + bucketName + ".s3.amazonaws.com/" + key
    System.out.println(key + "  ==>  [" + url + "]")
    commander.setProperty(propResult + "/" + key, url)

    println "Uploaded " + key + " successfully"
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
