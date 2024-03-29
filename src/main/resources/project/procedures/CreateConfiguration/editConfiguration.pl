use strict;
use warnings;
## === createConfiguration template ===
# Please do not edit this file
use ElectricCommander;
use ElectricCommander::PropDB;
use JSON;
use Data::Dumper;
# exit 0;
use constant {
    SUCCESS => 0,
    ERROR   => 1,
};
local $| = 1;

my $projName = '@PLUGIN_KEY@-@PLUGIN_VERSION@';
my $ec = ElectricCommander->new();

my $configPropertySheet;
eval {
    $configPropertySheet = $ec->getPropertyValue('/myProject/ec_configPropertySheet');
    1;
} or do {
    $configPropertySheet = 's3_cfgs';
};

my $steps = [];
my $stepsJSON = eval {
    $ec->getPropertyValue("/projects/$projName/procedures/CreateConfiguration/ec_stepsWithAttachedCredentials")
};
if ($stepsJSON) {
    $steps = decode_json($stepsJSON);
}

my $opts = getActualParameters();

my $configuration_name = $opts->{config};
my $configName = $configuration_name;

# print "PROJECT NAME: $projName\n";
# print Dumper $opts;
# print Dumper $steps;

# 1. Check whether configuration property sheet exists. Procedure should be failed if it does not.

my $propertySheetFullPath = '/projects/' . $projName . '/' . $configPropertySheet . '/' . $configuration_name;
# print "Full Path To Config Property Sheet: $propertySheetFullPath\n";


eval {
    $ec->getPropertyValue($propertySheetFullPath);
    1;
} or do {
    print "Config property sheet $propertySheetFullPath does not exist. Can't edit.\n";
    exit 1;
};

# now we have a path to the propety sheet where our config is stored.
# we're handling properties, credentials will go next.

for my $k (keys %$opts) {
    if ($k =~ m/_?credential$/s) {
        my $key_ref = $k . '_ref';
        unless ($opts->{$key_ref}) {
            # Deleting credential
            # push @$credentials_to_delete, gen_credential_name($configuration_name, $k);
            # print "Full path of credential: $propertySheetFullPath/$k\n";

            my $generated_cred_name = gen_credential_name($configuration_name, $k);
            $ec->setProperty("$propertySheetFullPath/$k", gen_credential_name($configuration_name, $k));
            my $rr = $ec->getPropertyValue("$propertySheetFullPath/$k");
            # print "PROP: $k, VALUE: $rr\n", Dumper $rr;
        }
        else {
            # it has the credential reference, so the runtime credential should be deleted.
            # TODO: Add deletion of runtime credential.
            # print "SETTING REFERENCE: $propertySheetFullPath/$k, $opts->{$key_ref}\n";
            my $credential_to_delete =  gen_credential_name($configuration_name, $k);
            $ec->deleteCredential($projName, $credential_to_delete);
            $ec->setProperty("$propertySheetFullPath/$k", $opts->{$key_ref});
        }
        next;
    }
    my $propPath = $propertySheetFullPath . '/' . $k;
    # print "Setting '$propPath' => $opts->{$k}\n";
    $ec->setProperty($propPath, $opts->{$k});
}

# deleting ref keys
for my $k (keys %$opts) {
    if ($k =~ m/credential_ref$/s) {
        $ec->deleteProperty("$propertySheetFullPath/$k");
    }
}

eval {
    my $opts = getActualParameters();
    # print "OPTS2: ", Dumper $opts;
    for my $param ($ec->getFormalParameters({
        projectName => $projName,
        procedureName => 'CreateConfiguration',
    })->findnodes('//formalParameter')) {
        my $type = $param->findvalue('type') . '';
        if ($type eq 'credential') {
            my $required = $param->findvalue('required') . '';
            my $fieldName = $param->findvalue('formalParameterName') . '';
            my $credentialName = $opts->{$fieldName};

            eval {
                my $ref = createAndAttachCredential($credentialName, $configName, $configPropertySheet, $steps);
                1;
            } or do {
                my $err = $@;
                if ($required) {
                    die $err;
                }
                else {
                    print "Failed to create credential $credentialName: $err\n";
                }
            };
        }
    }
    1;
} or do {
    my $err = $@;
    print $err;
    rollback($configPropertySheet, $err);
    $ec->setProperty("/myJob/configError", $err);
    exit 1;
};


