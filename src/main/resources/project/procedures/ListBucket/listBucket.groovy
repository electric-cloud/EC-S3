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
    println(e.getMessage())
    return
}
def propResult = '$[propResult]'.trim()

//validations
if(propResult.length() == 0) {
    propResult = "/myJob/S3Output"
}

while(propResult.endsWith("/")) {
    propResult = propResult.substring(0, propResult.length() - 1)
}
try {
    def credentials = new BasicAWSCredentials(commander.userName, commander.password)

    // Create TransferManager
    def tx = new TransferManager(credentials)

    // Get S3 Client
    AmazonS3 s3 = tx.getAmazonS3Client()

    //Check the owner of the account just to verify if the access keys are valid
    def owner = s3.getS3AccountOwner()

    def i=0

    println("Listing buckets")

    for (Bucket bucket : s3.listBuckets()) {
        println(bucket.getName())
        commander.setProperty(propResult + "/" + bucket.getName(), bucket.getName())
        i++
    }

    println("Listed " + i + " buckets")

} catch (AmazonServiceException ase) {
    handleServiceException(ase)

} catch (AmazonClientException ace) {
    handleClientException(ace)
}