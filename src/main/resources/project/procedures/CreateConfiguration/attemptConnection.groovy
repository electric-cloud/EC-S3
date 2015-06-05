/**
 * Created by anand on 03-06-2015.
 */
import com.amazonaws.AmazonClientException
import com.amazonaws.AmazonServiceException
import com.amazonaws.auth.BasicAWSCredentials
import com.amazonaws.services.s3.AmazonS3
import com.amazonaws.services.s3.transfer.TransferManager

$[/myProject/procedure_helpers/preamble]

ElectricCommander commander;
//get credentials from commander
try {
    commander = new ElectricCommander(false);
}catch(Exception e){
    println(e.getMessage());
    return
}

try {
    println("Checking if the access keys are valid")

    def credentials = new BasicAWSCredentials(commander.userName, commander.password)

    // Create TransferManager
    def tx = new TransferManager(credentials)

    // Get S3 Client
    AmazonS3 s3 = tx.getAmazonS3Client()

    //Check the owner of the account just to verify if the access keys are valid
    def owner = s3.getS3AccountOwner()

} catch (AmazonServiceException ase) {
    handleServiceException(ase)

} catch (AmazonClientException ace) {
    handleClientException(ace)
}