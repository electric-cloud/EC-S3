#!/bin/sh

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

exec "$COMMANDER_HOME/bin/cb-perl" -x "$0" "${@}"

#!perl

###############################################################################
# 
# monitorJob.cgi
#
# Monitors a job: waits for it to complete and reports on its success or
# failure.
#
###############################################################################

use strict vars;
use ElectricCommander;
use XML::XPath;
use CGI;

my $gTimeout = 300;

# -----------------------------------------------------------------------------
# main
#
# -----------------------------------------------------------------------------

sub main {

    # Get CGI args
    my $cgi = new CGI;
    my $cgiArgs = $cgi->Vars;
    
    # Check for required args
    my $jobId = $cgiArgs->{jobId};
    if (!defined $jobId || "$jobId" eq "") {
        reportError($cgi, "jobId is a required parameter");
    }
    
    # Wait for job
    my $ec = new ElectricCommander({abortOnError => 0});
    my $xpath = $ec->waitForJob($jobId, $gTimeout);
    my $errors = $ec->checkAllErrors($xpath);
    
    if ("$errors" ne "") {
        reportError($cgi, $errors);
    }
    
    my $status = $xpath->findvalue("//status");
    if ("$status" ne "completed") {
        
        # Abort job and report failure
        abortJobAndReportError($cgi, $ec, $jobId);
    }
    
    my $outcome = $xpath->findvalue("//outcome");
    if ("$outcome" ne "success") {
        
        # Report job errors
        reportJobErrors($cgi, $ec, $jobId);
    }
    
    # If the job was successful and the debug flag is not set, delete it
    my $debug = $cgiArgs->{debug};
    if (!defined $debug || "$debug" ne "1") {
        $ec->deleteJob($jobId);
    }
    
    # Report the job's success
    reportSuccess($cgi);
}

# -----------------------------------------------------------------------------
# abortJobAndReportError
#
#   Abort the job and report the timeout error.
# -----------------------------------------------------------------------------

sub abortJobAndReportError($$$) {
    my ($cgi, $ec, $jobId) = @_;
    
    my $errMsg = "Aborting job after reaching timeout";
        
    # Try to abort the job
    my $xpath = $ec->abortJob($jobId);
    my $errors = $ec->checkAllErrors($xpath);
    if ("$errors" ne "") {
        reportError($cgi, $errMsg . "\n" . $errors);
    }
    
    # Wait for the job to finish aborting
    $xpath = $ec->waitForJob($jobId, $gTimeout);
    $errors = $ec->checkAllErrors($xpath);
    if ("$errors" ne "") {
        reportError($cgi, $errMsg . "\n" . $errors);
    }
    
    # Check to see if the job actually aborted
    my $status = $xpath->findvalue("//status");
    if ("$status" ne "completed") {
        reportError($cgi, $errMsg . "\nJob still running after abort");
    }
    
    reportError($cgi, $errMsg . "\nJob successfully aborted");
}

# -----------------------------------------------------------------------------
# reportJobErrors
#
#   Look for errors in the job to report.
# -----------------------------------------------------------------------------

sub reportJobErrors($$$) {
    my ($cgi, $ec, $jobId) = @_;
    
    # Get job details
    my $xpath = $ec->getJobDetails($jobId);
    my $errors = $ec->checkAllErrors($xpath);
    if ("$errors" ne "") {
        reportError($cgi, $errors);
    }
    
    # Look for configError first
    my $configError = $xpath->findvalue("//job/propertySheet/property[propertyName='configError']/value");
    if (defined $configError && "$configError" ne "") {
        reportError($cgi, $configError)
    }
    
    # Find the first error message and report it
    my @errorMessages = $xpath->findnodes("//errorMessage");
    if (@errorMessages > 0) {
        my $firstMessage = $errorMessages[0]->string_value();
        reportError($cgi, $firstMessage);
    }
    
    # Report a generic error message if we couldn't find a specific one on the
    # job
    reportError($cgi, "Configuration creation failed");
}

# -----------------------------------------------------------------------------
# reportError
#
#   Print the error message and exit.
# -----------------------------------------------------------------------------

sub reportError($$) {
    my ($cgi, $error) = @_;
    
    print $cgi->header("text/html");
    print $error;
    exit 1;
}

# -----------------------------------------------------------------------------
# reportSuccess
#
#   Report success.
# -----------------------------------------------------------------------------

sub reportSuccess($) {
    my ($cgi) = @_;
    
    print $cgi->header("text/html");
    print "Success";
}

###############################################
# validateUserSession
#
#      Check current session on valid, if not - redirects
#      user to the login page.
###############################################
sub validateUserSession() {

    my $ec = ElectricCommander->new({abortOnError => 0});
    $ec->login();
    if($ec->getError()) {
        print "Location: ../../../\n\n";
        exit 0;
    }
}

validateUserSession();

main();
exit 0;
