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
def website_hosting = '$[website_hosting]'
def indexDoc = '$[indexDoc]'.trim()
def errorDoc = '$[errorDoc]'.trim()

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

println("Changing website hosting for bucket " + bucketName);

try {
    if(website_hosting == '1') {
        if(errorDoc.length() ==0) {
            s3.setBucketWebsiteConfiguration(bucketName, new BucketWebsiteConfiguration(indexDoc))
        } else {
            s3.setBucketWebsiteConfiguration(bucketName,
                    new BucketWebsiteConfiguration(indexDoc, errorDoc))
        }
    } else {
        // Delete website configuration.
        s3.deleteBucketWebsiteConfiguration(bucketName);
    }

    println("Website on " + bucketName + " changed successfully");

} catch (AmazonServiceException ase) {
    handleServiceException(ase)

} catch (AmazonClientException ace) {
    handleClientException(ace)

}


