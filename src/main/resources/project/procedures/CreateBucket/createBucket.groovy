import com.amazonaws.AmazonClientException
import com.amazonaws.AmazonServiceException
import com.amazonaws.auth.BasicAWSCredentials
import com.amazonaws.services.s3.AmazonS3
import com.amazonaws.services.s3.transfer.TransferManager

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

// Create bucket logic here

def credentials = new BasicAWSCredentials(commander.userName, commander.password)

// Create TransferManager
def tx = new TransferManager(credentials);

// Get S3 Client
AmazonS3 s3 = tx.getAmazonS3Client();

if (bucketName.length() == 0) {
    println("Error : Bucket name is empty");
    return
}
if (s3.doesBucketExist(bucketName)) {
	println("Error : Bucket " + bucketName + " already present");
	return
}

println("Creating bucket " + bucketName);

try {
    s3.createBucket(bucketName);

    if (s3.doesBucketExist(bucketName)) {
        println("Bucket " + bucketName + " created successfully");
    }

} catch (AmazonServiceException ase) {
    if (ase.statusCode.equals(409)) {
		println("Error : Bucket " + bucketName + " already present");
    }
    handleServiceException(ase)

} catch (AmazonClientException ace) {
    handleClientException(ace)

}


