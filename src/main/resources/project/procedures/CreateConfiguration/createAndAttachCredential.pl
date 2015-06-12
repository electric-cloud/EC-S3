##########################
# createAndAttachCredential.pl
##########################
use ElectricCommander;

use constant {
	SUCCESS => 0,
	ERROR   => 1,
};

## get an EC object
my $ec = new ElectricCommander();
$ec->abortOnError(0);

my $credName = "$[/myJob/config]";

my $xpath = $ec->getFullCredential("credential");
my $errors = $ec->checkAllErrors($xpath);
my $userName = $xpath->findvalue("//userName");
my $password = $xpath->findvalue("//password");

my $projName = "$[/myProject/projectName]";
print "Creating credential $credName in project $projName with user $userName\n";

# Create credential
$ec->deleteCredential($projName, $credName);
$xpath = $ec->createCredential($projName, $credName, $userName, $password);
$errors .= $ec->checkAllErrors($xpath);

# Give config the credential's real name
my $configPath = "/projects/$projName/s3_cfgs/$credName";
$xpath = $ec->setProperty($configPath . "/credential", $credName);
$errors .= $ec->checkAllErrors($xpath);

# Give job launcher full permissions on the credential
my $user = "$[/myJob/launchedByUser]";
$xpath = $ec->createAclEntry("user", $user,
    {projectName => $projName,
     credentialName => $credName,
     readPrivilege => allow,
     modifyPrivilege => allow,
     executePrivilege => allow,
     changePermissionsPrivilege => allow});
$errors .= $ec->checkAllErrors($xpath);

# Attach credential to steps that will need it
$xpath = $ec->attachCredential($projName, $credName,
    {procedureName => "CreateBucket",
     stepName => "createBucket"});
$errors .= $ec->checkAllErrors($xpath);

$xpath = $ec->attachCredential($projName, $credName,
    {procedureName => "ListBucket",
     stepName => "listBucket"});
$errors .= $ec->checkAllErrors($xpath);

$xpath = $ec->attachCredential($projName, $credName,
    {procedureName => "UploadFolder",
     stepName => "uploadFolder"});
$errors .= $ec->checkAllErrors($xpath);

$xpath = $ec->attachCredential($projName, $credName,
    {procedureName => "UploadObject",
     stepName => "uploadObject"});
$errors .= $ec->checkAllErrors($xpath);

$xpath = $ec->attachCredential($projName, $credName,
    {procedureName => "DownloadObject",
     stepName => "downloadObject"});
$errors .= $ec->checkAllErrors($xpath);

$xpath = $ec->attachCredential($projName, $credName,
    {procedureName => "CreateFolder",
     stepName => "createFolder"});
$errors .= $ec->checkAllErrors($xpath);

$xpath = $ec->attachCredential($projName, $credName,
    {procedureName => "ListFolder",
     stepName => "listFolder"});
$errors .= $ec->checkAllErrors($xpath);

$xpath = $ec->attachCredential($projName, $credName,
    {procedureName => "DownloadFolder",
     stepName => "downloadFolder"});
$errors .= $ec->checkAllErrors($xpath);

$xpath = $ec->attachCredential($projName, $credName,
    {procedureName => "DeleteObject",
     stepName => "deleteObject"});
$errors .= $ec->checkAllErrors($xpath);

$xpath = $ec->attachCredential($projName, $credName,
    {procedureName => "DeleteBucketContents",
     stepName => "deleteBucketContents"});
$errors .= $ec->checkAllErrors($xpath);


$xpath = $ec->attachCredential($projName, $credName,
    {procedureName => "WebsiteHosting",
     stepName => "websiteHosting"});
$errors .= $ec->checkAllErrors($xpath);

if ("$errors" ne "") {
    # Cleanup the partially created configuration we just created
    $ec->deleteProperty($configPath);
    $ec->deleteCredential($projName, $credName);
    my $errMsg = "Error creating configuration credential: " . $errors;
    $ec->setProperty("/myJob/configError", $errMsg);
    print $errMsg;
    exit 1;
}
