import com.amazonaws.AmazonClientException
import com.amazonaws.AmazonServiceException
import com.amazonaws.auth.BasicAWSCredentials
import com.amazonaws.services.s3.AmazonS3
import com.amazonaws.services.s3.transfer.TransferManager

$[/myProject/procedure_helpers/preamble]

//get credentials from commander
ElectricCommander commander = new ElectricCommander();

def bucketName = '$[bucketName]'.trim()
def folderName = '$[folderName]'.trim()

// Create bucket logic here

def credentials = new BasicAWSCredentials(commander.userName, commander.password)
def SUFFIX = "/"

// Create TransferManager
def tx = new TransferManager(credentials);

// Get S3 Client
AmazonS3 s3 = tx.getAmazonS3Client();

println("Creating folder " + folderName);

try {
    if (bucketName.length() == 0) {
        println("Error : Bucket name is empty");
        return
    }

    if (folderName.length() == 0) {
        println("Error : Folder name is empty");
        return
    }

    if (!s3.doesBucketExist(bucketName)) {
        println("Error : Bucket " + bucketName + " not present");
        return
    }
    // create meta-data for your folder and set content-length to 0
    ObjectMetadata metadata = new ObjectMetadata();
    metadata.setContentLength(0)
    // create empty content
    InputStream emptyContent = new ByteArrayInputStream(new byte[0])
    // create a PutObjectRequest passing the folder name suffixed by /
    PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName,
            folderName + SUFFIX, emptyContent, metadata)
    // send request to S3 to create folder
    s3.putObject(putObjectRequest)

    println("Folder " + folderName + " created successfully")

} catch (AmazonServiceException ase) {
    if (ase.statusCode.equals(409)) {
		println("Error : Folder " + folderName + " already present");
    }
    handleServiceException(ase)

} catch (AmazonClientException ace) {
    handleClientException(ace)
}


