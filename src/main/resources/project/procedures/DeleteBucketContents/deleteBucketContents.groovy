import com.amazonaws.AmazonClientException
import com.amazonaws.AmazonServiceException
import com.amazonaws.auth.BasicAWSCredentials
import com.amazonaws.services.s3.AmazonS3
import com.amazonaws.services.s3.transfer.TransferManager

$[/myProject/procedure_helpers/preamble]

//get credentials from commander
ElectricCommander commander = new ElectricCommander();

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

if (!s3.doesBucketExist(bucketName)) {
    println("Error : Bucket " + bucketName + " not present");
    return
}

// Multi-object delete by specifying only keys (no version ID).
DeleteObjectsRequest multiObjectDeleteRequest = new DeleteObjectsRequest(
        bucketName).withQuiet(false)

//get keys
List<DeleteObjectsRequest.KeyVersion> keys = new ArrayList<DeleteObjectsRequest.KeyVersion>();
println("Listing objects of bucket " + bucketName)

ObjectListing objectListing = s3.listObjects(new ListObjectsRequest()
        .withBucketName(bucketName))
for (S3ObjectSummary objectSummary : objectListing.getObjectSummaries()) {
    keys.add(objectSummary.getKey())
}

// Create request that include only object key names.
List<DeleteObjectsRequest.KeyVersion> justKeys = new ArrayList<DeleteObjectsRequest.KeyVersion>();
for (String key : keys) {
    justKeys.add(new DeleteObjectsRequest.KeyVersion(key))
}

if(justKeys.size() == 0) {
    println("Bucket : " + bucketName + " is empty")
    return
}

multiObjectDeleteRequest.setKeys(justKeys)
// Execute DeleteObjects - Amazon S3 add delete marker for each object
// deletion. The objects no disappear from your bucket (verify).
DeleteObjectsResult delObjRes = null

try {

    delObjRes = s3.deleteObjects(multiObjectDeleteRequest);
    println("Successfully deleted " + delObjRes.getDeletedObjects().size() + " items")

} catch (MultiObjectDeleteException mode) {
    printDeleteResults(mode)
} catch (AmazonServiceException ase) {
    handleServiceException(ase)
} catch (AmazonClientException ace) {
    handleClientException(ace)

}