# Handling credentials here
### Service subroutines

sub getActualParameters {
    my $x       = $ec->getJobDetails($ENV{COMMANDER_JOBID});
    my $nodeset = $x->find('//actualParameter');
    my $opts;

    foreach my $node ($nodeset->get_nodelist) {
        my $parm = $node->findvalue('actualParameterName');
        if ($parm =~ m/credential$/s) {
            my $credRef = getCredentialReference($parm);
            if ($credRef) {
                my $key = $parm . '_ref';
                $opts->{$key} = $credRef;
            }
        }
        my $val  = $node->findvalue('value');
        $opts->{$parm} = "$val";
    }
    return $opts;
}


sub getCredentialReference {
    my ($credName) = @_;

    if (!$credName) {
        die "Cred name is mandatory to get credential reference...\n";
    }
    my $xpath = $ec->getFullCredential($credName);
    my $existingCredRef = undef;
    eval {
        $existingCredRef = $xpath->findvalue("//credentialReference")->string_value();
    };

    return $existingCredRef;
}


sub createAndAttachCredential {
    my ($credName, $configName, $configPropertySheet, $steps) = @_;

    my $xpath = $ec->getFullCredential($credName);
    my $errors = $ec->checkAllErrors($xpath);

    my $clientID = $xpath->findvalue("//userName");
    my $clientSecret = $xpath->findvalue("//password");
    my ($existingCredRef, $existingCredProjectName, $existingCredName);
    eval {
        $existingCredRef = $xpath->findvalue("//credentialReference")->string_value();
        $existingCredName = $xpath->findvalue("//credentialName")->string_value();
        # here we have the credential that has been passed as a reference.
        $existingCredProjectName = $1 if $existingCredRef =~ m|\/projects\/(.*?)\/|s;
        # now we have all three of them. And if these values are defined - we have
        # existing credential that we just need to attach instead of create.
    };
    my $projName = '$[/myProject/projectName]';

    my $credObjectName = $credName eq 'credential' ? $configName : "${configName}_${credName}";
    # die $credObjectName;
    my $configPath = "/projects/$projName/$configPropertySheet/$configName/$credName";
    if (!$existingCredRef) {
        print "Credential reference is not present. Creating runtime credential.\n";
        # Create credential
        $ec->deleteCredential($projName, $credObjectName);
        $xpath = $ec->createCredential($projName, $credObjectName, $clientID, $clientSecret);
        $errors .= $ec->checkAllErrors($xpath);

        # Give config the credential's real name

        # DEBUG
        # $xpath = $ec->setProperty($configPath, $credObjectName);
        # $errors .= $ec->checkAllErrors($xpath);
        # END OF DEBUG
        # print "SET PROPERTY: setProperty($configPath, $credObjectName);\n";
        # Give job launcher full permissions on the credential
        my $user = '$[/myJob/launchedByUser]';
        $xpath = $ec->createAclEntry("user", $user, {
            projectName => $projName,
            credentialName => $credObjectName,
            readPrivilege => 'allow',
            modifyPrivilege => 'allow',
            executePrivilege => 'allow',
            changePermissionsPrivilege => 'allow'
        });
        $errors .= $ec->checkAllErrors($xpath);
    }
    # Attach credential to steps that will need it
    for my $step( @$steps ) {
        if ($existingCredRef) {
            $credObjectName = $existingCredRef;
        }
        print "Attaching credential to procedure " . $step->{procedureName} . " at step " . $step->{stepName} . "\n";
        my $apath = $ec->attachCredential(
            $projName,
            $credObjectName, {
            procedureName => $step->{procedureName},
            stepName => $step->{stepName}
        });
        $errors .= $ec->checkAllErrors($apath);
    }

    if ("$errors" ne "") {
        # Cleanup the partially created configuration we just created
        $ec->deleteProperty($configPath);
        $ec->deleteCredential($projName, $credObjectName);
        my $errMsg = "Error creating configuration credential: " . $errors;
        $ec->setProperty("/myJob/configError", $errMsg);
        die $errMsg;
    }
}

sub gen_credential_name {
    my ($config_name, $credential_name) = @_;

    if ($credential_name eq 'credential') {
        return $config_name;
    }

    return $config_name . '_' . $credential_name;
}