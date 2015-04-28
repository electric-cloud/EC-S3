#########################
## createcfg.pl
#########################

use ElectricCommander;
use ElectricCommander::PropDB;
use ElectricCommander::PropMod qw(/myProject/lib);
use Carp qw( carp croak );

use constant {
               SUCCESS => 0,
               ERROR   => 1,
             };

## get an EC object
my $ec = new ElectricCommander();
$ec->abortOnError(0);

my $credName = "$[/myJob/config]";

my $xpath    = $ec->getFullCredential("credential");
my $errors   = $ec->checkAllErrors($xpath);
my $AWS_ACCESS_KEY_ID = $xpath->findvalue("//userName");
my $AWS_SECRET_ACCESS_KEY = $xpath->findvalue("//password");

my $projName = "$[/myProject/projectName]";
print "Attempting connection with user server\n";

#require Amazon::EC2::Client;
#my $config = {
#               ServiceURL       => "$[service_url]",
#               UserAgent        => "Amazon S3 Perl Library",
#               SignatureVersion => 2,
#               SignatureMethod  => "HmacSHA256",
#               ProxyHost        => undef,
#               ProxyPort        => -1,
#               MaxErrorRetry    => 3
#             };
#my $service = Amazon::EC2::Client->new($AWS_ACCESS_KEY_ID, $AWS_SECRET_ACCESS_KEY, $config);

#require Amazon::EC2::Model::DescribeAvailabilityZonesRequest;#

#my $request = new Amazon::EC2::Model::DescribeAvailabilityZonesRequest();
##
##eval {
##    my $response = $service->describeAvailabilityZones($request);
##
##    print("Service Response\n");
##    print("=============================================================================\n");
##
##    print(" DescribeAvailabilityZonesResponse\n");
##    if ($response->isSetResponseMetadata()) {
##        my $responseMetadata = $response->getResponseMetadata();
##        if ($responseMetadata->isSetRequestId()) {
##            print("  RequestId:");
##            print(" " . $responseMetadata->getRequestId() . "\n");
##        }
##    }
##};
##my $ex = $@;
##if ($ex) {
##    my $errMsg = "Test connection failed.\n";
##    $ec->setProperty("/myJob/configError", $errMsg);
##    print $errMsg;
##    
##    $ec->deleteProperty("/projects/$projName/ec2_cfgs/$credName");
##    $ec->deleteCredential($projName, $credName);
##    require Amazon::EC2::Exception;
##    if (ref $ex eq "Amazon::EC2::Exception") {
##        print("Caught Exception: " . $ex->getMessage() . "\n");
##        print("Response Status Code: " . $ex->getStatusCode() . "\n");
##        print("Error Code: " . $ex->getErrorCode() . "\n");
##
##    }
##    else {
##       croak $@;
##    }
##
##    exit ERROR;
##}

exit SUCCESS;
