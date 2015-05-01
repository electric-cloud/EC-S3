import com.amazonaws.AmazonClientException
import com.amazonaws.AmazonServiceException
import com.amazonaws.auth.BasicAWSCredentials
import com.amazonaws.services.s3.AmazonS3
import com.amazonaws.services.s3.transfer.TransferManager

$[/myProject/procedure_helpers/preamble]

//get credentials from commander
ElectricCommander commander = new ElectricCommander();

def credentials = new BasicAWSCredentials(commander.userName, commander.password)

// Create TransferManager
def tx = new TransferManager(credentials);

// Get S3 Client
AmazonS3 s3 = tx.getAmazonS3Client();
def i=0

println("Listing buckets")

try {
	
    for (Bucket bucket : s3.listBuckets()) {
        println(bucket.getName())
        i++
    }

} catch (AmazonServiceException ase) {
    handleServiceException(ase)

} catch (AmazonClientException ace) {
    handleClientException(ace)
}

println("Listed " + i + " buckets")
