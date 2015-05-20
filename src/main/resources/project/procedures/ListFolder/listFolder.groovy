import com.amazonaws.AmazonClientException
import com.amazonaws.AmazonServiceException
import com.amazonaws.auth.BasicAWSCredentials
import com.amazonaws.services.s3.AmazonS3
import com.amazonaws.services.s3.transfer.TransferManager

$[/myProject/procedure_helpers/preamble]

def bucketName = '$[bucketName]'.trim()
def prefix = '$[folderName]'.trim()
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
    //do the validations
    if (bucketName.length() == 0) {
        println("Error : Bucket name is empty");
        return
    }

    if (!doesBucketExist(s3,bucketName)) {
        println("Error : Bucket " + bucketName + " not present");
        return
    }

    String delimiter = "/"

    if ((prefix.trim().length() != 0) && (!prefix.endsWith(delimiter))) {
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

    ObjectListing listing = s3.listObjects(listObjectsRequest)

    List<S3ObjectSummary> summaries = listing.getObjectSummaries()

    while (listing.isTruncated()) {
        listing = s3.listNextBatchOfObjects (listing)
        summaries.addAll (listing.getObjectSummaries())
    }

    for (S3ObjectSummary summary: summaries) {
        System.out.println(summary.getKey())
        i++
    }

    println("Listed " + i + " objects")

} catch (AmazonServiceException ase) {
    handleServiceException(ase)

} catch (AmazonClientException ace) {
    handleClientException(ace)
}
