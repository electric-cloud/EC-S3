import com.amazonaws.AmazonClientException
import com.amazonaws.AmazonServiceException
import com.amazonaws.auth.BasicAWSCredentials
import com.amazonaws.services.s3.AmazonS3
import com.amazonaws.services.s3.transfer.TransferManager

$[/myProject/procedure_helpers/preamble]

def bucketName = '$[bucketName]'
def prefix = '$[folderName]'
def include_sub_folder = '$[include_sub_folder]'

//get credentials from commander
ElectricCommander commander = new ElectricCommander();

def credentials = new BasicAWSCredentials(commander.userName, commander.password)

// Create TransferManager
def tx = new TransferManager(credentials);

// Get S3 Client
AmazonS3 s3 = tx.getAmazonS3Client();
def i=0

println("Listing objects under folder")

try {
	
    String delimiter = "/"
    if (!prefix.endsWith(delimiter)) {
        prefix += delimiter
    }

	ListObjectsRequest listObjectsRequest

	if(include_sub_folder == '1') {
	    listObjectsRequest = new ListObjectsRequest()
	            .withBucketName(bucketName).withPrefix(prefix)
	} else {
	    listObjectsRequest = new ListObjectsRequest()
	            .withBucketName(bucketName).withPrefix(prefix)
	            .withDelimiter(delimiter)
	}

    ObjectListing objects = s3.listObjects(listObjectsRequest)
	for (S3ObjectSummary summary: objects.getObjectSummaries()) {
	    System.out.println(summary.getKey())
	    i++
	}

} catch (AmazonServiceException ase) {
    handleServiceException(ase)

} catch (AmazonClientException ace) {
    handleClientException(ace)
}

println("Listed " + i + " objects")
