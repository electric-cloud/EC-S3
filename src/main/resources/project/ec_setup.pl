# Data that drives the create step picker registration for this plugin.
my %CreateBucket = ( 
  label       => "EC-S3 - CreateBucket", 
  procedure   => "CreateBucket", 
  description => "Create a new Bucket on S3 Object Storage", 
  category    => "Resource Management" 
);

my %UploadObject = ( 
  label       => "EC-S3 - UploadObject", 
  procedure   => "UploadObject", 
  description => "Uploads an Object to given bucket on S3 Object Storage", 
  category    => "Resource Management" 
);

my %DownloadObject = ( 
  label       => "EC-S3 - DownloadObject", 
  procedure   => "DownloadObject", 
  description => "Download an Object from a given bucket to local storage", 
  category    => "Resource Management" 
);

my %CreateFolder = ( 
  label       => "EC-S3 - CreateFolder", 
  procedure   => "CreateFolder", 
  description => "Create a new Folder under a given bucket on S3 Object Storage", 
  category    => "Resource Management" 
);

my %ListBucket = ( 
  label       => "EC-S3 - ListBucket", 
  procedure   => "ListBucket", 
  description => "Lists the buckets under current account", 
  category    => "Resource Management" 
);

my %ListFolder = ( 
  label       => "EC-S3 - ListFolder", 
  procedure   => "ListFolder", 
  description => "Lists the objects under a given folder/prefix", 
  category    => "Resource Management" 
);

@::createStepPickerSteps = (\%CreateBucket, \%UploadObject, \%DownloadObject, \%ListBucket, \%CreateFolder, \%ListFolder);

if ($upgradeAction eq "upgrade") {
    my $cfgPropertySheet = 's3_cfgs';
    $commander->{abortOnError} = 0;
    my $query = $commander->newBatch();
    my $newcfg = $query->getProperty(
        "/plugins/$pluginName/project/$cfgPropertySheet");
    my $oldcfgs = $query->getProperty(
        "/plugins/$otherPluginName/project/$cfgPropertySheet");

    my $creds = $query->getCredentials(
        "\$[/plugins/$otherPluginName]");

    $query->submit();

    # if new plugin does not already have cfgs
    if ($query->findvalue($newcfg,"code") eq "NoSuchProperty") {
        # if old cfg has some cfgs to copy
        if ($query->findvalue($oldcfgs,"code") ne "NoSuchProperty") {
            $batch->clone({
                path => "/plugins/$otherPluginName/project/$cfgPropertySheet",
                cloneName => "/plugins/$pluginName/project/$cfgPropertySheet"
            });
        }
    }

    # Copy configuration credentials and attach them to the appropriate steps
    my $nodes = $query->find($creds);
    if ($nodes) {
        my @nodes = $nodes->findnodes('credential/credentialName');
        for (@nodes) {

            my $cred = $_->string_value;

            # Clone the credential
            $batch->clone({
                path => "/plugins/$otherPluginName/project/credentials/$cred",
                cloneName => "/plugins/$pluginName/project/credentials/$cred"
            });

            # Make sure the credential has an ACL entry for the new project principal
            my $xpath = $commander->getAclEntry("user", "project: $pluginName", {
                projectName => $otherPluginName,
                credentialName => $cred
            });
            if ($xpath->findvalue("//code") eq "NoSuchAclEntry") {
                $batch->deleteAclEntry("user", "project: $otherPluginName", {
                    projectName => $pluginName,
                    credentialName => $cred
                });
                $batch->createAclEntry("user", "project: $pluginName", {
                    projectName => $pluginName,
                    credentialName => $cred,
                    readPrivilege => 'allow',
                    modifyPrivilege => 'allow',
                    executePrivilege => 'allow',
                    changePermissionsPrivilege => 'allow'
                });
            }

            # Attach credentials to appropriate steps
            $batch->attachCredential("\$[/plugins/$pluginName/project]", $cred, {
                procedureName => 'CreateBucket',
                stepName => 'createBucket'
            });
            $batch->attachCredential("\$[/plugins/$pluginName/project]", $cred, {
                procedureName => 'UploadObject',
                stepName => 'uploadObject'
            });
            $batch->attachCredential("\$[/plugins/$pluginName/project]", $cred, {
                procedureName => 'DownloadObject',
                stepName => 'downloadObject'
            });
            $batch->attachCredential("\$[/plugins/$pluginName/project]", $cred, {
                procedureName => 'ListBucket',
                stepName => 'listBucket'
            });
            $batch->attachCredential("\$[/plugins/$pluginName/project]", $cred, {
                procedureName => 'CreateFolder',
                stepName => 'createFolder'
            });
            $batch->attachCredential("\$[/plugins/$pluginName/project]", $cred, {
                procedureName => 'ListFolder',
                stepName => 'listFolder'
            });
		}
    }
}