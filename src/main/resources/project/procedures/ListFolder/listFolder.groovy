import com.amazonaws.AmazonClientException
import com.amazonaws.AmazonServiceException
import com.amazonaws.auth.BasicAWSCredentials
import com.amazonaws.services.s3.AmazonS3
import com.amazonaws.services.s3.transfer.TransferManager

$[/myProject/procedure_helpers/preamble]

def bucketName = '$[bucketName]'.trim()
def prefix = '$[folderName]'.trim()
def include_sub_folder = '$[include_sub_folder]'

ElectricCommander commander;
//get credentials from commander
try {
    commander = new ElectricCommander()
}catch(Exception e){
    println(e.getMessage())
    return
}

def i=0

//validations
if (bucketName.length() == 0) {
    println("Error : Bucket name is empty")
    return
}

try {
    def credentials = new BasicAWSCredentials(commander.userName, commander.password)

    // Create TransferManager
    def tx = new TransferManager(credentials)

    // Get S3 Client
    AmazonS3 s3 = tx.getAmazonS3Client()

    //Check the owner of the account just to verify if the access keys are valid
    def owner = s3.getS3AccountOwner()

    //check if the bucket is present and the user has rights
    if (!doesBucketExist(s3,bucketName)) {
        println("Error : Bucket " + bucketName + " not present")
        return
    }

    String delimiter = "/"

    if ((prefix.trim().length() != 0) && (!prefix.endsWith(delimiter))) {
        prefix += delimiter
    }

	ListObjectsRequest listObjectsRequest

    //Format the request based on what we want. With or without subfolders
	if(include_sub_folder == '1') {
	    listObjectsRequest = new ListObjectsRequest()
	            .withBucketName(bucketName).withPrefix(prefix)
	} else {
	    listObjectsRequest = new ListObjectsRequest()
	            .withBucketName(bucketName).withPrefix(prefix)
	            .withDelimiter(delimiter)
	}

    println("Listing objects under folder")

    //Create the list now
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
