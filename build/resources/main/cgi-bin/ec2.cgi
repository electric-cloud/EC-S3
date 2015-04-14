#!/bin/sh

exec "$COMMANDER_HOME/bin/ec-perl" -x "$0" "${@}"

#!perl
# ec2.cgi -
#
# Get/set EC-S3 configuration info for UI
#
# The following special keyword indicates that the "cleanup" script should
# scan this file for formatting errors, even though it doesn't have one of
# the expected extensions.
# CLEANUP: CHECK
#
# Copyright (c) 2007-2009 Electric Cloud, Inc.
# All rights reserved

#use strict;
#no strict "subs";
#use warnings;
use Getopt::Long;
use File::Spec;
use File::Temp;
use ElectricCommander;
use ElectricCommander::PropMod;
use ElectricCommander::PropDB;
use CGI qw(:standard);

# used for output redirection
$::tmpOut = "";
$::tmpErr = "";
$::oldout;
$::olderr;

#-------------------------------------------------------------------------
# main
#
#      Main program for the application.
#-------------------------------------------------------------------------

sub main() {

    ## globals
    $::cg = CGI->new();
    $::opts = $::cg->Vars;
    $::ec = new ElectricCommander();
    $::ec->abortOnError(0);

    # make sure no libraries print to STDOUT
    saveOutErr();

    # Check for required arguments.
    if (!defined $::opts->{cmd} || "$::opts->{cmd}" eq "") {
        retError("error: cmd is required parameter");
    }

    # ---------------------------------------------------------------
    # Dispatch operation
    # ---------------------------------------------------------------
    for ($::opts->{cmd})
    {
        # modes
        /getCfgList/i and do   { getCfgList(); last; };
    }
    retError("unknown command $::opts->{cmd}");

    exit 0;
}


#############################################
# getCfgList
#
# Return the list of configurations
#############################################
sub getCfgList {

    my $gcfg = new ElectricCommander::PropDB($::ec,"/projects/EC-S3-1.0.0.0/s3_cfgs");

    my %cfgs = $gcfg->getRows();
    # print results as XML block
    my $xml = "";
    $xml .= "<cfgs>\n";
    foreach my $cfg (keys  %cfgs) {
        my $service_url = $gcfg->getCol("$cfg/service_url");
        my $desc = eval { $gcfg->getCol("$cfg/description") };
        $xml .= "  <cfg>\n";
        $xml .= "     <name>$cfg</name>\n";
        $xml .= "     <service_url>" . xmlQuote($service_url) . "</service_url>\n";
        $xml .= "     <desc>" . xmlQuote($desc) . "</desc>\n";
        $xml .= "  </cfg>\n";
    }
    $xml .= "</cfgs>\n";
    printXML($xml);
    exit 0;
}

##############################################
# retError
#
# return an error message
##############################################
sub retError {
    my $msg = shift;

    printXML("<error>$msg</error>\n");
    exit 1;
}

##############################################
# printXML
#
# print the XML block, add stdout, stderr
##############################################
sub printXML {
    my $xml = shift;

    my ($out,$err) = retrieveOutErr();
    print $::cg->header("text/html");
    print "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n";
    print "<response>\n";
    print "$xml\n";
    print "<stdout>" . xmlQuote($out) . "</stdout>\n";
    print "<stderr>" . xmlQuote($err) . "</stderr>\n";
    print "</response>";
}


##############################################
# saveOutErr
#
# redirect stdout/stderr to files so that any
# spurious output from commands does not 
# end up on the return to the cgi caller
##############################################
sub saveOutErr {
    # temporarily save STDOUT/STDERR to files
    open $::oldout, ">&STDOUT"  or die "Can't dup STDOUT: $!";
    open $::olderr, ">&STDERR"  or die "Can't dup STDERR: $!";
    close STDOUT;
    open STDOUT, '>', \$::tmpOut or die "Can't open STDOUT: $!";
    close STDERR;
    open STDERR, '>', \$::tmpErr or die "Can't open STDOUT: $!";

}

##############################################
# retrieveOutErr
#
# reset stdout/sterr back to normal and 
# return the contents of the temp files
##############################################
sub retrieveOutErr {
    # reconnect to normal STDOUT/STDERR
    open STDOUT, ">&", $::oldout or die "can't reinstate $!";
    open STDERR, ">&", $::olderr or die "can't reinstate $!";
    return ($::tmpOut, $::tmpErr);
}

#-------------------------------------------------------------------------
# xmlQuote
#
#      Quote special characters such as & to generate well-formed XML
#      character data.
#
# Results:
#      The return value is identical to $string except that &, <, and >,
#      have been translated to &amp;, &lt;, and &gt;, respectively.
#
# Side Effects:
#      None.
#
# Arguments:
#      string -        String whose contents should be quoted.
#-------------------------------------------------------------------------

sub xmlQuote($) {
    my ($string) = @_;

    $string =~ s/&/&amp;/g;
    $string =~ s/</&lt;/g;
    $string =~ s/>/&gt;/g;
    $string =~ s{([\0-\x{08}\x{0b}\x{0c}\x{0e}-\x{1f}])}{
    sprintf("%%%02x", ord($1))}ge;
    return $string;
}


main();






