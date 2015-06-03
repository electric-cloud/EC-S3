import com.amazonaws.AmazonClientException
import com.amazonaws.AmazonServiceException
import com.amazonaws.auth.BasicAWSCredentials
import com.amazonaws.services.s3.AmazonS3
import com.amazonaws.services.s3.transfer.TransferManager

$[/myProject/procedure_helpers/preamble]

ElectricCommander commander;
//get credentials from commander
try {
    commander = new ElectricCommander()
}catch(Exception e){
    println(e.getMessage());
    return
}

def bucketName = '$[bucketName]'.trim()

//validations
if (bucketName.length() == 0) {
    println("Error : Bucket name is empty")
    return
}

try {

    def credentials = new BasicAWSCredentials(commander.userName, commander.password)

    // Create TransferManager
    def tx = new TransferManager(credentials);

    // Get S3 Client
    AmazonS3 s3 = tx.getAmazonS3Client();

    //Check the owner of the account just to verify if the access keys are valid
    def owner = s3.getS3AccountOwner()

    //check if the bucket is present
    if (doesBucketExist(s3,bucketName)) {
        println("Error : Bucket " + bucketName + " already present")
        return
    }

    // Create bucket logic here
    println("Creating bucket " + bucketName)

    //Create the bucket
    s3.createBucket(bucketName)

    //Verify again if the bucket is created
    if (s3.doesBucketExist(bucketName)) {
        println("Bucket " + bucketName + " created successfully")
    }

} catch (AmazonServiceException ase) {
    if (ase.statusCode.equals(409)) {
		println("Error : Bucket " + bucketName + " already present")
    }
    handleServiceException(ase)

} catch (AmazonClientException ace) {
    handleClientException(ace)

}


