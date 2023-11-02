# Copyright 2015 Electric Cloud, Inc.
#
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
#
#     http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.


# Data that drives the create step picker registration for this plugin.
use ElectricCommander::Util;
use strict;
use warnings;
no warnings 'redefine';
use Data::Dumper;

use ElectricCommander;
use JSON qw(decode_json);
use subs qw(debug);
use Time::HiRes qw(time gettimeofday tv_interval);

my @logs = ();
sub debug($) {
    my ($message) = @_;
    push @logs, scalar time . ": " . $message;

    if ($ENV{EC_SETUP_DEBUG}) {
        print scalar time . ": $message\n";
    }
}

our $plugin_config_locations = 's3_cfgs';

# External Credential Manageent Update:
# We're retrieving the steps with attached creds from property sheet
my $stepsWithCredentials = getStepsWithCredentials();


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

my %UploadFolder = ( 
  label       => "EC-S3 - UploadFolder", 
  procedure   => "UploadFolder",
  description => "Uploads an Folder to given bucket on S3 Object Storage", 
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

my %DownloadFolder = ( 
  label       => "EC-S3 - DownloadFolder", 
  procedure   => "DownloadFolder", 
  description => "Download all the contents from given Folder / Prefix to local storage", 
  category    => "Resource Management" 
);

my %DeleteObject = ( 
  label       => "EC-S3 - DeleteObject", 
  procedure   => "DeleteObject", 
  description => "Delete a given object from the given bucket", 
  category    => "Resource Management" 
);

my %DeleteBucketContents = ( 
  label       => "EC-S3 - DeleteBucketContents", 
  procedure   => "DeleteBucketContents", 
  description => "Delete all the objects under a given bucket", 
  category    => "Resource Management" 
);

my %WebsiteHosting = (
  label       => "EC-S3 - WebsiteHosting",
  procedure   => "WebsiteHosting",
  description => "Host your static website in S3",
  category    => "Resource Management"
);

@::createStepPickerSteps = (\%CreateBucket, \%UploadObject, \%UploadFolder, \%DownloadObject, \%ListBucket, \%CreateFolder, \%ListFolder, \%DownloadFolder, \%DeleteObject, \%DeleteBucketContents, \%WebsiteHosting);

if ($upgradeAction eq 'upgrade') {
    migrateConfigurations($otherPluginName);
    migrateProperties($otherPluginName);
    debug "Migrated properties";
    reattachExternalCredentials($otherPluginName);
}


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
                procedureName => 'UploadFolder',
                stepName => 'uploadFolder'
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
            $batch->attachCredential("\$[/plugins/$pluginName/project]", $cred, {
                procedureName => 'DownloadFolder',
                stepName => 'downloadFolder'
            });
            $batch->attachCredential("\$[/plugins/$pluginName/project]", $cred, {
                procedureName => 'DeleteObject',
                stepName => 'deleteObject'
            });
            $batch->attachCredential("\$[/plugins/$pluginName/project]", $cred, {
                procedureName => 'DeleteBucketContents',
                stepName => 'deleteBucketContents'
            });
            $batch->attachCredential("\$[/plugins/$pluginName/project]", $cred, {
                procedureName => 'WebsiteHosting',
                stepName => 'websiteHosting'
            });
		}
    }
    reattachExternalCredentials($otherPluginName);
}


sub reattachExternalCredentials {
    my ($otherPluginName) = @_;

    my $configName = getConfigLocation($otherPluginName);
    my $configsPath = "/plugins/$otherPluginName/project/$configName";

    my $xp = $commander->getProperty($configsPath);

    my $id = $xp->findvalue('//propertySheetId')->string_value();
    my $props = $commander->getProperties({propertySheetId => $id});
    for my $node ($props->findnodes('//property/propertySheetId')) {
        my $configPropertySheetId = $node->string_value();
        my $config = $commander->getProperties({propertySheetId => $configPropertySheetId});

        # iterate through props to get credentials.
        for my $configRow ($config->findnodes('//property')) {
            my $propName = $configRow->findvalue('propertyName')->string_value();
            my $propValue = $configRow->findvalue('value')->string_value();
            # print "Name $propName, value: $propValue\n";
            if ($propName =~ m/credential$/s && $propValue =~ m|^\/|s) {
                for my $step (@$stepsWithCredentials) {
                    $batch->attachCredential({
                        projectName    => $pluginName,
                        procedureName  => $step->{procedureName},
                        stepName       => $step->{stepName},
                        credentialName => $propValue,
                    });
                    #    debug "Attached credential to $step->{stepName}";
                }
                print "Reattaching $propName with val: $propValue\n";
            }
        }
        # exit 0;
    }
}

