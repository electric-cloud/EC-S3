use ElectricCommander;

my $ec = new ElectricCommander();

my $projName = "@PLUGIN_NAME@";

$ec->deleteProperty("/projects/$projName/s3_cfgs/$[config]");
$ec->deleteCredential($projName, "$[config]");
exit 0;
