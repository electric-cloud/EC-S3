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
def propResult = '$[propResult]'.trim()

//validations
if (bucketName.length() == 0) {
    println("Error : Bucket name is empty");
    return
}

if(propResult.length() == 0) {
    propResult = "/myJob/S3Output"
}

while(propResult.endsWith("/")) {
    propResult = propResult.substring(0, propResult.length() - 1)
}

try {
    def credentials = new BasicAWSCredentials(commander.userName, commander.password)

    // Create TransferManager
    def tx = new TransferManager(credentials);

    // Get S3 Client
    AmazonS3 s3 = tx.getAmazonS3Client();

    //Check the owner of the account just to verify if the access keys are valid
    def owner = s3.getS3AccountOwner()

    //check if the bucket is present and the user has rights
    if (!doesBucketExist(s3,bucketName)) {
        println("Error : Bucket " + bucketName + " not present")
        return
    }

    println("Changing website hosting for bucket " + bucketName)

    if(website_hosting == '1') {
        //Enable website hosting
        if(errorDoc.length() ==0) {
            s3.setBucketWebsiteConfiguration(bucketName, new BucketWebsiteConfiguration(indexDoc))
        } else {
            s3.setBucketWebsiteConfiguration(bucketName,
                    new BucketWebsiteConfiguration(indexDoc, errorDoc))
        }

        def url = "http://" + bucketName + ".s3-website-us-east-1.amazonaws.com"

        println(bucketName + "  ==>  [" + url + "]")
        commander.setProperty(propResult + "/website_" + bucketName, url)
    } else {
        // Delete website hosting.
        s3.deleteBucketWebsiteConfiguration(bucketName)
    }

    println("Website on " + bucketName + " changed successfully")

} catch (AmazonServiceException ase) {
    handleServiceException(ase)

} catch (AmazonClientException ace) {
    handleClientException(ace)

}