sub getConfigLocation {
    my ($otherPluginName) = @_;

    my $configName = eval {
        $commander->getProperty("/plugins/$otherPluginName/project/ec_configPropertySheet")->findvalue('//value')->string_value
    } || $plugin_config_locations;
    return $configName;
}

sub getStepsWithCredentials {
    my $retval = [];
    eval {
        my $pluginName = '@PLUGIN_NAME@';
        my $stepsJson = $commander->getProperty("/projects/$pluginName/procedures/CreateConfiguration/ec_stepsWithAttachedCredentials")->findvalue('//value')->string_value;
        $retval = decode_json($stepsJson);
    };
    return $retval;
}

sub reattachExternalConfigurations {
    my ($otherPluginName) = @_;

    my %migrated = ();
    # For the configurations that exists while the plugin was deleted
    # The api is new so it requires the upgraded version of the agent
    eval {
        my $cfgs = $commander->getPluginConfigurations({
            pluginKey => '@PLUGIN_KEY@',
        });
        my @creds = ();
        for my $cfg ($cfgs->findnodes('//pluginConfiguration/credentialMappings/parameterDetail')) {
            my $value = $cfg->findvalue('parameterValue')->string_value();
            push @creds, $value;
        }

        for my $cred (@creds) {
            next if $migrated{$cred};
            for my $stepWithCreds (@$stepsWithCredentials) {
                $commander->attachCredential({
                    projectName => "/plugins/$pluginName/project",
                    credentialName => $cred,
                    procedureName => $stepWithCreds->{procedureName},
                    stepName => $stepWithCreds->{stepName}
                });
            }
            $migrated{$cred} = 1;
            debug "Migrated $cred";
        }
        1;
    } or do {
        debug "getPluginConfiguration API is not supported on the promoting agent, falling back";
        for my $stepWithCreds (@$stepsWithCredentials) {
            my $step = $commander->getStep({
                projectName => "/plugins/$otherPluginName/project",
                procedureName => $stepWithCreds->{procedureName},
                stepName => $stepWithCreds->{stepName},
            });
            for my $attachedCred ($step->findnodes('//attachedCredentials/credentialName')) {
                my $credName = $attachedCred->string_value();
                $commander->attachCredential({
                    projectName => "/plugins/$pluginName/project",
                    credentialName => $credName,
                    procedureName => $stepWithCreds->{procedureName},
                    stepName => $stepWithCreds->{stepName}
                });
                $migrated{$credName} = 1;
                debug "Migrated credential $credName to $stepWithCreds->{procedureName}";
            }
        }
    };
}

sub migrateConfigurations {
    my ($otherPluginName) = @_;

    my $configName = getConfigLocation($otherPluginName);
    # my $configName = eval {
    #     $commander->getProperty("/plugins/$otherPluginName/project/ec_configPropertySheet")->findvalue('//value')->string_value
    # } || 'ec_plugin_cfgs';

    $commander->clone({
        path      => "/plugins/$otherPluginName/project/$configName",
        cloneName => "/plugins/$pluginName/project/$configName"
    });

}

sub migrateProperties {
    my ($otherPluginName) = @_;
    my $clonedPropertySheets = eval {
        decode_json($commander->getProperty("/plugins/$otherPluginName/project/ec_clonedProperties")->findvalue('//value')->string_value);
    };
    unless ($clonedPropertySheets) {
        debug "No properties to migrate";
        return;
    }

    for my $prop (@$clonedPropertySheets) {
        $commander->clone({
            path      => "/plugins/$otherPluginName/project/$prop",
            cloneName => "/plugins/$pluginName/project/$prop"
        });
        debug "Cloned $prop"
    }
}