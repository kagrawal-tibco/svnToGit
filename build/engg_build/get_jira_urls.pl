#!/usr/bin/perl
#
#
# This script searches for "BE-XXXX" Jira issues for a given V-build (5.2.1.026)
# Then for each Jira issue that it finds, it updates Jira's "Resolver Build" field (in this example, "V26") using Jira's REST APIs.
#
#

use JSON;
use strict;

die "Usage: get_jira_urls.pl <vbuilds-file> <be-build-revision>" if (scalar @ARGV < 2);

my $flNm = $ARGV[0];
my $bldNo = $ARGV[1];

my $JIRA_URL = "https://jira.tibco.com/browse/";

my $jiraUrl = "https://jira.tibco.com";
my $jiraUsr = "auto_BE";
my $jiraPwd = "aut0sDef1";


open(my $fh, '<', $flNm);

my $startPattern = '>>>>>'.$bldNo.'>>>>>';
my $endPattern = '<<<<<'.$bldNo.'<<<<<';
#print "$startPattern    $endPattern\n";

my $found = 0;
my %jiraIssues;
while ( <$fh> ) {
  if ($_ =~ /$startPattern/) {
    $found = 1;
    next;
  } elsif ($_ =~ /$endPattern/) {
    $found = 0;
    next;
  }
  if ($found == 1) {
    
	#replacing Clone of Jira block with underscore to prevent those JIRAs to be included in the URL list
	my $line = $_;
	$line=~ s/(\(clone of BE-\d+\))/\_/g;
	
    my @ln = split(/\s|,|;/, $line);
    foreach my $word (@ln) {
      if ( $word =~ /(^BE-\d+)/) {
        $jiraIssues{$1}=$1;
      }
    }
  }
}

my $resolverBuild;

if ($bldNo =~ /^\d+?\.\d+?\.\d+?\.(\d+)/) {
  my $resBldNo=$1;
  $resBldNo =~ s/^0+//;
  $resolverBuild = "V$resBldNo";
}

my $jiraUrls;
foreach my $jiraIssueNo (sort keys %jiraIssues) {
  my $CURL_GET = "curl -k -D- -u %%JIRA_USER%%:%%JIRA_PWD%% -X GET -H \"Content-Type: application/json\" \"%%jiraUrl%%/rest/api/2/issue/%%JIRA_ISSUE%%?fields=summary\"";
  #substitute jira user
  $CURL_GET =~ s/%%JIRA_USER%%/$jiraUsr/g;
  # subs jira pwd
  $CURL_GET =~ s/%%JIRA_PWD%%/$jiraPwd/g;
  # subs Jira URL
  $CURL_GET =~ s/%%jiraUrl%%/$jiraUrl/g;
  # subs jira ticket
  $CURL_GET =~ s/%%JIRA_ISSUE%%/$jiraIssueNo/g;

  #print "Curl command = $CURL_GET\n";
  my $result = `$CURL_GET`;
  #print "RESULT = $result\n";


  my @resultlist = split ('\n', $result);
  foreach my $line (@resultlist) {
    if ($line =~ /\"expand\"/) {
      $result = $line;
      last;
    }
  }


  my $parsedJson;
  my $summary = "";
  my $status = eval {$parsedJson = from_json ($result)};
  if (! defined $status) {
    $result = "error";
  } else {
    $summary = $parsedJson->{"fields"}->{"summary"};
  }
  $jiraUrls = $jiraUrls."$JIRA_URL"."$jiraIssueNo : $summary\n";
}
if ($jiraUrls ne "") {
$jiraUrls = "The following issues are addressed in this build:\n".$jiraUrls;
print $jiraUrls;
}
